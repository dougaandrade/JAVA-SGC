package Aplicacao;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Transacao {
	
	protected double valor;
	protected String tipoPag;
	protected Date dataAberturaConta;
		
	//Transação
	public Transacao(String tipoPag, double valor) {
		super();
		this.tipoPag = tipoPag;
		this.valor = valor;
		dataAberturaConta = new Date();
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
	
	
	//data formatada quando criar conta 
	public String getDateAberturaFormatada() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(dataAberturaConta);
	}
	
	// exibir extrato bancario
	public void exibirExtrato() {
		System.out.println("Tipo Pagamento: " + tipoPag + "\n" + "Valor : " + valor + "\n" + "Data da Operação " + getDateAberturaFormatada() + "\n");
	}
	
	public String toString() {
		return "Tipo Pagamento : " + this.tipoPag + "\n" + "Valor : " + valor + "\n" + "Data de Abertura " + getDateAberturaFormatada();
		 
	}

}
