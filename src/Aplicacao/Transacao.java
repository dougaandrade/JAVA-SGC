package Aplicacao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
