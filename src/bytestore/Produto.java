package bytestore;

public class Produto {
    private final int codigo;
    private final String nome;
    private double preco;
    private int estoque;

    public Produto(int codigo, String nome, double preco, int estoque) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("o código do produto precisa ser positivo.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("o nome do produto não pode ficar vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("o preço precisa ser maior que zero.");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("o estoque não pode ser negativo.");
        }
        this.codigo = codigo;
        this.nome = nome.trim();
        this.preco = Dinheiro.arredondar(preco);
        this.estoque = estoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void reporEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("a quantidade de reposição precisa ser maior que zero.");
        }
        estoque += quantidade;
    }
    public void alterarPreco(double novoPreco) {
        if (novoPreco <= 0) {
            throw new IllegalArgumentException("o novo preço precisa ser maior que zero.");
        }
        preco = Dinheiro.arredondar(novoPreco);
    }
    public String getSituacao() {
        return estoque > 0 ? "disponível" : "sem estoque";
    }
    @Override
    public String toString() {
        return String.format("%d - %s | %s | estoque: %d", codigo, nome, Dinheiro.formatar(preco), estoque);
    }
}
