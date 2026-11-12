package bytestore;

public class Cliente {
    private final int codigo;
    private final String nome;
    private final String email;
    private boolean ativo;
    private double credito;

    public Cliente(int codigo, String nome, String email) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("o código do cliente precisa ser positivo.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("o nome do cliente não pode ficar vazio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("o e-mail do cliente não pode ficar vazio.");
        }
        this.codigo = codigo;
        this.nome = nome.trim();
        this.email = email.trim();
        this.ativo = true;
        this.credito = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public double getCredito() {
        return credito;
    }

    @Override
    public String toString() {
        return String.format("%d - %s (%s) | %s | crédito: %s", codigo, nome, email,
                ativo ? "ativo" : "inativo", Dinheiro.formatar(credito));
    }
}
