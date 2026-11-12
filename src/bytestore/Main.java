package bytestore;

public class Main {
    public static void main(String[] args) {
        Produto teclado = new Produto(101, "Teclado USB", 120.00, 5);
        Produto mouse = new Produto(102, "Mouse USB", 80.00, 8);

        System.out.println("Produtos");
        System.out.println(teclado);
        System.out.println(mouse);

        Cliente ana = new Cliente(1, "Ana", "ana@exemplo.com");
        Cliente bruno = new Cliente(2, "Bruno", "bruno@exemplo.com");

        System.out.println();
        System.out.println("Clientes");
        System.out.println(ana);
        System.out.println(bruno);
    }
}
