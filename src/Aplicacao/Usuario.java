package Aplicacao;

public class Usuario {

    private int login;
    private String senha;

    public Usuario(int login, String senha) {

        this.login = login;
        this.senha = senha;
    }

    public int getlogin() {
        return login;
    }

    public void setlogin() {
        this.login = login;
    }

    public String getsenha() {
        return senha;
    }

    public void setsenha() {
        this.senha = senha;
    }

    public void setUsuario(int login, String senha) {
        this.login = login;
    }
}
