package bytestore;

public class ItemPedido {
    private final Produto produto;
    private int quantidade;
    private final double precoUnitario;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("a quantidade precisa ser maior que zero.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public void adicionarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("a quantidade precisa ser maior que zero.");
        }
        this.quantidade += quantidade;
    }

    public double getPrecoComDesconto(int percentual) {
        return Dinheiro.arredondar(precoUnitario * (100 - percentual) / 100.0);
    }

    public double getTotalComDesconto(int percentual) {
        return Dinheiro.arredondar(getPrecoComDesconto(percentual) * quantidade);
    }
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubtotal() {
        return Dinheiro.arredondar(precoUnitario * quantidade);
    }
}
