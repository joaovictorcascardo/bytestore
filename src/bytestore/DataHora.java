package bytestore;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DataHora {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private DataHora() {
    }

    public static String formatar(OffsetDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(FORMATO);
    }
}
