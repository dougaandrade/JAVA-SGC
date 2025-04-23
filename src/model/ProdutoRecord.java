package model;

import java.time.LocalDate;

public record ProdutoRecord(Integer id_produto, String nm_produto, Integer qt_produto, double valor_produto,
        LocalDate data_cadastro) {

    // Construtor reduzido para facilitar o uso da classe
    public ProdutoRecord(Integer id_produto, String nomeProduto, Integer quantidadeProduto, double valor_produto) {
        this(id_produto, nomeProduto, quantidadeProduto, valor_produto, null);
    }
}
