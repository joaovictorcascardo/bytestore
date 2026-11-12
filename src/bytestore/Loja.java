package bytestore;

import java.util.LinkedList;

public class Loja {
    private final LinkedList<Produto> produtos = new LinkedList<>();

    public void carregarDadosIniciais() {
        produtos.add(new Produto(101, "Teclado USB", 120.00, 5));
        produtos.add(new Produto(102, "Mouse USB", 80.00, 8));
    }

    public Produto cadastrarProduto(int codigo, String nome, double preco, int estoque) {
        if (buscarProduto(codigo) != null) {
            throw new IllegalArgumentException("já existe um produto com o código " + codigo + ".");
        }
        Produto produto = new Produto(codigo, nome, preco, estoque);
        produtos.add(produto);
        return produto;
    }

    public Produto buscarProduto(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    public LinkedList<Produto> getProdutos() {
        return produtos;
    }
}
