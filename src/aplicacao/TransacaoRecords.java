package aplicacao;

import java.util.Date;

public record TransacaoRecords(Integer id_produto, double valor, String tipo_Pag, String tipo_prod, Integer quantidade,
    Date dataAberturaConta) {

}
