package Aplicacao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class TotalPay {
	
	protected String tipoPag;
	protected double sum;
	protected int qtd;
		
	//Transação
	public TotalPay(String tipoPag, double sum) {
		super();
		this.tipoPag = tipoPag;
		this.valor = valor;
		dataOperacao = new Date();
	}
	//valor transação
	public double getValor() {
		return valor;
	}

	public void setValor() {
		this.valor = valor;
	}
	
	//Tipo pagamento
	public String getTipoPag() {
		return tipoPag;
	}
	//Tipo pagamento
	public void setTipoPag() {
		this.tipoPag = tipoPag;
	}
	
	public void setTransacao(Double Valor, String tipoPag) {
		this.tipoPag = tipoPag;
	}
	
	
	//data formatada quando criar transação
	public String getDateTransacao() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(dataOperacao);
	}
	
	// exibir extrato
	public void exibirExtrato() {
		System.out.println("Tipo Pagamento: " + tipoPag + "\n" + "Valor: " + valor + "\n" + "Data da Operação: " + getDateTransacao() + "\n");
	}
	
	// public String toString() {
	// 	return "Tipo Pagamento: " + this.tipoPag + "\n" + "Valor: " + valor + "\n" + "Data de Abertura " + getDateTransacao();
		 
	// }

}
