package controller;

import arquivo.Arquivo;
import interfaces.Hash;
import interfaces.Tree;

import java.util.Date;
import java.util.List;

public class Controller {
    private Hash hash;
    private Tree tree;
    private String stringBusca;

    public Controller(Hash hash, Tree tree, String stringBusca) {
        this.hash = hash;
        this.tree = tree;
        this.stringBusca = stringBusca;
    }

    public List<Arquivo> buscarNome() {
        List<Arquivo> searchResult = hash.searchByName(stringBusca);
        return searchResult;
    }

    public List<Arquivo> buscarKeyword() {
        List<Arquivo> searchResult = hash.searchByKeyword(stringBusca);
        return searchResult;
    }

    public void ordenarNome(int num) {
        List<Arquivo> searchResult = buscarNome();
        if (searchResult.isEmpty()) {
            System.err.println("Nenhum resultado encontrado.");
            return;
        }

        for (Arquivo arquivo : searchResult) {
            switch (num) {
                case 1:
                    tree.put(arquivo.getNome(), arquivo);
                    break;
                case 2:
                    tree.put(arquivo.getCaminho(), arquivo);
                    break;
                case 3:
                    tree.put(arquivo.getTipo(), arquivo);
                    break;
                case 4:
                    tree.put(arquivo.getTamanho(), arquivo);
                    break;
                case 5:
                    tree.put(arquivo.getDataCriacao(), arquivo);
                    break;
                case 6:
                    tree.put(arquivo.getDataModificacao(), arquivo);
                    break;
                default:
                    System.err.println("Opção inválida");
                    break;
            }
        }
    }

    public void ordenarKeyword(int num) {
        List<Arquivo> searchResult = buscarKeyword();
        if (searchResult.isEmpty()) {
            System.err.println("Nenhum resultado encontrado.");
            return;
        }

        for (Arquivo arquivo : searchResult) {
            switch (num) {
                case 1:
                    if (arquivo.getNome() instanceof String) {
                        tree.put((String) arquivo.getNome(), arquivo);
                    }
                    break;
                case 2:
                    if (arquivo.getCaminho() instanceof String) {
                        tree.put((String) arquivo.getCaminho(), arquivo);
                    }
                    break;
                case 3:
                    if (arquivo.getTipo() instanceof String) {
                        tree.put((String) arquivo.getTipo(), arquivo);
                    }
                    break;
                case 4:
                    tree.put((Long) arquivo.getTamanho(), arquivo);
                    break;
                case 5:
                    tree.put((Date) arquivo.getDataCriacao(), arquivo);
                    break;
                case 6:
                    tree.put((Date) arquivo.getDataModificacao(), arquivo);
                    break;
                default:
                    System.err.println("Opção inválida");
                    break;
            }
        }
    }
}
