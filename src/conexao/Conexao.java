package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/sgc_postgres"; // Verifique a URL
    private static final String USER = "root"; // Verifique o usuário
    private static final String PASSWORD = "root"; // Verifique a senha
    private static Connection conn;

    public static Connection getConexao() {
        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Tentando conectar ao banco...");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão bem-sucedida!");
            }
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }
}
