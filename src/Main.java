import DAO.ProdutoDAO;
import DAO.TransacaoDAO;
import aplicacao.Produto;
import aplicacao.Transacao;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import menus.MenuExtrato;
import menus.MenuHome;
import menus.MenuPay;

public class Main {

	private static final List<Transacao> listaDeContas = new LinkedList<>();

	public static List<Transacao> getListaDeContas() {
		return listaDeContas;
	}

	public static void main(String[] args) {
		// Initialize scanner once and pass it to methods
		try (Scanner entrada = new Scanner(System.in)) {
			int opcao;
			do {
				MenuHome.GerarMenu();
				opcao = entrada.nextInt();
				entrada.nextLine(); // Consume the newline character left by nextInt()

				switch (opcao) {
					case 1 -> InserirValor(entrada); // Pass the scanner here
					case 2 -> Extrato(entrada); // Pass the scanner here
					case 3 -> System.out.println("\t\n SGC Volte Sempre! \n");
					default -> System.out.println("Opcao Invalida!.");
				}
			} while (opcao != 0);
		}
	}

	private static void InserirValor(Scanner criar) {
		System.out.println("\t\n [Nova Operação] \n");
		try {
			System.out.println("Insira o Valor:");
			double valor = Double.parseDouble(criar.nextLine());

			System.out.println("\n[Forma de Pagamento]");
			System.out.println("(ENTER para exibir as formas de pagamento)");
			String tipoPag = criar.nextLine();

			int opcao2;
			do {
				MenuPay.payMethods();
				opcao2 = Integer.parseInt(criar.nextLine());

				switch (opcao2) {
					case 1 -> tipoPag = "Dinheiro";
					case 2 -> tipoPag = "PIX";
					case 3 -> tipoPag = "Cartao de Credito";
					case 4 -> tipoPag = "Cartao de Debito";
					default -> System.out.println("\t\n Opcão invalida!\n");
				}
			} while (tipoPag == null || tipoPag.isEmpty());

			Transacao transacao = new Transacao(tipoPag, valor);
			transacao.setTransacao(valor, tipoPag);
			new TransacaoDAO().cadastrarTransacao(transacao);
			listaDeContas.add(transacao);
		} catch (NumberFormatException e) {
			System.out.println("Valor Invalido!");
		}
		CadastroPrd(criar); // Pass the scanner here
	}

	private static void CadastroPrd(Scanner criar) {
		try {
			System.out.println("Digite o id do Produto: \n");
			int idProduto = Integer.parseInt(criar.nextLine());

			System.out.println("Digite o nome do Produto: \n");
			String nmProduto = criar.nextLine();

			if (nmProduto.isEmpty()) {
				System.out.println("Nome do produto não pode ser vazio.");
				return;
			}

			System.out.println("Digite a quantidade de Produto: \n");
			int qtProduto = Integer.parseInt(criar.nextLine());

			Produto produto = new Produto(idProduto, nmProduto, qtProduto);
			produto.setProduto(nmProduto, qtProduto, idProduto);
			new ProdutoDAO().cadastrarProduto(produto);
			System.out.println("\n [Realizado com Sucesso!] \n");
		} catch (NumberFormatException e) {
			System.out.println("Erro ao processar números: " + e.getMessage());
		}
	}

	private static void Extrato(Scanner criar) {

		MenuExtrato.GerarMenuExtrato();

		int opcao3 = 0;
		try {
			opcao3 = Integer.parseInt(criar.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Erro ao processar a opção: " + e.getMessage());
		}

		do {
			switch (opcao3) {
				case 1 -> ExtratoParcial();
				case 2 -> ExtratoDetalhado();
				case 3 -> ExtratoProdutoDetalhado();
				case 4 -> ExtratoProdutoTotal();
				default -> System.out.println("\t\n Opcão invalida!\n");
			}
		} while (opcao3 == 0);
	}

	private static void ExtratoParcial() {
		Transacao exibirExtrato = null;
		new TransacaoDAO().Extrato(exibirExtrato);
	}

	public static void ExtratoDetalhado() {
		Transacao exibirExtrato = null;
		new TransacaoDAO().ExtratoDetalhado(exibirExtrato);
	}

	private static void ExtratoProdutoDetalhado() {
		Produto exibirProdutoDetalhado = null;
		new ProdutoDAO().dtProduto(exibirProdutoDetalhado);
	}

	private static void ExtratoProdutoTotal() {
		Produto exibirProduto = null;
		new ProdutoDAO().dtProdutoSum(exibirProduto);
	}
}
