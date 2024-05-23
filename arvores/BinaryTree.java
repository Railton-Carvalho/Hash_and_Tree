package arvores;

import arquivo.Arquivo;
import interfaces.Tree;

public class BinaryTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private static class Node<Key extends Comparable<Key>, Value> {
        private Key chave;
        private Value valor;
        private Node<Key, Value> esq;
        private Node<Key, Value> dir;

        Node(Key chave, Value valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    private Node<Key, Value> raiz;

    @Override
    public boolean isEmpty() {
        return raiz == null;
    }

    @Override
    public int tamanho() {
        return tamanho(raiz);
    }

    private int tamanho(Node<Key, Value> no) {
        return (no == null) ? 0 : 1 + tamanho(no.esq) + tamanho(no.dir);
    }

    @Override
    public Value get(Key chave) {
        if (chave == null) {
            return null;
        }

        return get(raiz, chave);
    }

    private Value get(Node<Key, Value> no, Key chave) {
        if (no == null) {
            return null;
        }

        int compare = chave.compareTo(no.chave);
        if (compare < 0) {
            return get(no.esq, chave);
        } else if (compare > 0) {
            return get(no.dir, chave);
        } else {
            return no.valor;
        }
    }

    @Override
    public boolean contains(Key chave) {
        if (chave == null) {
            throw new IllegalArgumentException("Argument to contains() cannot be null");
        }
        return get(chave) != null;
    }

    @Override
    public void put(Key chave, Value valor) {
        if (chave == null) {
            return;
        }

        if (valor == null) {
            delete(chave);
            return;
        }

        raiz = put(raiz, chave, valor);
    }

    private Node<Key, Value> put(Node<Key, Value> no, Key chave, Value valor) {
        if (no == null) {
            return new Node<>(chave, valor);
        }

        int compare = chave.compareTo(no.chave);

        if (compare < 0) {
            no.esq = put(no.esq, chave, valor);
        } else if (compare > 0) {
            no.dir = put(no.dir, chave, valor);
        } else {
            no.valor = valor;
        }

        return no;
    }

    @Override
    public void delete(Key chave) {
        if (chave == null) {
            return;
        }

        raiz = delete(raiz, chave);
    }

    private Node<Key, Value> delete(Node<Key, Value> no, Key chave) {
        if (no == null) {
            return null;
        }

        int compare = chave.compareTo(no.chave);

        if (compare < 0) {
            no.esq = delete(no.esq, chave);
        } else if (compare > 0) {
            no.dir = delete(no.dir, chave);
        } else {
            if (no.esq == null) {
                return no.dir;
            } else if (no.dir == null) {
                return no.esq;
            } else {
                Node<Key, Value> temp = min(no.dir);
                no.chave = temp.chave;
                no.valor = temp.valor;
                no.dir = delete(no.dir, temp.chave);
            }
        }

        return no;
    }

    private Node<Key, Value> min(Node<Key, Value> no) {
        if (no == null) {
            return null;
        }
        if (no.esq != null) {
            return min(no.esq);
        }
        return no;
    }

    @Override
    public void printTree() {
        printTree(raiz);
    }

    private void printTree(Node<Key, Value> node) {
        if (node != null) {
            printTree(node.esq);
            System.out.println(node.valor);
            printTree(node.dir);
        }
    }
}
