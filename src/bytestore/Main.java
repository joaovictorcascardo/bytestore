package bytestore;

public class Main {
    public static void main(String[] args) {
        Produto teclado = new Produto(101, "Teclado USB", 120.00, 5);
        Produto mouse = new Produto(102, "Mouse USB", 80.00, 8);

        System.out.println("Ficha do produto 1");
        System.out.println(teclado);
        System.out.println();
        System.out.println("Ficha do produto 2");
        System.out.println(mouse);
    }
}
