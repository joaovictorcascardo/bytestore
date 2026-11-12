package bytestore;

import java.time.OffsetDateTime;
import java.util.LinkedList;

public class Pedido {
    private static int ultimoId = 0;

    private final int id;
    private final Cliente cliente;
    private final LinkedList<ItemPedido> itens = new LinkedList<>();
    private int desconto;
    private StatusPedido status;
    private final OffsetDateTime abertoEm;
    private OffsetDateTime confirmadoEm;
    private Pagamento pagamento;
    private OffsetDateTime canceladoEm;
    private String motivoCancelamento;

    public Pedido(Cliente cliente) {
        ultimoId++;
        this.id = ultimoId;
        this.cliente = cliente;
        this.desconto = 0;
        this.status = StatusPedido.ABERTO;
        this.abertoEm = OffsetDateTime.now();
    }

    public void adicionarItem(Produto produto, int quantidade) {
        verificarAberto();
        if (quantidade <= 0) {
            throw new IllegalArgumentException("a quantidade precisa ser maior que zero.");
        }
        ItemPedido existente = buscarItem(produto.getCodigo());
        if (existente != null) {
            existente.adicionarQuantidade(quantidade);
        } else {
            itens.add(new ItemPedido(produto, quantidade));
        }
    }

    public void removerItem(int codigoProduto) {
        verificarAberto();
        ItemPedido item = buscarItem(codigoProduto);
        if (item == null) {
            throw new IllegalArgumentException("o produto " + codigoProduto + " não está no pedido " + id + ".");
        }
        itens.remove(item);
    }
    public ItemPedido buscarItem(int codigoProduto) {
        for (ItemPedido item : itens) {
            if (item.getProduto().getCodigo() == codigoProduto) {
                return item;
            }
        }
        return null;
    }

    public double getTotalSemDesconto() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return Dinheiro.arredondar(total);
    }

    public void aplicarDesconto(int percentual) {
        verificarAberto();
        if (percentual != 0 && percentual != 10 && percentual != 20) {
            throw new IllegalArgumentException("só são aceitos descontos de 0%, 10% ou 20%.");
        }
        this.desconto = percentual;
    }

    public double getTotalAPagar() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getTotalComDesconto(desconto);
        }
        return Dinheiro.arredondar(total);
    }

    public double getEconomia() {
        return Dinheiro.arredondar(getTotalSemDesconto() - getTotalAPagar());
    }

    public int getDesconto() {
        return desconto;
    }

    public void confirmar() {
        verificarAberto();
        if (itens.isEmpty()) {
            throw new IllegalStateException("o pedido " + id + " não tem itens.");
        }
        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();
            if (!produto.temEstoque(item.getQuantidade())) {
                throw new IllegalStateException("estoque insuficiente de " + produto.getNome() + ": pedido "
                        + item.getQuantidade() + ", disponível " + produto.getEstoque() + ".");
            }
        }
        for (ItemPedido item : itens) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
        confirmadoEm = OffsetDateTime.now();
        status = StatusPedido.CONFIRMADO;
    }

    public double calcularCreditoUsado(boolean usarCredito) {
        if (!usarCredito) {
            return 0;
        }
        return Dinheiro.arredondar(Math.min(cliente.getCredito(), getTotalAPagar()));
    }

    public void pagar(boolean usarCredito, double valorDinheiro) {
        if (status != StatusPedido.CONFIRMADO) {
            throw new IllegalStateException("só pedido CONFIRMADO pode ser pago. O pedido " + id + " está " + status + ".");
        }
        double credito = calcularCreditoUsado(usarCredito);
        double restante = Dinheiro.arredondar(getTotalAPagar() - credito);
        if (!Dinheiro.iguais(valorDinheiro, restante)) {
            throw new IllegalArgumentException("o valor em dinheiro precisa ser exatamente " + Dinheiro.formatar(restante) + ".");
        }
        cliente.usarCredito(credito);
        pagamento = new Pagamento(credito, valorDinheiro);
        status = StatusPedido.PAGO;
    }

    public void cancelar(String motivo) {
        if (status == StatusPedido.PAGO) {
            throw new IllegalStateException("pedido pago não pode ser cancelado, faça uma devolução.");
        }
        if (status == StatusPedido.CANCELADO) {
            throw new IllegalStateException("o pedido " + id + " já está cancelado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("informe o motivo do cancelamento.");
        }
        if (status == StatusPedido.CONFIRMADO) {
            for (ItemPedido item : itens) {
                item.getProduto().devolverAoEstoque(item.getQuantidade());
            }
        }
        motivoCancelamento = motivo.trim();
        canceladoEm = OffsetDateTime.now();
        status = StatusPedido.CANCELADO;
    }

    public Devolucao devolver(int codigoProduto, int quantidade, String motivo) {
        if (status != StatusPedido.PAGO) {
            throw new IllegalStateException("só dá para devolver produtos de pedido PAGO. O pedido " + id + " está " + status + ".");
        }
        ItemPedido item = buscarItem(codigoProduto);
        if (item == null) {
            throw new IllegalArgumentException("o produto " + codigoProduto + " não faz parte do pedido " + id + ".");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("informe o motivo da devolução.");
        }
        item.registrarDevolucao(quantidade);
        double valor = Dinheiro.arredondar(item.getPrecoComDesconto(desconto) * quantidade);
        item.getProduto().devolverAoEstoque(quantidade);
        cliente.adicionarCredito(valor);
        return new Devolucao(this, item.getProduto(), quantidade, valor, motivo);
    }

    public OffsetDateTime getCanceladoEm() {
        return canceladoEm;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public OffsetDateTime getConfirmadoEm() {
        return confirmadoEm;
    }

    private void verificarAberto() {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("o pedido " + id + " está " + status + " e não pode mais ser alterado.");
        }
    }
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public OffsetDateTime getAbertoEm() {
        return abertoEm;
    }

    public LinkedList<ItemPedido> getItens() {
        return itens;
    }
}
