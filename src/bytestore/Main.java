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
        System.out.println("8 - Consultar disponibilidade");
        System.out.println("9 - Abrir pedido");
        System.out.println("10 - Ver pedido");
        System.out.println("11 - Adicionar produto ao pedido");
        System.out.println("12 - Remover produto do pedido");
        System.out.println("13 - Aplicar desconto");
        System.out.println("14 - Confirmar pedido");
        System.out.println("15 - Pagar pedido");
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
            case 8 -> consultarProduto();
            case 9 -> abrirPedido();
            case 10 -> verPedido();
            case 11 -> adicionarItem();
            case 12 -> removerItem();
            case 13 -> aplicarDesconto();
            case 14 -> confirmarPedido();
            case 15 -> pagarPedido();
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
    private static void consultarProduto() {
        Produto produto = loja.localizarProduto(lerInteiro("Código do produto: "));
        System.out.printf("%s | %s | %d unidade(s) | %s%n", produto.getNome(),
                Dinheiro.formatar(produto.getPreco()), produto.getEstoque(), produto.getSituacao());
    }
    private static void abrirPedido() {
        Pedido pedido = loja.abrirPedido(lerInteiro("Código do cliente: "));
        System.out.println("Pedido " + pedido.getId() + " aberto para " + pedido.getCliente().getNome() + ".");
        exibirPedido(pedido);
    }

    private static void verPedido() {
        exibirPedido(loja.buscarPedido(lerInteiro("Número do pedido: ")));
    }

    private static void adicionarItem() {
        int idPedido = lerInteiro("Número do pedido: ");
        int codigo = lerInteiro("Código do produto: ");
        int quantidade = lerInteiro("Quantidade: ");
        loja.adicionarItem(idPedido, codigo, quantidade);
        exibirPedido(loja.buscarPedido(idPedido));
    }
    private static void removerItem() {
        int idPedido = lerInteiro("Número do pedido: ");
        int codigo = lerInteiro("Código do produto a remover: ");
        loja.removerItem(idPedido, codigo);
        exibirPedido(loja.buscarPedido(idPedido));
    }
    private static void aplicarDesconto() {
        int idPedido = lerInteiro("Número do pedido: ");
        int percentual = lerInteiro("Desconto (0, 10 ou 20): ");
        loja.aplicarDesconto(idPedido, percentual);
        exibirPedido(loja.buscarPedido(idPedido));
    }
    private static void confirmarPedido() {
        int idPedido = lerInteiro("Número do pedido: ");
        loja.confirmarPedido(idPedido);
        System.out.println("Pedido confirmado e estoque separado.");
        exibirPedido(loja.buscarPedido(idPedido));
    }
    private static void pagarPedido() {
        int idPedido = lerInteiro("Número do pedido: ");
        Pedido pedido = loja.buscarPedido(idPedido);
        System.out.println("Total a pagar: " + Dinheiro.formatar(pedido.getTotalAPagar()));
        double valor = lerValor("Valor recebido: ");
        loja.pagarPedido(idPedido, valor);
        System.out.println("Pagamento registrado.");
        exibirPedido(pedido);
    }
    private static void exibirPedido(Pedido pedido) {
        System.out.println("Pedido " + pedido.getId() + " | cliente: " + pedido.getCliente().getNome()
                + " | " + pedido.getStatus());
        System.out.println("Aberto em: " + DataHora.formatar(pedido.getAbertoEm()));
        if (pedido.getConfirmadoEm() != null) {
            System.out.println("Confirmado em: " + DataHora.formatar(pedido.getConfirmadoEm()));
        }
        if (pedido.getPagamento() != null) {
            System.out.println("Pago em: " + DataHora.formatar(pedido.getPagamento().getPagoEm())
                    + " | valor: " + Dinheiro.formatar(pedido.getPagamento().getValor()));
        }
        if (pedido.getItens().isEmpty()) {
            System.out.println("  (sem itens)");
        }
        for (ItemPedido item : pedido.getItens()) {
            System.out.printf("  %s | %d x %s | subtotal %s%n", item.getProduto().getNome(), item.getQuantidade(),
                    Dinheiro.formatar(item.getPrecoUnitario()), Dinheiro.formatar(item.getSubtotal()));
        }
        System.out.println("Total sem desconto: " + Dinheiro.formatar(pedido.getTotalSemDesconto()));
        System.out.println("Desconto: " + pedido.getDesconto() + "% | economia: " + Dinheiro.formatar(pedido.getEconomia()));
        System.out.println("Total a pagar: " + Dinheiro.formatar(pedido.getTotalAPagar()));
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
