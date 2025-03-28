package aplicacao;

public class Produto {

    protected Integer id_Produto;
    protected String nm_Produto;
    protected Integer qt_Produto;

    public Produto(Integer id_Produto, String nm_Produto, Integer qt_Produto) {
        super();
        this.id_Produto = id_Produto;
        this.nm_Produto = nm_Produto;
        this.qt_Produto = qt_Produto;
    }

    public String getNmProduto() {
        return nm_Produto;
    }

    public String setNmProduto() {
        return nm_Produto;
    }

    public Integer getQtProdudo() {
        return qt_Produto;
    }

    public void setQtProdudo(Integer qt_Produto) {
        this.qt_Produto = qt_Produto;
    }

    public Integer getIdProdudo() {
        return id_Produto;
    }

    public void setIdProdudo(Integer id_Produto) {
        this.id_Produto = id_Produto;
    }

    public void setProduto(String nm_Produto, Integer qt_Produto, Integer id_Produto) {
        this.nm_Produto = nm_Produto;
        this.qt_Produto = qt_Produto;
        this.id_Produto = id_Produto;
    }
}
