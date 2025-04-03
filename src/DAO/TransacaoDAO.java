package DAO;

import aplicacao.ProdutoRecord;
import aplicacao.TransacaoRecords;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class TransacaoDAO {

  public void cadastrarTransacao() {
    try {
      double valor = obterValor();
      String tipoPag = obterTipoPagamento();
      int quantidade = obterQuantidade();
      ProdutoRecord produto = obterProduto();

      if (tipoPag == null || produto == null || quantidade == 0) {
        JOptionPane.showMessageDialog(null, "Operação cancelada.");
        return;
      }

      // Verificar se há estoque suficiente
      if (produto.qt_produto() < quantidade) {
        JOptionPane.showMessageDialog(null,
            "Estoque insuficiente! Apenas " + produto.qt_produto() + " unidades disponíveis.", "Erro",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      Timestamp dataAbertura = new Timestamp(System.currentTimeMillis());
      TransacaoRecords transacao = new TransacaoRecords(quantidade, valor, tipoPag, produto.nm_produto(), quantidade,
          dataAbertura);

      // Salvar transação e atualizar estoque
      salvarTransacao(transacao);
      atualizarEstoque(produto.id_produto(), produto.qt_produto() - quantidade);

      JOptionPane.showMessageDialog(null, "Transação realizada com sucesso!");

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Erro: Valor ou Quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Erro ao salvar no banco: " + e.getMessage(), "Erro",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private double obterValor() {
    return Double.parseDouble(JOptionPane.showInputDialog("Insira o Valor:"));
  }

  private String obterTipoPagamento() {
    String[] opcoesPag = { "Dinheiro", "PIX", "Cartão de Crédito", "Cartão de Débito" };
    return (String) JOptionPane.showInputDialog(null, "Escolha a forma de pagamento:", "Pagamento",
        JOptionPane.QUESTION_MESSAGE, null, opcoesPag, opcoesPag[0]);
  }

  private int obterQuantidade() {
    return Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade:"));
  }

  private ProdutoRecord obterProduto() {
    List<ProdutoRecord> produtos = new ProdutoDAO().listarPorNome();
    if (produtos.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado! Cadastre um produto primeiro.");
      new ProdutoDAO().cadastrarProduto();
      return null;
    }

    String[] opcaoProd = produtos.stream()
        .map(prod -> prod.nm_produto() + " - " + prod.qt_produto() + " unidades")
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
    String sql = "INSERT INTO TRANSACAO (tipo_pag, valor, quantidade, tipo_prod, data_cadastro) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement prs = Conexao.getConexao().prepareStatement(sql)) {
      prs.setString(1, transacao.tipo_Pag());
      prs.setDouble(2, transacao.valor());
      prs.setInt(3, transacao.quantidade());
      prs.setString(4, transacao.tipo_prod());
      prs.setTimestamp(5, (Timestamp) transacao.dataAberturaConta());

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
    String selectSql = "SELECT tipo_pag, SUM(valor) AS totalValor, COALESCE(SUM(quantidade), 0) AS totalQuantidade " +
        "FROM sgc_postgres.public.TRANSACAO " +
        "GROUP BY tipo_pag";

    String insertSql = "INSERT INTO TRANSACAOTOTAL (tipo_pag, valor, quantidade) VALUES (?, ?, ?)";

    Map<String, Map<String, Object>> resultado = new HashMap<>();

    try (Connection conexao = Conexao.getConexao();
        PreparedStatement prs = conexao.prepareStatement(selectSql);
        ResultSet result = prs.executeQuery();
        PreparedStatement insertPs = conexao.prepareStatement(insertSql)) {

      while (result.next()) {
        String tipoPag = result.getString("tipo_pag");
        double totalValor = result.getDouble("totalValor");
        int totalQuantidade = result.getInt("totalQuantidade");

        insertPs.setString(1, tipoPag);
        insertPs.setDouble(2, totalValor);
        insertPs.setInt(3, totalQuantidade);
        insertPs.executeUpdate();

        Map<String, Object> detalhes = new HashMap<>();
        detalhes.put("quantidade", totalQuantidade);
        detalhes.put("total", totalValor);

        resultado.put(tipoPag, detalhes);
      }
    } catch (SQLException e) {
      System.out.println("Erro ao gerar extrato: " + e);
    }

    return resultado;
  }

}
