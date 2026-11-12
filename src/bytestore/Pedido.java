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
