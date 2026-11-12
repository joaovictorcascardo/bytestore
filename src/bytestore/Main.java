package bytestore;

import java.util.Scanner;

public class Main {
    private static final Scanner leitor = new Scanner(System.in);
    private static final Loja loja = new Loja();

    public static void main(String[] args) {
        loja.carregarDadosIniciais();
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Opção: ");
            try {
                executar(opcao);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Operação recusada: " + e.getMessage());
            }
        } while (opcao != 0);
        leitor.close();
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("========== ByteStore ==========");
        System.out.println("1 - Listar produtos");
        System.out.println("2 - Cadastrar produto");
        System.out.println("0 - Sair");
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> listarProdutos();
            case 2 -> cadastrarProduto();
            case 0 -> System.out.println("Encerrando o atendimento.");
            default -> System.out.println("Opção inválida, tente de novo.");
        }
    }

    private static void listarProdutos() {
        System.out.println("Catálogo:");
        for (Produto produto : loja.getProdutos()) {
            System.out.println(produto);
        }
    }

    private static void cadastrarProduto() {
        int codigo = lerInteiro("Código: ");
        String nome = lerTexto("Nome: ");
        double preco = lerValor("Preço: ");
        int estoque = lerInteiro("Estoque inicial: ");
        Produto produto = loja.cadastrarProduto(codigo, nome, preco, estoque);
        System.out.println("Produto cadastrado: " + produto);
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return Integer.parseInt(leitor.nextLine().trim());
    }

    private static double lerValor(String mensagem) {
        System.out.print(mensagem);
        return Double.parseDouble(leitor.nextLine().trim().replace(",", "."));
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return leitor.nextLine();
    }
}
