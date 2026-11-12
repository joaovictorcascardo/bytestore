package bytestore;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataHora {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private DataHora() {
    }

    public static String formatar(OffsetDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(FORMATO);
    }

    public static OffsetDateTime ler(String texto) {
        try {
            return LocalDateTime.parse(texto.trim(), FORMATO).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("data/hora inválida, use o formato dd/MM/aaaa HH:mm:ss.");
        }
    }

    public static boolean dentroDoPeriodo(OffsetDateTime momento, OffsetDateTime inicio, OffsetDateTime fim) {
        boolean depoisDoInicio = inicio == null || !momento.isBefore(inicio);
        boolean antesDoFim = fim == null || !momento.isAfter(fim);
        return depoisDoInicio && antesDoFim;
    }
}
