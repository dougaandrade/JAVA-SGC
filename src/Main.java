
import DAO.ProdutoDAO;
import DAO.TransacaoDAO;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MenuExec::new);
	}

	public static void Novacompra() {
		new TransacaoDAO().cadastrarTransacao();
	}

	public static void Transacao() {
		String[] opcoes = { "Transação Total", "Voltar" };
		int escolha = JOptionPane.showOptionDialog(
				null, "Escolha o tipo de extrato:", "Extrato",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes,
				opcoes[0]);

		switch (escolha) {
			case 0 -> new TransacaoDAO().transacaoTotal();
			case 1 -> main(opcoes);
			default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}

	public static void Produto() {
		String[] opcoes = { "Cadastrar Produto", "Produtos Cadastrados", "Voltar" };
		int escolha = JOptionPane.showOptionDialog(
				null, "Escolha o tipo de extrato:", "Extrato",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		switch (escolha) {
			case 0 -> new ProdutoDAO().cadastrarProduto();
			case 1 -> new ProdutoDAO().visualizarProdutos();
			case 2 -> main(opcoes);
			default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}

}
