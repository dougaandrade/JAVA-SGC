package dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import model.ProdutoRecord;

public class ProdutoDAO {

    protected PreparedStatement ps = null;

    public void cadastrarProduto() {

        String sql = "INSERT INTO PRODUTO (nm_produto, qt_produto, valor_produto) VALUES (?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);

            String nmProduto = JOptionPane.showInputDialog("Digite o nome do Produto:");
            if (nmProduto == null || nmProduto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do produto não pode ser vazio.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setString(1, nmProduto);

            String qtProduto = JOptionPane.showInputDialog("Digite a quantidade do Produto:");
            if (qtProduto == null || qtProduto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Quantidade do produto nao pode ser vazio.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setInt(2, Integer.parseInt(qtProduto));

            String valorProduto = JOptionPane.showInputDialog("Digite o valor do Produto:");
            if (valorProduto == null || valorProduto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Valor do produto nao pode ser vazio.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setDouble(3, Double.parseDouble(valorProduto));

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

            ps.execute();
            ps.close();
        } catch (SQLException error) {
            System.out.println("Error: " + error);
        }
    }

    public void visualizarProdutos() {
        String selectSQL = "SELECT id_produto, nm_produto, qt_produto, valor_produto, data_cadastro FROM sgc_postgres.public.PRODUTO GROUP BY id_produto";

        String insertSQL = "INSERT INTO PRODUTOS_TOTAL (nm_produto, qt_produto, valor_produto, data_cadastro) VALUES (?, ?, ?, ?)";

        try {
            ps = Conexao.getConexao().prepareStatement(selectSQL);
            ResultSet result = ps.executeQuery();

            PreparedStatement insertPs = Conexao.getConexao().prepareStatement(insertSQL);

            while (result.next()) {
                String nm_produto = result.getString("nm_produto");
                int qt_produto = result.getInt("qt_produto");
                double valor_produto = result.getDouble("valor_produto");
                Timestamp data_cadastro = result.getTimestamp("data_cadastro");

                insertPs.setString(1, nm_produto);
                insertPs.setInt(2, qt_produto);
                insertPs.setDouble(3, valor_produto);
                insertPs.setTimestamp(4, data_cadastro);
                insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public List<ProdutoRecord> listarPorNome() {
        String selectSQL = "SELECT id_produto, nm_produto, qt_produto, valor_produto FROM sgc_postgres.public.PRODUTO ORDER BY nm_produto";
        Set<ProdutoRecord> produtos = new LinkedHashSet<>();

        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement prs = conexao.prepareStatement(selectSQL);
                ResultSet result = prs.executeQuery()) {

            while (result.next()) {
                int id_produto = result.getInt("id_produto");
                String nm_produto = result.getString("nm_produto");
                int qt_produto = result.getInt("qt_produto");
                double valor_produto = result.getDouble("valor_produto");

                produtos.add(new ProdutoRecord(id_produto, nm_produto, qt_produto, valor_produto));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e);
        }

        return new ArrayList<>(produtos);
    }

}
