package aplicacao;

import java.text.SimpleDateFormat;
import java.util.Date;

public record TransacaoRecords(double valor, String tipoPag, Date dataAberturaConta) {

  public String getDateAberturaFormatada() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(dataAberturaConta);
  }

}
