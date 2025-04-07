
import java.awt.*;
import javax.swing.*;

public class MenuExec extends JFrame {

  public MenuExec() {
    // Configuração do JFrame
    setTitle("SGC - Sistema de Gestão");
    setSize(500, 350);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Layout e Painel
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4, 2, 10, 5));

    // Botões
    JButton btnInserir = new JButton("Nova Transação");
    JButton btnProduto = new JButton("Visualizar Produtos");
    JButton btnTransacao = new JButton("Visualizar Transações");
    JButton btnSair = new JButton("Sair");

    // Eventos dos botões
    btnInserir.addActionListener(_ -> Main.NovaTransacao());
    btnProduto.addActionListener(_ -> Main.Produto());
    btnTransacao.addActionListener(_ -> Main.Transacao());
    btnSair.addActionListener(_ -> System.exit(0));

    // Adicionando botões ao painel
    panel.add(btnInserir);
    panel.add(btnProduto);
    panel.add(btnTransacao);
    panel.add(btnSair);

    // Adicionando painel ao JFrame
    add(panel);
    setVisible(true);
  }
}
