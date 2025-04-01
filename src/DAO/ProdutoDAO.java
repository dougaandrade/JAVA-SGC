package DAO;

import aplicacao.ProdutoRecord;
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProdutoDAO {

    protected PreparedStatement ps = null;

    public void cadastrarProduto(ProdutoRecord produto) {

        String sql = "INSERT INTO produto (id_produto, nm_produto, qt_produto) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, produto.id_Produto());
            ps.setString(2, produto.nm_Produto());
            ps.setInt(3, produto.qt_Produto());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

    }

    public void produtoDetalhado() {

        String selectSQL = "SELECT id_produto, nm_produto, qt_produto FROM sgc_postgres.public.produto";

        String insertSQL = "INSERT INTO PRODUTODETALHADO (id_produto, nm_produto, qt_produto) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(selectSQL);
            ResultSet result = ps.executeQuery();

            PreparedStatement insertPs = Conexao.getConexao().prepareStatement(insertSQL);

            System.out.println("\nProduto Detalhado:");
            while (result.next()) {
                int id_produto = result.getInt("id_produto");
                String nm_produto = result.getString("nm_produto");
                int qt_produto = result.getInt("qt_produto");

                // System.out.println("\n");
                // System.out.println("Código do Produto: " + id_produto);
                // System.out.println("Nome Produto: " + nm_produto);
                // System.out.println("Quantidade Total dos Produtos: " + qt_produto);
                // System.out.println("\n");

                insertPs.setInt(1, id_produto);
                insertPs.setString(2, nm_produto);
                insertPs.setInt(3, qt_produto);
                insertPs.executeUpdate();
            }
            System.out.println("Esses foram os últimos produtos!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void produtoParcial() {

        String selectSQL = "SELECT id_produto, nm_produto, SUM(qt_produto) AS qt_produto   FROM sgc_postgres.public.produto GROUP BY id_produto";

        String insertSQL = "INSERT INTO PRODUTOPARCIAL (id_produto, nm_produto, qt_produto) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(selectSQL);
            ResultSet result = ps.executeQuery();

            PreparedStatement insertPs = Conexao.getConexao().prepareStatement(insertSQL);

            System.out.println("\nProduto Parcial:");
            while (result.next()) {

                int id_produto = result.getInt("id_produto");
                String nm_produto = result.getString("nm_produto");
                int qt_produto = result.getInt("qt_produto");

                // System.out.println("\n");
                // System.out.println("Código do Produto: " + id_produto);
                // System.out.println("Nome Produto: " + nm_produto);
                // System.out.println("Quantidade Total dos Produtos: " + qt_produto);

                insertPs.setInt(1, id_produto);
                insertPs.setString(2, nm_produto);
                insertPs.setInt(3, qt_produto);
                insertPs.executeUpdate();
            }
            System.out.println("Esses foram os últimos produtos!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<ProdutoRecord> listarporNome() {
        String selectSQL = "SELECT nm_produto, qt_produto FROM sgc_postgres.public.produto";
        List<ProdutoRecord> produtos = new LinkedList<>();

        try {
            ps = Conexao.getConexao().prepareStatement(selectSQL);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String nm_produto = result.getString("nm_produto");
                int qt_produto = result.getInt("qt_produto");

                ProdutoRecord produto = new ProdutoRecord(qt_produto, nm_produto, qt_produto);
                produtos.add(produto);
            }
        } catch (

        SQLException e) {
            System.out.println(e);
        }

        return produtos;
    }

}
