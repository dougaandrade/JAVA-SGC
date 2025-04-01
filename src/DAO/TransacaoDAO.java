package DAO;

import aplicacao.TransacaoRecords;
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoDAO {

    protected PreparedStatement ps = null;

    public void cadastrarTransacao(TransacaoRecords transacao) {

        String sql = "INSERT INTO TRANSACAO (TIPOPAG, VALOR, DATA) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, transacao.tipoPag());
            ps.setDouble(2, transacao.valor());
            ps.setString(3, transacao.getDateAberturaFormatada());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void transacaoParcial() {

        String selectSql = "SELECT tipoPag, COUNT(tipoPag) AS countTipoPag, SUM(valor) AS totalValor " +
                "FROM sgc_postgres.public.transacao GROUP BY tipoPag";

        String insertSql = "INSERT INTO TRANSACAOPARCIAL (TIPOPAG, VALOR, DATA) VALUES (?, ?, NOW())";

        try {
            ps = Conexao.getConexao().prepareStatement(selectSql);
            ResultSet result = ps.executeQuery();

            PreparedStatement insertPs = Conexao.getConexao().prepareStatement(insertSql);

            System.out.println("\n Extrato Parcial:");
            while (result.next()) {
                String tipoPag = result.getString("tipoPag");
                int countTipoPag = result.getInt("countTipoPag");
                double totalValor = result.getDouble("totalValor");

                System.out.println("\nTipo do Pagamento: " + tipoPag);
                System.out.println("Quantidade de Recibo: " + countTipoPag);
                System.out.println("Total em valor: " + totalValor);
                System.out.println("\n");

                insertPs.setString(1, tipoPag);
                insertPs.setDouble(2, totalValor);
                insertPs.executeUpdate();
            }

            System.out.println("Esses foram os últimos registros!");

        } catch (SQLException e) {
            System.out.println("Erro ao gerar extrato: " + e.getMessage());
        }
    }

    public void transacaoDetalhado() {

        String selectSql = "SELECT id, tipoPag, valor, Data  FROM sgc_postgres.public.transacao";

        String insertSql = "INSERT INTO TRANSACAODETALHADO (TIPOPAG, VALOR, DATA) VALUES (?, ?, NOW())";

        try {
            ps = Conexao.getConexao().prepareStatement(selectSql);
            ResultSet result = ps.executeQuery();

            PreparedStatement insertPs = Conexao.getConexao().prepareStatement(insertSql);

            System.out.println("\n Extrato Detalhado:");
            while (result.next()) {

                String tipoPag = result.getString("tipoPag");
                double valor = result.getDouble("valor");
                String data = result.getString("Data");

                System.out.println("\nTipo do Pagamento: " + tipoPag);
                System.out.println("Valor: " + valor);
                System.out.println("Data: " + data);
                System.out.println("\n");

                insertPs.setString(1, tipoPag);
                insertPs.setDouble(2, valor);
                insertPs.executeUpdate();
            }
            System.out.println("Esses foram os ultimos registros!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
