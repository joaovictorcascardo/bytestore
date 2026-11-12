package bytestore;

import java.time.OffsetDateTime;

public class Devolucao {
    private static int ultimoId = 0;

    private final int id;
    private final Pedido pedido;
    private final Produto produto;
    private final int quantidade;
    private final double valor;
    private final String motivo;
    private final OffsetDateTime registradaEm;

    public Devolucao(Pedido pedido, Produto produto, int quantidade, double valor, String motivo) {
        ultimoId++;
        this.id = ultimoId;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = Dinheiro.arredondar(valor);
        this.motivo = motivo.trim();
        this.registradaEm = OffsetDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

    public String getMotivo() {
        return motivo;
    }

    public OffsetDateTime getRegistradaEm() {
        return registradaEm;
    }

    @Override
    public String toString() {
        return String.format("Devolução %d | pedido %d | %d x %s | %s | motivo: %s | %s", id, pedido.getId(),
                quantidade, produto.getNome(), Dinheiro.formatar(valor), motivo, DataHora.formatar(registradaEm));
    }
}
