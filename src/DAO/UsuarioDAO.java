package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Aplicacao.Usuario;
import conexao.Conexao;

public class UsuarioDAO {

    public void LoginColaborador(Usuario usuario) {

        String sql = "INSERT INTO USUARIO (LOGIN, SENHA) VALUES (?, ?)";

        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, usuario.getlogin());
            ps.setString(2, usuario.getsenha());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ColaboradorLog(Usuario colaboradorlog) {
        String sql = "SELECT idUsuario, login, senha FROM projeto.usuario";

        try {
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            System.out.println("\n Usuario:");
            while (result.next()) {
                System.out.println("\n");
                System.out.println("IdLogin: " + result.getInt("idTransacao"));
                System.out.println("Login: " + result.getInt("login"));
                System.out.println("Senha: " + result.getString("senha"));
                System.out.println("\n");
            }
            System.out.println("Login's registrados!");
            return;
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

}
