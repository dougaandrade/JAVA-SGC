package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Aplicacao.Transacao;
import conexao.Conexao;

public class TransacaoDAO {
    
    public void cadastrarTransacao(Transacao transacao){

        String sql = "INSERT INTO TRANSACAO (TIPOPAG, VALOR, DATA) VALUES (?, ?, ?)";
    
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, transacao.getTipoPag());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getDateAberturaFormatada());
        
            ps.execute();
            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    
    }


    public void Extrato(Transacao extrato){
        
             String sql = "SELECT tipoPag, COUNT(tipoPag), SUM(valor) FROM projeto.transacao group by tipoPag";
        try{
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();
                

            while (result.next()) {
                System.out.println("\n");
                System.out.println("Tipo do Pagamento: " + result.getString("tipoPag")); 
                System.out.println("Quantidade de Recibo: " + result.getString("COUNT(tipoPag)"));
                System.out.println("Total em valor: " + result.getString("SUM(valor)"));
                System.out.println("\n");
            }
            System.out.println("Esses foram os ultimos registros!");
            return;
        }catch(Exception e){System.out.println(e);}
        return;
    }


}
