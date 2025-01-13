
import java.util.Scanner;

import Aplicacao.Produto;
import Aplicacao.Transacao;
import DAO.TransacaoDAO;
import DAO.ProdutoDAO;

import java.io.Console;
import java.util.ArrayList;

public class Main {

	public static ArrayList<Transacao> listaDeContas = new ArrayList<>();

    public static void main(String[] args) throws Exception {
    	//switch case do menu principal//
        int opcao;
        Scanner entrada = new Scanner(System.in);

        do {
            MenuHome.GerarMenu();
            opcao = entrada.nextInt();

            switch (opcao) {
            	case 1 :
            		InserirValor();
                    break;
                    
            	case 2 :
            		Extrato();
            		break;
                    
                case 3 :
                    System.out.println("\t\n SGC Volte Sempre! \n");
                    break;

                default:
                    System.out.println("Opcao Invalida!.");
            }
        } while (opcao != 0);
    }
	
    // switch case para inserir valor
    private static void InserirValor() {
    	
    	System.out.println("\t\n [Nova Operação] \n");
		Scanner criar = new Scanner(System.in);
		
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
			
			case 1: 
				tipoPag = "\n" + "Dinheiro";
			break;
			
			case 2: 
				tipoPag = "\n" + "PIX";
			break;
			
			case 3: 
				tipoPag = "\n" + "Cartao de Credito";
				break;
				
			case 4: 
				tipoPag = "\n" + "Cartao de Debito";
				break;
			
			default:
                System.out.println("\t\n Opcão invalida!\n");
			}
		}while (tipoPag == null);
		
			Transacao transacao = new Transacao(tipoPag, valor);
			transacao.setTransacao(valor, tipoPag);
			new TransacaoDAO().cadastrarTransacao(transacao);
			listaDeContas.add(transacao);
			CadastroPrd();
	}

		private static void CadastroPrd() {	
			Scanner criar = new Scanner(System.in);

			int idProduto;
			String nmProduto;
			int qtProduto;
			
				System.out.println("digite o id do Produto: \n");
				idProduto = Integer.parseInt(criar.nextLine());

				System.out.println("digite o nome do Produto: \n");
				nmProduto = criar.nextLine();
				
				System.out.println("digite o quantidade de Produto: \n");
				qtProduto = Integer.parseInt(criar.nextLine());
		

			Produto produto = new Produto(idProduto, nmProduto, qtProduto);
			produto.setProduto(idProduto, nmProduto, qtProduto);
			new ProdutoDAO().cadastrarProduto(produto);

			System.out.println("\n [Realizado com Sucesso!] \n");
		
	}
	
    //puxar extrato da conta
    public static void Extrato() {
        Transacao exibirExtrato = null;

		System.out.println("\n");
		System.out.println("1. Extrato Parcial");
		System.out.println("2. Extrato Detalhado");
		System.out.println("3. Extrato Produto Detalhado");
		System.out.println("4. Extrato Produto Total");
		System.out.println("\n [Insira Opção] \n");

		int opcao3;
		Scanner criar = new Scanner(System.in);
		opcao3 = Integer.parseInt(criar.nextLine());
		do {
			switch (opcao3) {
			
			case 1: 
			ExtratoParcial();
			break;

			case 2: 
			ExtratoDetalhado();
			break;
			
			case 3: 
			ExtratoProdutoDetalhado();
			break;

			case 4: 
			ExtratoProdutoTotal();
			break;

			default:
                System.out.println("\t\n Opcão invalida!\n");
			}
		}while (opcao3 == 0);
        
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
