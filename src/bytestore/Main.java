package bytestore;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static final Scanner leitor = new Scanner(System.in);
    private static final Loja loja = new Loja();

    public static void main(String[] args) {
        loja.carregarDadosIniciais();
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
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
        System.out.println("============ ByteStore ============");
        System.out.println("Produtos");
        System.out.println("  1 - Listar produtos");
        System.out.println("  2 - Cadastrar produto");
        System.out.println("  6 - Repor estoque");
        System.out.println("  7 - Alterar preço");
        System.out.println("  8 - Consultar disponibilidade");
        System.out.println("Clientes");
        System.out.println("  3 - Listar clientes");
        System.out.println("  4 - Cadastrar cliente");
        System.out.println("  5 - Buscar cliente");
        System.out.println("  16 - Histórico do cliente");
        System.out.println("Pedidos");
        System.out.println("  9 - Abrir pedido");
        System.out.println("  10 - Ver pedido");
        System.out.println("  11 - Adicionar produto ao pedido");
        System.out.println("  12 - Remover produto do pedido");
        System.out.println("  13 - Aplicar desconto");
        System.out.println("  14 - Confirmar pedido");
        System.out.println("  15 - Pagar pedido");
        System.out.println("  17 - Cancelar pedido");
        System.out.println("Pós-venda");
        System.out.println("  18 - Registrar devolução");
        System.out.println("  19 - Trocar produto");
        System.out.println("  20 - Ver trocas");
        System.out.println("Gestão");
        System.out.println("  21 - Relatório por período");
        System.out.println("0 - Encerrar atendimento");
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
            case 16 -> historicoDoCliente();
            case 17 -> cancelarPedido();
            case 18 -> registrarDevolucao();
            case 19 -> trocarProduto();
            case 20 -> listarTrocas();
            case 21 -> relatorioPorPeriodo();
            case 0 -> exibirResumoFinal();
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
        Cliente cliente = pedido.getCliente();
        System.out.println("Total a pagar: " + Dinheiro.formatar(pedido.getTotalAPagar()));
        boolean usarCredito = false;
        if (cliente.getCredito() > 0) {
            System.out.println("Crédito disponível de " + cliente.getNome() + ": " + Dinheiro.formatar(cliente.getCredito()));
            usarCredito = lerTexto("Usar crédito? (s/n): ").trim().equalsIgnoreCase("s");
        }
        double restante = Dinheiro.arredondar(pedido.getTotalAPagar() - pedido.calcularCreditoUsado(usarCredito));
        System.out.println("Valor a receber em dinheiro: " + Dinheiro.formatar(restante));
        double valor = lerValor("Valor em dinheiro: ");
        loja.pagarPedido(idPedido, usarCredito, valor);
        System.out.println("Pagamento registrado. Saldo de crédito de " + cliente.getNome() + ": "
                + Dinheiro.formatar(cliente.getCredito()));
        exibirPedido(pedido);
    }
    private static void historicoDoCliente() {
        int codigo = lerInteiro("Código do cliente: ");
        LinkedList<Pedido> historico = loja.historicoDoCliente(codigo);
        if (historico.isEmpty()) {
            System.out.println("Esse cliente ainda não tem pedidos.");
            return;
        }
        for (Pedido pedido : historico) {
            System.out.println("--------------------------------");
            exibirPedido(pedido);
        }
    }
    private static void cancelarPedido() {
        int idPedido = lerInteiro("Número do pedido: ");
        String motivo = lerTexto("Motivo: ");
        loja.cancelarPedido(idPedido, motivo);
        System.out.println("Pedido cancelado.");
        exibirPedido(loja.buscarPedido(idPedido));
    }
    private static void registrarDevolucao() {
        int idPedido = lerInteiro("Número do pedido pago: ");
        int codigo = lerInteiro("Código do produto: ");
        int quantidade = lerInteiro("Quantidade devolvida: ");
        String motivo = lerTexto("Motivo: ");
        Devolucao devolucao = loja.registrarDevolucao(idPedido, codigo, quantidade, motivo);
        System.out.println("Devolução registrada: " + devolucao);
        Cliente cliente = devolucao.getPedido().getCliente();
        System.out.println("Crédito de " + cliente.getNome() + ": " + Dinheiro.formatar(cliente.getCredito()));
        exibirPedido(devolucao.getPedido());
    }
    private static void trocarProduto() {
        int codigoCliente = lerInteiro("Código do cliente: ");
        System.out.println("Compras pagas:");
        for (Pedido pedido : loja.historicoDoCliente(codigoCliente)) {
            if (pedido.getStatus() == StatusPedido.PAGO) {
                exibirPedido(pedido);
            }
        }
        int idPedido = lerInteiro("Número do pedido a trocar: ");
        int codigoDevolvido = lerInteiro("Código do produto devolvido: ");
        int quantidadeDevolvida = lerInteiro("Quantidade devolvida: ");
        String motivo = lerTexto("Motivo: ");
        Troca troca = loja.iniciarTroca(codigoCliente, idPedido, codigoDevolvido, quantidadeDevolvida, motivo);
        Cliente cliente = troca.getCliente();
        Pedido pedidoNovo = troca.getPedidoNovo();
        System.out.println("Devolução aprovada: " + troca.getDevolucao());
        System.out.println("Crédito disponível: " + Dinheiro.formatar(cliente.getCredito()));
        System.out.println("Novo pedido " + pedidoNovo.getId() + " aberto para o produto substituto.");
        try {
            int codigoNovo = lerInteiro("Código do produto substituto: ");
            int quantidadeNova = lerInteiro("Quantidade: ");
            loja.adicionarItem(pedidoNovo.getId(), codigoNovo, quantidadeNova);
            loja.confirmarPedido(pedidoNovo.getId());
            double restante = Dinheiro.arredondar(pedidoNovo.getTotalAPagar() - pedidoNovo.calcularCreditoUsado(true));
            System.out.println("Total da nova compra: " + Dinheiro.formatar(pedidoNovo.getTotalAPagar()));
            System.out.println("Diferença a pagar em dinheiro: " + Dinheiro.formatar(restante));
            double dinheiro = lerValor("Valor em dinheiro: ");
            loja.pagarPedido(pedidoNovo.getId(), true, dinheiro);
            System.out.println("Troca concluída. Crédito restante de " + cliente.getNome() + ": "
                    + Dinheiro.formatar(cliente.getCredito()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("A nova compra não foi concluída: " + e.getMessage());
            System.out.println("A devolução " + troca.getDevolucao().getId() + " continua valendo e o crédito de "
                    + Dinheiro.formatar(cliente.getCredito()) + " segue disponível para " + cliente.getNome() + ".");
            System.out.println("O pedido " + pedidoNovo.getId() + " ficou " + pedidoNovo.getStatus()
                    + " e pode ser continuado pelo menu.");
        }
        exibirPedido(pedidoNovo);
    }

    private static void listarTrocas() {
        if (loja.getTrocas().isEmpty()) {
            System.out.println("Nenhuma troca registrada.");
        }
        for (Troca troca : loja.getTrocas()) {
            System.out.println(troca);
        }
    }
    private static void relatorioPorPeriodo() {
        OffsetDateTime inicio = DataHora.ler(lerTexto("Início (dd/MM/aaaa HH:mm:ss): "));
        OffsetDateTime fim = DataHora.ler(lerTexto("Fim (dd/MM/aaaa HH:mm:ss): "));
        int codigoCliente = lerInteiro("Código do cliente (0 para todos): ");
        Relatorio relatorio = loja.gerarRelatorio(inicio, fim, codigoCliente);
        System.out.println("Relatório de " + DataHora.formatar(inicio) + " até " + DataHora.formatar(fim));
        exibirRelatorio(relatorio);
    }

    private static void exibirRelatorio(Relatorio relatorio) {
        System.out.println("Pedidos pagos: " + relatorio.getPedidosPagos());
        System.out.println("Total pago (dinheiro + crédito): " + Dinheiro.formatar(relatorio.getTotalPago()));
        System.out.println("Dinheiro recebido: " + Dinheiro.formatar(relatorio.getDinheiroRecebido()));
        System.out.println("Crédito utilizado: " + Dinheiro.formatar(relatorio.getCreditoUtilizado()));
        System.out.println("Devoluções: " + Dinheiro.formatar(relatorio.getTotalDevolvido()));
        System.out.println("Resultado comercial: " + Dinheiro.formatar(relatorio.getResultadoComercial()));
    }
    private static void exibirResumoFinal() {
        System.out.println();
        System.out.println("========== Resumo do atendimento ==========");
        System.out.println("Estoque:");
        for (Produto produto : loja.getProdutos()) {
            System.out.println("  " + produto.getNome() + ": " + produto.getEstoque());
        }
        System.out.println("Crédito dos clientes:");
        for (Cliente cliente : loja.getClientes()) {
            System.out.println("  " + cliente.getNome() + ": " + Dinheiro.formatar(cliente.getCredito()));
        }
        System.out.println("Pedidos por situação:");
        for (StatusPedido status : StatusPedido.values()) {
            System.out.println("  " + status + ": " + loja.contarPedidos(status));
        }
        exibirRelatorio(loja.gerarRelatorio(null, null, 0));
        System.out.println("Atendimento encerrado.");
    }

    private static void exibirPedido(Pedido pedido) {
        System.out.println("Pedido " + pedido.getId() + " | cliente: " + pedido.getCliente().getNome()
                + " | " + pedido.getStatus());
        System.out.println("Aberto em: " + DataHora.formatar(pedido.getAbertoEm()));
        if (pedido.getConfirmadoEm() != null) {
            System.out.println("Confirmado em: " + DataHora.formatar(pedido.getConfirmadoEm()));
        }
        if (pedido.getCanceladoEm() != null) {
            System.out.println("Cancelado em: " + DataHora.formatar(pedido.getCanceladoEm())
                    + " | motivo: " + pedido.getMotivoCancelamento());
        }
        if (pedido.getPagamento() != null) {
            Pagamento pagamento = pedido.getPagamento();
            System.out.println("Pago em: " + DataHora.formatar(pagamento.getPagoEm()) + " | total: "
                    + Dinheiro.formatar(pagamento.getValorTotal()) + " (crédito " + Dinheiro.formatar(pagamento.getValorCredito())
                    + " + dinheiro " + Dinheiro.formatar(pagamento.getValorDinheiro()) + ")");
        }
        if (pedido.getItens().isEmpty()) {
            System.out.println("  (sem itens)");
        }
        for (ItemPedido item : pedido.getItens()) {
            System.out.printf("  %s | %d x %s | subtotal %s%n", item.getProduto().getNome(), item.getQuantidade(),
                    Dinheiro.formatar(item.getPrecoUnitario()), Dinheiro.formatar(item.getSubtotal()));
            if (item.getQuantidadeDevolvida() > 0) {
                System.out.printf("    devolvidas: %d | ainda podem ser devolvidas: %d%n",
                        item.getQuantidadeDevolvida(), item.getQuantidadeDevolvivel());
            }
        }
        System.out.println("Total sem desconto: " + Dinheiro.formatar(pedido.getTotalSemDesconto()));
        System.out.println("Desconto: " + pedido.getDesconto() + "% | economia: " + Dinheiro.formatar(pedido.getEconomia()));
        System.out.println("Total a pagar: " + Dinheiro.formatar(pedido.getTotalAPagar()));
    }
    private static int lerOpcao() {
        try {
            return lerInteiro("Opção: ");
        } catch (NumberFormatException e) {
            return -1;
        }
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
