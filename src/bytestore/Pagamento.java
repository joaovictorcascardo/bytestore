package bytestore;

import java.time.OffsetDateTime;

public class Pagamento {
    private final double valorCredito;
    private final double valorDinheiro;
    private final OffsetDateTime pagoEm;

    public Pagamento(double valorCredito, double valorDinheiro) {
        this.valorCredito = Dinheiro.arredondar(valorCredito);
        this.valorDinheiro = Dinheiro.arredondar(valorDinheiro);
        this.pagoEm = OffsetDateTime.now();
    }

    public double getValorCredito() {
        return valorCredito;
    }

    public double getValorDinheiro() {
        return valorDinheiro;
    }

    public double getValorTotal() {
        return Dinheiro.arredondar(valorCredito + valorDinheiro);
    }

    public OffsetDateTime getPagoEm() {
        return pagoEm;
    }
}
