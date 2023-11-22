
import java.util.Scanner;

import Aplicacao.Transacao;
import DAO.TransacaoDAO;

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
                    System.out.println("Op��o Invalida!.");
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
			MenuPay.GerarMenu2();
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
		
	}
	
    //puxar extrato da conta
    public static void Extrato() {
        Transacao exibirExtrato = null;

		new TransacaoDAO().Extrato(exibirExtrato);

        // for (Transacao conta : listaDeContas) {
        // 	conta.exibirExtrato();
        // 	}
        // System.out.println("\n\t Esses são os ultimos lancamentos!\n");
    }
}
