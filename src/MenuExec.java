
import java.awt.*;
import javax.swing.*;

public class MenuExec extends JFrame {

  public MenuExec() {
    // Configuração do JFrame
    setTitle("SGC - Sistema de Gestão");
    setSize(400, 300);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Layout e Painel
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1, 10, 10));

    // Botões
    JButton btnInserir = new JButton("Nova Operação");
    JButton btnExtrato = new JButton("Visualizar Extrato");
    JButton btnSair = new JButton("Sair");

    // Eventos dos botões
    btnInserir.addActionListener(e -> Main.InserirValor()); // Método da Main
    btnExtrato.addActionListener(e -> Main.Extrato()); // Método da Main
    btnSair.addActionListener(e -> System.exit(0));

    // Adicionando botões ao painel
    panel.add(btnInserir);
    panel.add(btnExtrato);
    panel.add(btnSair);

    // Adicionando painel ao JFrame
    add(panel);
    setVisible(true);
  }
}
