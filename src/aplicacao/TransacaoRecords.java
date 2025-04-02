package aplicacao;

import java.text.SimpleDateFormat;
import java.util.Date;

public record TransacaoRecords(Integer id_produto, double valor, String tipo_Pag, String tipo_prod, Integer quantidade,
    Date dataAberturaConta) {

  public String getDateAberturaFormatada() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(dataAberturaConta);
  }

}
