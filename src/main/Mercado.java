package main;

import modelo.Produto;
import utils.Utils;

import java.util.*;

public class Mercado {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Produto> produtos;
    private static Map<Produto, Integer> carrinho;

    public static void main(String[] args) {
        produtos = new ArrayList<>();
        carrinho = new HashMap<>();
        menu();
    }

    private static void menu() {
        System.out.println("--------------------------------------------------");
        System.out.println("--------------Welcome to Super Store--------------");
        System.out.println("--------------------------------------------------");
        System.out.println("*** Selecione uma operação que deseja realizar ***");
        System.out.println("|     opção 1- Cadastrar    |");
        System.out.println("|     opção 2- Listar       |");
        System.out.println("|     opção 3- Comprar      |");
        System.out.println("|     opção 4- Carrinho     |");
        System.out.println("|     opção 5- Sair         |");

        int option = input.nextInt();

        switch (option) {
            case 1:
                cadastrarProdutos();
                break;
            case 2:
                listarProdutos();
                break;
            case 3:
                comprarProdutos();
                break;

            case 4:
                verCarrinho();
                break;
            case 5:
                System.out.println("Obrigado pela preferência, volte sempre!");
                System.exit(0);
            default:
                System.out.println("Opção Inválida!");
                menu();
                break;
        }
    }

    private static void cadastrarProdutos() {
        System.out.println("Nome do produto");
        String nome = input.next();

        System.out.println("Preço do produto");
        Double preco = input.nextDouble();

        Produto produto = new Produto(nome, preco);
        produtos.add(produto);

        System.out.println(produto.getNome() + " cadastrado com sucesso!");
        menu();
    }

    private static void listarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("lista de produtos! \n");
            for (Produto p : produtos) {
                System.out.println(p);
            }
        } else {
            System.out.println("Nenhum produto cadastrado!");
        }
        menu();

    }

    private static void comprarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("Código do produto: \n");
            System.out.println("--------------------Produtos Disponíveis -----------------");
            for (Produto p : produtos) {
                System.out.println(p + "\n");
            }
            int id = Integer.parseInt(input.next());
            boolean produtoExiste = false;

            for (Produto p : produtos) {
                if (p.getId() == id) {
                    int qtd = 0;
                    try {
                        qtd = carrinho.get(p);
                        // checa se o produto já está no carrinho, incrementa quantidade.
                        carrinho.put(p, qtd + 1);
                    } catch (NullPointerException e) {
                        // se o produto for o primeiro no carrinho.
                        carrinho.put(p, 1);
                    }
                    System.out.println(p.getNome() + " adicionado ao carrinho");
                    produtoExiste = true;
                }
            }

            if (produtoExiste) {
                System.out.println("Deseja adicionar outro produto ao carrinho?");
                System.out.println("Digite 1 para sim, ou 0 para finalizar a compra.\n");
                int option = Integer.parseInt(input.next());

                if (option == 1) {
                    comprarProdutos();
                } else finalizarCompra();
            } else {
                System.out.println("Produto não existe!");
                menu();
            }

        } else {
            System.out.println("Não existem produtos cadastrados");
            menu();
        }

    }

    private static void verCarrinho() {
        System.out.println("----Produtos no seu carrinho----");
        if (carrinho.size() > 0) {
            for (Produto p : carrinho.keySet()) {
                System.out.println("Produto" + p + "\nQuantidade" + carrinho.get(p));
            }
        } else {
            System.out.println("Carrinho vazio!");
        }
        menu();

    }

    private static void finalizarCompra() {
        Double valorFinal = 0.0;
        System.out.println("Seus produtos");

        for (Produto p : carrinho.keySet()) {
            int qtd = carrinho.get(p);
            valorFinal += p.getPreco() * qtd;
            System.out.println(p);
            System.out.println("Quantidade " + qtd);
            System.out.println("-------------------");

        }
        System.out.println("O valor da sua compra é: " + Utils.formatarParaReais(valorFinal));


        System.out.println("Inserir pagamento");

        try {
            Double pagamento = input.nextDouble();
            Double troco = pagamento - valorFinal;
            if(pagamento > valorFinal){
                System.out.println("Pagamento realizado com sucesso, seu troco é de: "+ Utils.formatarParaReais(troco));
            }
            else if (valorFinal > pagamento) {
                System.out.println("O valor inserido não é suficinte para efetuar o pagamento, por favor insera o valor correto!");
                finalizarCompra();
            } else{
                System.out.println("Pagamento efetuado com sucesso!");
            }
        } catch (InputMismatchException e){
            System.out.println("Forma de pagamento não valida!");
            input.remove();
            finalizarCompra();
        }

        carrinho.clear();
        System.out.println("Obrigado pela preferência!");
        menu();
    }
}