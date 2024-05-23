package main;

import arquivo.Arquivo;
import arvores.AVLTree;
import arvores.BinaryTree;
import arvores.RBTree;
import controller.Controller;
import dataset.Leitor;
import hash.HashEncadeado;
import hash.HashQuadratico;
import interfaces.Hash;
import interfaces.Tree;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Arquivo[] arquivos = Leitor.lerDataset();

            Hash hash;

            System.out.println("Escolha o hash:");
            System.out.println("1 - Hash quadrático");
            System.out.println("2 - Hash encadeado");
            int inputHash = scanner.nextInt();

            if (inputHash == 1) {
                hash = new HashQuadratico(10000);
            } else if (inputHash == 2) {
                hash = new HashEncadeado();
            } else {
                System.err.println("Opção inválida");
                return;
            }

            for (Arquivo arquivo : arquivos) {
                hash.put(arquivo.getNome(), arquivo);
            }

            System.out.println("Escolha o critério da busca:");
            System.out.println("1 - Busca pelo nome do arquivo");
            System.out.println("2 - Busca por palavra-chave");
            int inputCriterioBusca = scanner.nextInt();

            String stringBusca;
            if (inputCriterioBusca == 1) {
                System.out.println("Digite o nome do arquivo:");
                scanner.nextLine();
                stringBusca = scanner.nextLine();
            } else if (inputCriterioBusca == 2) {
                System.out.println("Digite a palavra-chave:");
                scanner.nextLine();
                stringBusca = scanner.nextLine();
            } else {
                System.err.println("Opção inválida");
                return;
            }

            Tree tree;

            System.out.println("Escolha a árvore:");
            System.out.println("1 - Árvore binária");
            System.out.println("2 - Árvore AVL");
            System.out.println("3 - Árvore rubro-negra");
            int inputArvore = scanner.nextInt();

            if (inputArvore == 1) {
                tree = new BinaryTree<>();
            } else if (inputArvore == 2) {
                tree = new AVLTree<>();
            } else if (inputArvore == 3) {
                tree = new RBTree<>();
            } else {
                System.err.println("Opção inválida");
                return;
            }

            System.out.println("Escolha o critério para ordenação dos arquivos:");
            System.out.println("1 - Nome");
            System.out.println("2 - Caminho");
            System.out.println("3 - Tipo");
            System.out.println("4 - Tamanho");
            System.out.println("5 - Data de criação");
            System.out.println("6 - Data de modificação");
            int inputCriterioOrdenacao = scanner.nextInt();

            Controller controller = new Controller(hash, tree, stringBusca);

            if (inputCriterioBusca == 1) {
                controller.ordenarNome(inputCriterioOrdenacao);
            } else {
                controller.ordenarKeyword(inputCriterioOrdenacao);
            }

            tree.printTree();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }
    }

}
