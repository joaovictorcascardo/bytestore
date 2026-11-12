package bytestore;

import java.time.OffsetDateTime;

public class Pedido {
    private static int ultimoId = 0;

    private final int id;
    private final Cliente cliente;
    private StatusPedido status;
    private final OffsetDateTime abertoEm;

    public Pedido(Cliente cliente) {
        ultimoId++;
        this.id = ultimoId;
        this.cliente = cliente;
        this.status = StatusPedido.ABERTO;
        this.abertoEm = OffsetDateTime.now();
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
}
