package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Aplicacao.Produto;
import Aplicacao.Transacao;
import conexao.Conexao;

public class ProdutoDAO {

    public void cadastrarProduto(Produto produto){

        String sql = "INSERT INTO produto (idProduto, nmProduto, qtProduto) VALUES (?, ?, ?)";
    
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, produto.getidProtudo());
            ps.setString(2, produto.getNmProduto());
            ps.setInt(3, produto.getQtProtudo());
        
            ps.execute();
            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    
    }


    public void dtProduto(Produto produto){
        
            String sql = "SELECT idproduto, nmProduto, qtProduto FROM projeto.produto";
       try{
           PreparedStatement ps = null;
           ps = Conexao.getConexao().prepareStatement(sql);

           ResultSet result = ps.executeQuery();
               
           System.out.println("\n Extrato Detalhado:");
           while (result.next()) {
               System.out.println("\n");
               System.out.println("Id Transacao: " + result.getString("idproduto")); 
               System.out.println("Tipo do Pagamento: " + result.getString("nmProduto")); 
               System.out.println("Valor: " + result.getString("qtProduto"));
               System.out.println("\n");
           }
           System.out.println("Esses foram os ultimos produtos!");
           return;
       }catch(Exception e){System.out.println(e);}
       return;
   }

        public void dtProdutoSum(Produto produto){
                
            String sql = "SELECT idproduto, nmProduto, SUM(qtProduto) FROM projeto.produto group by idproduto";
        try{
        PreparedStatement ps = null;
        ps = Conexao.getConexao().prepareStatement(sql);

        ResultSet result = ps.executeQuery();
            

            System.out.println("\n Extrato Parcial:");
        while (result.next()) {
            System.out.println("\n");
            System.out.println("Código do produto: " + result.getString("idproduto")); 
            System.out.println("Nome Produto: " + result.getString("nmProduto"));
            System.out.println("Quantidades dos Produtos: " + result.getInt("SUM(qtProduto)"));
            System.out.println("\n");
        }
        System.out.println("Esses foram os ultimos produtos!");
        return;
        }catch(Exception e){System.out.println(e);}
        return;
        }
}
