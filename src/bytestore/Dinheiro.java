package bytestore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class Dinheiro {
    private static final Locale BRASIL = Locale.forLanguageTag("pt-BR");

    private Dinheiro() {
    }

    public static double arredondar(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean iguais(double a, double b) {
        return Math.round(a * 100) == Math.round(b * 100);
    }

    public static String formatar(double valor) {
        String texto = String.format(BRASIL, "R$ %,.2f", Math.abs(valor));
        return valor < 0 ? "-" + texto : texto;
    }
}
