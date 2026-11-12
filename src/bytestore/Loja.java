package bytestore;

import java.util.LinkedList;

public class Loja {
    private final LinkedList<Produto> produtos = new LinkedList<>();
    private final LinkedList<Cliente> clientes = new LinkedList<>();

    public void carregarDadosIniciais() {
        produtos.add(new Produto(101, "Teclado USB", 120.00, 5));
        produtos.add(new Produto(102, "Mouse USB", 80.00, 8));
        clientes.add(new Cliente(1, "Ana", "ana@exemplo.com"));
        clientes.add(new Cliente(2, "Bruno", "bruno@exemplo.com"));
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

    public Produto localizarProduto(int codigo) {
        Produto produto = buscarProduto(codigo);
        if (produto == null) {
            throw new IllegalArgumentException("produto " + codigo + " não encontrado.");
        }
        return produto;
    }

    public void reporEstoque(int codigoProduto, int quantidade) {
        localizarProduto(codigoProduto).reporEstoque(quantidade);
    }
    public void alterarPreco(int codigoProduto, double novoPreco) {
        localizarProduto(codigoProduto).alterarPreco(novoPreco);
    }
    public Cliente cadastrarCliente(int codigo, String nome, String email) {
        if (buscarCliente(codigo) != null) {
            throw new IllegalArgumentException("já existe um cliente com o código " + codigo + ".");
        }
        Cliente cliente = new Cliente(codigo, nome, email);
        clientes.add(cliente);
        return cliente;
    }

    public Cliente buscarCliente(int codigo) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente localizarCliente(int codigo) {
        Cliente cliente = buscarCliente(codigo);
        if (cliente == null) {
            throw new IllegalArgumentException("cliente " + codigo + " não encontrado.");
        }
        return cliente;
    }
    public LinkedList<Produto> getProdutos() {
        return produtos;
    }

    public LinkedList<Cliente> getClientes() {
        return clientes;
    }
}
