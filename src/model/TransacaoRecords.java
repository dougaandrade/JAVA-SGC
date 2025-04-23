package model;

import java.util.Date;

public record TransacaoRecords(Integer id_produto, String tipo_Pag, String tipo_prod, Integer quantidade,
        Date dataAberturaConta) {

    public TransacaoRecords(Integer id_produto, String tipo_Pag, String tipo_prod, Integer quantidade) {
        this(id_produto, tipo_Pag, tipo_prod, quantidade, null);
    }

}
