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
        System.out.println("3 - Listar clientes");
        System.out.println("4 - Cadastrar cliente");
        System.out.println("5 - Buscar cliente");
        System.out.println("6 - Repor estoque");
        System.out.println("7 - Alterar preço");
        System.out.println("0 - Sair");
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> listarProdutos();
            case 2 -> cadastrarProduto();
            case 3 -> listarClientes();
            case 4 -> cadastrarCliente();
            case 5 -> buscarCliente();
            case 6 -> reporEstoque();
            case 7 -> alterarPreco();
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

    private static void listarClientes() {
        System.out.println("Clientes:");
        for (Cliente cliente : loja.getClientes()) {
            System.out.println(cliente);
        }
    }

    private static void cadastrarCliente() {
        int codigo = lerInteiro("Código: ");
        String nome = lerTexto("Nome: ");
        String email = lerTexto("E-mail: ");
        Cliente cliente = loja.cadastrarCliente(codigo, nome, email);
        System.out.println("Cliente cadastrado: " + cliente);
    }

    private static void buscarCliente() {
        Cliente cliente = loja.localizarCliente(lerInteiro("Código do cliente: "));
        System.out.println(cliente);
    }
    private static void reporEstoque() {
        int codigo = lerInteiro("Código do produto: ");
        int quantidade = lerInteiro("Quantidade recebida: ");
        Produto produto = loja.localizarProduto(codigo);
        int anterior = produto.getEstoque();
        loja.reporEstoque(codigo, quantidade);
        System.out.println("Estoque de " + produto.getNome() + ": " + anterior + " -> " + produto.getEstoque());
    }
    private static void alterarPreco() {
        int codigo = lerInteiro("Código do produto: ");
        double novoPreco = lerValor("Novo preço: ");
        loja.alterarPreco(codigo, novoPreco);
        System.out.println("Preço atualizado: " + loja.localizarProduto(codigo));
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
