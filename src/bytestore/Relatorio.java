package bytestore;

import java.time.OffsetDateTime;
import java.util.LinkedList;

public class Relatorio {
    private int pedidosPagos;
    private double totalPago;
    private double dinheiroRecebido;
    private double creditoUtilizado;
    private double totalDevolvido;

    public Relatorio(LinkedList<Pedido> pedidos, LinkedList<Devolucao> devolucoes,
                     OffsetDateTime inicio, OffsetDateTime fim, Cliente cliente) {
        if (inicio != null && fim != null && inicio.isAfter(fim)) {
            throw new IllegalArgumentException("o início do período não pode ser depois do fim.");
        }
        for (Pedido pedido : pedidos) {
            Pagamento pagamento = pedido.getPagamento();
            boolean doCliente = cliente == null || pedido.getCliente() == cliente;
            if (pagamento != null && doCliente && DataHora.dentroDoPeriodo(pagamento.getPagoEm(), inicio, fim)) {
                pedidosPagos++;
                totalPago += pagamento.getValorTotal();
                dinheiroRecebido += pagamento.getValorDinheiro();
                creditoUtilizado += pagamento.getValorCredito();
            }
        }
        for (Devolucao devolucao : devolucoes) {
            boolean doCliente = cliente == null || devolucao.getPedido().getCliente() == cliente;
            if (doCliente && DataHora.dentroDoPeriodo(devolucao.getRegistradaEm(), inicio, fim)) {
                totalDevolvido += devolucao.getValor();
            }
        }
    }

    public int getPedidosPagos() {
        return pedidosPagos;
    }

    public double getTotalPago() {
        return Dinheiro.arredondar(totalPago);
    }

    public double getDinheiroRecebido() {
        return Dinheiro.arredondar(dinheiroRecebido);
    }

    public double getCreditoUtilizado() {
        return Dinheiro.arredondar(creditoUtilizado);
    }

    public double getTotalDevolvido() {
        return Dinheiro.arredondar(totalDevolvido);
    }

    public double getResultadoComercial() {
        return Dinheiro.arredondar(totalPago - totalDevolvido);
    }
}
