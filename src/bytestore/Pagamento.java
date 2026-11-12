package bytestore;

import java.time.OffsetDateTime;

public class Pagamento {
    private final double valor;
    private final OffsetDateTime pagoEm;

    public Pagamento(double valor) {
        this.valor = Dinheiro.arredondar(valor);
        this.pagoEm = OffsetDateTime.now();
    }

    public double getValor() {
        return valor;
    }

    public OffsetDateTime getPagoEm() {
        return pagoEm;
    }
}
