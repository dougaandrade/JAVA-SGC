package DAO;

import aplicacao.Transacao;
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoDAO {

    protected PreparedStatement ps = null;

    public void cadastrarTransacao(Transacao transacao) {

        String sql = "INSERT INTO TRANSACAO (TIPOPAG, VALOR, DATA) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, transacao.getTipoPag());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getDateAberturaFormatada());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Extrato(Transacao extrato) {

        String sql = "SELECT tipoPag, COUNT(tipoPag) AS countTipoPag, SUM(valor) AS totalValor " +
                "FROM sgc_postgres.public.transacao GROUP BY tipoPag";
        try {
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            System.out.println("\n Extrato Parcial:");
            while (result.next()) {

                System.out.println("\n");
                System.out.println("Tipo do Pagamento: " + result.getString("tipoPag"));
                System.out.println("Quantidade de Recibo: " + result.getInt("countTipoPag"));
                System.out.println("Total em valor: " + result.getDouble("totalValor"));
                System.out.println("\n");
            }
            System.out.println("Esses foram os ultimos registros!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ExtratoDetalhado(Transacao extrato) {

        String sql = "SELECT idTransacao, tipoPag, valor, Data  FROM sgc_postgres.public.transacao";
        try {
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            System.out.println("\n Extrato Detalhado:");
            while (result.next()) {
                System.out.println("\n");
                System.out.println("Id Transacao: " + result.getString("idTransacao"));
                System.out.println("Tipo do Pagamento: " + result.getString("tipoPag"));
                System.out.println("Valor: " + result.getString("valor"));
                System.out.println("Data: " + result.getString("Data"));
                System.out.println("\n");
            }
            System.out.println("Esses foram os ultimos registros!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
