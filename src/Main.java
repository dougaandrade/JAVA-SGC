
import DAO.ProdutoDAO;
import DAO.TransacaoDAO;
import aplicacao.ProdutoRecord;
import aplicacao.TransacaoRecords;
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

			TransacaoRecords transacao = new TransacaoRecords(valor, tipoPag, new Date());
			new TransacaoDAO().cadastrarTransacao(transacao);
			listaDeContas.add(transacao);

			JOptionPane.showMessageDialog(null, "Transação realizada com sucesso!");

			CadastroPrd();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void CadastroPrd() {
		try {
			String idStr = JOptionPane.showInputDialog("Digite o ID do Produto:");
			if (idStr == null)
				return;
			int idProduto = Integer.parseInt(idStr);

			String nmProduto = JOptionPane.showInputDialog("Digite o nome do Produto:");
			if (nmProduto == null || nmProduto.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nome do produto não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String qtStr = JOptionPane.showInputDialog("Digite a quantidade do Produto:");
			if (qtStr == null)
				return;
			int qtProduto = Integer.parseInt(qtStr);

			ProdutoRecord produto = new ProdutoRecord(idProduto, nmProduto, qtProduto);
			new ProdutoDAO().cadastrarProduto(produto);

			JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Erro ao processar números!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void Extrato() {
		String[] opcoes = { "Parcial", "Detalhado", "Produto Detalhado", "Produto Total" };
		int escolha = JOptionPane.showOptionDialog(
				null, "Escolha o tipo de extrato:", "Extrato",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		switch (escolha) {
			case 0 -> new TransacaoDAO().extratoParcial();
			case 1 -> new TransacaoDAO().extratoDetalhado();
			case 2 -> new ProdutoDAO().dtProduto();
			case 3 -> new ProdutoDAO().dtProdutoSum();
			default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}
}
