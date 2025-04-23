package dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.ProdutoRecord;
import model.TransacaoRecords;

public class TransacaoDAO {

  public void cadastrarTransacao() {
    try {
      String tipoPag = obterTipoPagamento();
      int quantidade = obterQuantidade();
      ProdutoRecord produto = obterProduto();

      if (tipoPag == null || produto == null || quantidade == 0) {
        JOptionPane.showMessageDialog(null, "Operação cancelada.");
        return;
      }
      // Salvar transação e atualizar estoque

      Timestamp dataAbertura = new Timestamp(System.currentTimeMillis());
      TransacaoRecords transacao = new TransacaoRecords(quantidade, tipoPag, produto.nm_produto(), quantidade,
          dataAbertura);
      // Verificar se há estoque suficiente
      if (produto.qt_produto() < quantidade) {
        JOptionPane.showMessageDialog(null,
            "Estoque insuficiente! Apenas " + produto.qt_produto() + " unidades disponíveis.", "Erro",
            JOptionPane.ERROR_MESSAGE);
        removerProduto(quantidade);
        return;
      }

      int novoEstoque = produto.qt_produto() - quantidade;
      salvarTransacao(transacao);
      atualizarEstoque(produto.id_produto(), novoEstoque);

      // Se o novo estoque for zero, remove o produto do banco
      if (novoEstoque == 0) {
        removerProduto(produto.id_produto());
      }

      JOptionPane.showMessageDialog(null, "Transação realizada com sucesso!");

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Erro: Valor ou Quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Erro ao salvar no banco de dados: " + e.getMessage(), "Erro",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private String obterTipoPagamento() {
    String[] opcoesPag = { "Dinheiro", "PIX", "Cartão de Crédito", "Cartão de Débito" };
    return (String) JOptionPane.showInputDialog(null, "Escolha a forma de pagamento:", "Pagamento",
        JOptionPane.QUESTION_MESSAGE, null, opcoesPag, opcoesPag[0]);
  }

  private int obterQuantidade() {
    return Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade:"));
  }

  public void removerProduto(int idProduto) throws SQLException {
    String sql = "DELETE FROM produto WHERE id_produto = ?";
    try (PreparedStatement prs = Conexao.getConexao().prepareStatement(sql)) {
      prs.setInt(1, idProduto);
      prs.executeUpdate();
    }
  }

  private ProdutoRecord obterProduto() {
    List<ProdutoRecord> produtos = new ProdutoDAO().listarPorNome();
    if (produtos.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado! Cadastre um produto primeiro.");
      new ProdutoDAO().cadastrarProduto();
      return null;
    }

    String[] opcaoProd = produtos.stream()
        .map(prod -> prod.nm_produto() + " - " + prod.qt_produto() + " unidades" + " - R$" + prod.valor_produto())
        .toArray(String[]::new);

    String escolha = (String) JOptionPane.showInputDialog(null, "Escolha o produto:", "Produto",
        JOptionPane.QUESTION_MESSAGE, null, opcaoProd, opcaoProd[0]);

    if (escolha == null)
      return null;

    return produtos.stream()
        .filter(p -> escolha.startsWith(p.nm_produto()))
        .findFirst()
        .orElse(null);
  }

  private void salvarTransacao(TransacaoRecords transacao) throws SQLException {
    String sql = "INSERT INTO TRANSACAO (tipo_pag, quantidade, tipo_prod, data_cadastro) VALUES (?, ?, ?, ?)";

    try (PreparedStatement prs = Conexao.getConexao().prepareStatement(sql)) {
      prs.setString(1, transacao.tipo_Pag());
      prs.setInt(2, transacao.quantidade());
      prs.setString(3, transacao.tipo_prod());
      prs.setTimestamp(4, (Timestamp) transacao.dataAberturaConta());

      prs.execute();
    }
  }

  private void atualizarEstoque(int produtoId, int novoEstoque) throws SQLException {
    String sql = "UPDATE PRODUTO SET qt_produto = ? WHERE id_produto = ?";

    try (PreparedStatement prs = Conexao.getConexao().prepareStatement(sql)) {

      prs.setInt(1, novoEstoque);
      prs.setInt(2, produtoId);
      prs.executeUpdate();
    }
  }

  public Map<String, Map<String, Object>> transacaoTotal() {
    String selectSql = "SELECT tipo_pag, tipo_prod, quantidade AS totalQuantidade " +
        "FROM sgc_postgres.public.TRANSACAO " +
        "GROUP BY tipo_pag, tipo_prod, quantidade";

    String insertSql = "INSERT INTO TRANSACAOTOTAL (tipo_pag, tipo_prod, quantidade) VALUES (?, ?, ?)";

    Map<String, Map<String, Object>> resultado = new LinkedHashMap<>();

    try (Connection conexao = Conexao.getConexao();
        PreparedStatement prs = conexao.prepareStatement(selectSql);
        ResultSet result = prs.executeQuery();
        PreparedStatement insertPs = conexao.prepareStatement(insertSql)) {

      while (result.next()) {
        String tipoPag = result.getString("tipo_pag");
        String tipoprod = result.getString("tipo_prod");
        int totalQuantidade = result.getInt("totalQuantidade");

        insertPs.setString(1, tipoPag);
        insertPs.setString(2, tipoprod);
        insertPs.setInt(3, totalQuantidade);
        insertPs.executeUpdate();

        Map<String, Object> detalhes = new LinkedHashMap<>();
        detalhes.put("quantidade", totalQuantidade);
        detalhes.put("tipo_prod", tipoprod);

        resultado.put(tipoPag, detalhes);
      }
    } catch (SQLException e) {
      System.out.println("Erro ao gerar extrato: " + e);
    }

    return resultado;
  }

}
