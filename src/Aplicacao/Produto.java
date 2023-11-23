package Aplicacao;

public class Produto {

    protected Integer idProduto;
    protected String nmProduto;
    protected Integer qtProduto;

	public Produto(Integer idProduto, String nmProduto, Integer qtProduto) {
		super();
		this.idProduto = idProduto;
		this.nmProduto = nmProduto;
		this.qtProduto = qtProduto;
	}

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto() {
        this.nmProduto = nmProduto;
    }

    public Integer getQtProtudo() {
        return qtProduto;
    }

    public void setQtProtudo() {
        this.qtProduto = qtProduto;
    }

    public Integer getidProtudo() {
        return idProduto;
    }

    public void setidProtudo() {
        this.idProduto = idProduto;
    }

    public void setProduto(Integer idProduto, String nmProduto, Integer qtProduto) {
		this.nmProduto = nmProduto;
	}
}
