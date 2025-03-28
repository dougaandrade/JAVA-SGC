package DAO;

import aplicacao.Produto;
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    protected PreparedStatement ps = null;

    // Method to register a product in the database
    public void cadastrarProduto(Produto produto) {

        String sql = "INSERT INTO produto (id_produto, nm_produto, qt_produto) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, produto.getIdProdudo()); // Corrected method name
            ps.setString(2, produto.getNmProduto()); // Corrected method name
            ps.setInt(3, produto.getQtProdudo()); // Corrected method name

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

    }

    // Method to retrieve detailed product information
    public void dtProduto(Produto produto) {

        String sql = "SELECT id_produto, nm_produto, qt_produto FROM sgc_postgres.public.produto";
        try {
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            System.out.println("\nExtrato Detalhado:");
            while (result.next()) {
                System.out.println("\n");
                System.out.println("Id Produto: " + result.getInt("id_produto"));
                System.out.println("Nome Produto: " + result.getString("nm_produto"));
                System.out.println("Quantidade Produto: " + result.getInt("qt_produto"));
                System.out.println("\n");
            }
            System.out.println("Esses foram os últimos produtos!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Method to retrieve the sum of product quantities
    public void dtProdutoSum(Produto produto) {

        String sql = "SELECT id_produto, nm_produto, SUM(qt_produto) FROM sgc_postgres.public.produto GROUP BY id_produto";
        try {
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            System.out.println("\nExtrato Parcial:");
            while (result.next()) {
                System.out.println("\n");
                System.out.println("Código do Produto: " + result.getInt("id_produto"));
                System.out.println("Nome Produto: " + result.getString("nm_produto"));
                System.out.println("Quantidade Total dos Produtos: " + result.getInt("SUM(qt_produto)"));
                System.out.println("\n");
            }
            System.out.println("Esses foram os últimos produtos!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
