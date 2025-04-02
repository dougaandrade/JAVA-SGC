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

  protected PreparedStatement ps = null;

  public void cadastrarTransacao() {

    String sql = "INSERT INTO TRANSACAO (tipo_pag, valor, quantidade, tipo_prod, data_cadastro) VALUES (?, ?, ?, ?, ?)";

    try {
      double valor = Double.parseDouble(JOptionPane.showInputDialog("Insira o Valor:"));

      String[] opcoesPag = { "Dinheiro", "PIX", "Cartão de Crédito", "Cartão de Débito" };
      String tipoPag = (String) JOptionPane.showInputDialog(
          null, "Escolha a forma de pagamento:", "Pagamento",
          JOptionPane.QUESTION_MESSAGE, null, opcoesPag, opcoesPag[0]);

      if (tipoPag == null)
        return;

      int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade:"));

      List<ProdutoRecord> produtos = new ProdutoDAO().listarPorNome();
      String[] opcaoProd = new String[produtos.size()];

      for (int i = 0; i < produtos.size(); i++) {
        opcaoProd[i] = produtos.get(i).nm_produto() + " - " +
            produtos.get(i).qt_produto() + " unidades";
      }

      String tipoProd = (String) JOptionPane.showInputDialog(
          null, "Escolha o produto:", "Produto",
          JOptionPane.QUESTION_MESSAGE, null, opcaoProd, opcaoProd[0]);

      if (tipoProd == null)
        return;

      Timestamp dataAbertura = new Timestamp(System.currentTimeMillis());

      TransacaoRecords transacao = new TransacaoRecords(quantidade, valor, tipoPag, tipoProd, quantidade, dataAbertura);

      JOptionPane.showMessageDialog(null, "Transação realizada com sucesso!");

      try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setString(1, transacao.tipo_Pag());
        ps.setDouble(2, transacao.valor());
        ps.setInt(3, transacao.quantidade());
        ps.setString(4, transacao.tipo_prod());
        ps.setTimestamp(5, (Timestamp) transacao.dataAberturaConta());

        ps.execute();
      }

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Erro: Valor ou Quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Erro ao salvar no banco: " + e.getMessage(), "Erro",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public Map<String, Map<String, Object>> transacaoTotal() {
    String selectSql = "SELECT tipo_pag, SUM(valor) AS totalValor, COALESCE(SUM(quantidade), 0) AS totalQuantidade " +
        "FROM sgc_postgres.public.TRANSACAO " +
        "GROUP BY tipo_pag";

    String insertSql = "INSERT INTO TRANSACAOTOTAL (tipo_pag, valor, quantidade) VALUES (?, ?, ?)";

    Map<String, Map<String, Object>> resultado = new HashMap<>();

    try (Connection conexao = Conexao.getConexao();
        PreparedStatement ps = conexao.prepareStatement(selectSql);
        ResultSet result = ps.executeQuery();
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
