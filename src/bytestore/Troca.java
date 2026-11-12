package bytestore;

import java.time.OffsetDateTime;

public class Troca {
    private static int ultimoId = 0;

    private final int id;
    private final Cliente cliente;
    private final Pedido pedidoOriginal;
    private final Devolucao devolucao;
    private final Pedido pedidoNovo;
    private final OffsetDateTime iniciadaEm;

    public Troca(Cliente cliente, Pedido pedidoOriginal, Devolucao devolucao, Pedido pedidoNovo) {
        ultimoId++;
        this.id = ultimoId;
        this.cliente = cliente;
        this.pedidoOriginal = pedidoOriginal;
        this.devolucao = devolucao;
        this.pedidoNovo = pedidoNovo;
        this.iniciadaEm = OffsetDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido getPedidoOriginal() {
        return pedidoOriginal;
    }

    public Devolucao getDevolucao() {
        return devolucao;
    }

    public Pedido getPedidoNovo() {
        return pedidoNovo;
    }

    public boolean isConcluida() {
        return pedidoNovo.getStatus() == StatusPedido.PAGO;
    }

    @Override
    public String toString() {
        return String.format("Troca %d | %s | pedido antigo %d -> devolução %d (%s) -> pedido novo %d (%s) | %s | %s",
                id, cliente.getNome(), pedidoOriginal.getId(), devolucao.getId(), Dinheiro.formatar(devolucao.getValor()),
                pedidoNovo.getId(), pedidoNovo.getStatus(), isConcluida() ? "concluída" : "nova compra pendente",
                DataHora.formatar(iniciadaEm));
    }
}
