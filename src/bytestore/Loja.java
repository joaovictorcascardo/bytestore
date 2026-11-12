package bytestore;

import java.util.LinkedList;

public class Loja {
    private final LinkedList<Produto> produtos = new LinkedList<>();
    private final LinkedList<Cliente> clientes = new LinkedList<>();
    private final LinkedList<Pedido> pedidos = new LinkedList<>();
    private final LinkedList<Devolucao> devolucoes = new LinkedList<>();

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
    public Pedido abrirPedido(int codigoCliente) {
        Cliente cliente = localizarCliente(codigoCliente);
        if (!cliente.isAtivo()) {
            throw new IllegalStateException("o cliente " + cliente.getNome() + " está inativo.");
        }
        Pedido pedido = new Pedido(cliente);
        pedidos.add(pedido);
        return pedido;
    }

    public Pedido buscarPedido(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        throw new IllegalArgumentException("pedido " + id + " não encontrado.");
    }
    public void adicionarItem(int idPedido, int codigoProduto, int quantidade) {
        Pedido pedido = buscarPedido(idPedido);
        Produto produto = localizarProduto(codigoProduto);
        pedido.adicionarItem(produto, quantidade);
    }
    public void removerItem(int idPedido, int codigoProduto) {
        buscarPedido(idPedido).removerItem(codigoProduto);
    }
    public void aplicarDesconto(int idPedido, int percentual) {
        buscarPedido(idPedido).aplicarDesconto(percentual);
    }
    public void confirmarPedido(int idPedido) {
        buscarPedido(idPedido).confirmar();
    }
    public void pagarPedido(int idPedido, double valor) {
        buscarPedido(idPedido).pagar(valor);
    }
    public LinkedList<Pedido> historicoDoCliente(int codigoCliente) {
        Cliente cliente = localizarCliente(codigoCliente);
        LinkedList<Pedido> historico = new LinkedList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente() == cliente) {
                historico.add(pedido);
            }
        }
        return historico;
    }
    public void cancelarPedido(int idPedido, String motivo) {
        buscarPedido(idPedido).cancelar(motivo);
    }
    public Devolucao registrarDevolucao(int idPedido, int codigoProduto, int quantidade, String motivo) {
        Devolucao devolucao = buscarPedido(idPedido).devolver(codigoProduto, quantidade, motivo);
        devolucoes.add(devolucao);
        return devolucao;
    }
    public LinkedList<Produto> getProdutos() {
        return produtos;
    }

    public LinkedList<Cliente> getClientes() {
        return clientes;
    }

    public LinkedList<Devolucao> getDevolucoes() {
        return devolucoes;
    }
}
