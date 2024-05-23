package arvores;

import arquivo.Arquivo;
import interfaces.Tree;

public class AVLTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private static class Node<Key extends Comparable<Key>, Value> {
        private Key chave;
        private Value valor;
        private Node<Key, Value> esq;
        private Node<Key, Value> dir;
        private int altura;
        private int tamanho;

        Node(Key chave, Value valor) {
            this.chave = chave;
            this.valor = valor;
            this.tamanho = 1;
            this.altura = 0;
        }
    }

    private Node<Key, Value> raiz;

    @Override
    public int tamanho() {
        return tamanho(raiz);
    }

    private int tamanho(Node<Key, Value> no) {
        return (no == null) ? 0 : no.tamanho;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Node<Key, Value> no) {
        return (no == null) ? -1 : no.altura;
    }

    @Override
    public boolean isEmpty() {
        return tamanho(raiz) == 0;
    }

    private Node<Key, Value> rotacaoEsquerda(Node<Key, Value> no) {
        Node<Key, Value> newRoot = no.dir;
        no.dir = newRoot.esq;
        newRoot.esq = no;
        atualizarAlturaTamanho(no);
        atualizarAlturaTamanho(newRoot);
        return newRoot;
    }

    private Node<Key, Value> rotacaoDireita(Node<Key, Value> no) {
        Node<Key, Value> newRoot = no.esq;
        no.esq = newRoot.dir;
        newRoot.dir = no;
        atualizarAlturaTamanho(no);
        atualizarAlturaTamanho(newRoot);
        return newRoot;
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

        atualizarAlturaTamanho(no);
        return balancear(no);
    }

    private void atualizarAlturaTamanho(Node<Key, Value> no) {
        no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
        no.tamanho = tamanho(no.esq) + 1 + tamanho(no.dir);
    }

    private Node<Key, Value> balancear(Node<Key, Value> no) {
        int fatorBalanceamento = fatorBalanceamento(no);

        if (fatorBalanceamento > 1) {
            if (fatorBalanceamento(no.dir) < 0) {
                no.dir = rotacaoDireita(no.dir);
            }
            return rotacaoEsquerda(no);
        }

        if (fatorBalanceamento < -1) {
            if (fatorBalanceamento(no.esq) > 0) {
                no.esq = rotacaoEsquerda(no.esq);
            }
            return rotacaoDireita(no);
        }

        return no;
    }

    private int fatorBalanceamento(Node<Key, Value> no) {
        return (no == null) ? 0 : altura(no.dir) - altura(no.esq);
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
            if (no.esq != null && no.dir != null) {
                Node<Key, Value> temp = min(no.dir);
                no.chave = temp.chave;
                no.valor = temp.valor;
                no.dir = delete(no.dir, temp.chave);
            } else if (no.esq != null) {
                return no.esq;
            } else {
                return no.dir;
            }
        }

        atualizarAlturaTamanho(no);
        return balancear(no);
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

    private boolean isAVL() {
        return isAVL(raiz);
    }

    private boolean isAVL(Node<Key, Value> no) {
        if (no == null) {
            return true;
        }

        int fatorBalanceamento = fatorBalanceamento(no);
        if (fatorBalanceamento < -1 || fatorBalanceamento > 1) {
            return false;
        }

        return isAVL(no.esq) && isAVL(no.dir);
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
