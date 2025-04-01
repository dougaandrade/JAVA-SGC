
import DAO.ProdutoDAO;
import DAO.TransacaoDAO;
import aplicacao.ProdutoRecord;
import aplicacao.TransacaoRecords;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Main {

	private static final List<TransacaoRecords> listaDeContas = new LinkedList<>();

	public static List<TransacaoRecords> getListaDeContas() {
		return listaDeContas;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MenuExec::new);
	}

	public static void InserirValor() {
		String valorStr = JOptionPane.showInputDialog("Insira o Valor:");
		if (valorStr == null)
			return;

		try {
			double valor = Double.parseDouble(valorStr);

			String[] opcoes = { "Dinheiro", "PIX", "Cartão de Crédito", "Cartão de Débito" };
			String tipoPag = (String) JOptionPane.showInputDialog(
					null, "Escolha a forma de pagamento:", "Pagamento",
					JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			if (tipoPag == null)
				return;

			List<ProdutoRecord> produtos = new ProdutoDAO().listarporNome();
			String[] opcoes2 = new String[produtos.size() + 1];
			for (int i = 0; i < produtos.size(); i++) {
				opcoes2[i] = produtos.get(i).nm_Produto() + " - " + produtos.get(i).qt_Produto();
			}
			String tipoProd = (String) JOptionPane.showInputDialog(
					null, "Escolha o produto:", "Produto",
					JOptionPane.QUESTION_MESSAGE, null, opcoes2, opcoes2[0]);

			if (tipoProd == null)
				return;

			TransacaoRecords transacao = new TransacaoRecords(valor, tipoPag, new Date());
			new TransacaoDAO().cadastrarTransacao(transacao);
			listaDeContas.add(transacao);

			JOptionPane.showMessageDialog(null, "Transação realizada com sucesso!");

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void CadastroPrd() {
		try {
			// Solicitar novo nome do produto
			String nmProduto = JOptionPane.showInputDialog("Digite o nome do Produto:");
			if (nmProduto == null || nmProduto.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nome do produto não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String qtStr = JOptionPane.showInputDialog("Digite a quantidade do Produto:");
			if (qtStr == null)
				return;

			JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Erro inesperado: " + e.getMessage());
		}
	}

	public static void Transacao() {
		String[] opcoes = { "Transação Parcial", "Transação Detalhada", "Voltar" };
		int escolha = JOptionPane.showOptionDialog(
				null, "Escolha o tipo de extrato:", "Extrato",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		switch (escolha) {
			case 0 -> new TransacaoDAO().transacaoParcial();
			case 1 -> new TransacaoDAO().transacaoDetalhado();
			case 4 -> main(opcoes);
			default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}

	public static void Produto() {
		String[] opcoes = { "Cadastrar Produto", "Produto Detalhado", "Produto Parcial", "Voltar" };
		int escolha = JOptionPane.showOptionDialog(
				null, "Escolha o tipo de extrato:", "Extrato",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		switch (escolha) {
			case 0 -> CadastroPrd();
			case 1 -> new ProdutoDAO().produtoDetalhado();
			case 2 -> new ProdutoDAO().produtoParcial();
			case 3 -> main(opcoes);
			default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}
}
