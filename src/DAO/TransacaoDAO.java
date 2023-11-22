package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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



    
}
