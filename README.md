# ByteStore

Sistema de atendimento de uma loja de informática feito em Java, rodando no terminal. Resolvi uma lista de 20 exercícios de orientação a objetos do treinamento da Foursys, cada um acrescentando uma funcionalidade nova ao mesmo projeto. Cada exercício tem o seu commit, então dá pra acompanhar a evolução pelo histórico.

## O que o sistema faz

- Cadastro e consulta de produtos e clientes
- Reposição de estoque, alteração de preço e consulta de disponibilidade
- Pedidos com os estados ABERTO, CONFIRMADO, PAGO e CANCELADO
- Inclusão e remoção de itens, guardando o preço do momento em que o item entrou no pedido
- Desconto de 0%, 10% ou 20% com arredondamento por item
- Confirmação que só baixa o estoque se todos os itens tiverem estoque
- Pagamento em dinheiro, com crédito do cliente ou os dois juntos
- Cancelamento que devolve o estoque quando o pedido já estava confirmado
- Devolução parcial que gera crédito para o cliente
- Troca de produto (devolução + nova compra usando o crédito)
- Histórico do cliente com as datas de cada etapa
- Relatório de vendas e devoluções por período, com filtro por cliente
- Resumo final do atendimento ao encerrar

## Classes

| Classe | Responsabilidade |
| --- | --- |
| `Main` | Menu, leitura das entradas e exibição dos resultados |
| `Loja` | Guarda produtos, clientes, pedidos, devoluções e trocas, e coordena as operações |
| `Produto` | Dados do produto e controle de estoque e preço |
| `Cliente` | Dados do cliente e saldo de crédito |
| `Pedido` | Itens, desconto, confirmação, pagamento, cancelamento e devolução |
| `ItemPedido` | Produto, quantidade, preço registrado e unidades já devolvidas |
| `Pagamento` | Valor pago em crédito e em dinheiro, com data e hora |
| `Devolucao` | Registro de cada devolução e do crédito gerado |
| `Troca` | Liga o pedido antigo, a devolução e o pedido novo |
| `Relatorio` | Totais de um período |
| `StatusPedido` | Estados do pedido |
| `Dinheiro` e `DataHora` | Arredondamento, formatação de valores e de datas |

Os IDs de pedidos, devoluções e trocas são sequenciais e usam um contador `static` em cada classe. As listas são `LinkedList` e as datas são `OffsetDateTime`, gravadas no momento real de cada evento.

## Regras que valem pra tudo

- Operação recusada mostra o motivo e não altera nada.
- Valores aparecem com duas casas decimais.
- Nome, e-mail e motivo não podem ser só espaços.
- O sistema já começa com Teclado USB (101, R$ 120,00, 5 unidades), Mouse USB (102, R$ 80,00, 8 unidades) e os clientes Ana (1) e Bruno (2).

## Como rodar

Precisa do JDK 17 ou mais novo.

```bash
javac -encoding UTF-8 -d out src/bytestore/*.java
java -cp out bytestore.Main
```

Se os acentos aparecerem quebrados no terminal do Windows, rode com `java -Dstdout.encoding=UTF-8 -cp out bytestore.Main`. Também dá pra abrir a pasta no IntelliJ e rodar a classe `Main`.

Valores podem ser digitados com ponto ou vírgula (`120.50` ou `120,50`) e as datas do relatório no formato `dd/MM/aaaa HH:mm:ss`.
