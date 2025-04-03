package aplicacao;

import java.time.LocalDate;

public record ProdutoRecord(Integer id_produto, String nm_produto, Integer qt_produto, Double valor_produto,
        LocalDate data_cadastro) {

    // Construtor reduzido com nome e quantidade apenas
    public ProdutoRecord(Integer id_produto, String nomeProduto, Integer quantidadeProduto) {
        this(id_produto, nomeProduto, quantidadeProduto, null, null);
    }
}
