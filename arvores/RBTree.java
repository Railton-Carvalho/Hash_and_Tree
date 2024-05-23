package arvores;

import arquivo.Arquivo;
import interfaces.Tree;

public class RBTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<Key extends Comparable<Key>, Value> {
        private Key chave;
        private Value valor;
        private Node<Key, Value> esq;
        private Node<Key, Value> dir;
        private boolean cor;
        private int tamanho;

        Node(Key chave, Value valor) {
            this.chave = chave;
            this.valor = valor;
            this.tamanho = 1;
            this.cor = RED;
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

    @Override
    public boolean isEmpty() {
        return tamanho(raiz) == 0;
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
        raiz.cor = BLACK;
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

        if (isRed(no.dir) && !isRed(no.esq)) {
            no = rotacaoEsquerda(no);
        }
        if (isRed(no.esq) && isRed(no.esq.esq)) {
            no = rotacaoDireita(no);
        }
        if (isRed(no.esq) && isRed(no.dir)) {
            inverterCores(no);
        }

        atualizarTamanho(no);
        return no;
    }

    private void atualizarTamanho(Node<Key, Value> no) {
        no.tamanho = tamanho(no.esq) + 1 + tamanho(no.dir);
    }

    private Node<Key, Value> rotacaoEsquerda(Node<Key, Value> no) {
        Node<Key, Value> newRoot = no.dir;
        no.dir = newRoot.esq;
        newRoot.esq = no;
        newRoot.cor = no.cor;
        no.cor = RED;
        atualizarTamanho(no);
        atualizarTamanho(newRoot);
        return newRoot;
    }

    private Node<Key, Value> rotacaoDireita(Node<Key, Value> no) {
        Node<Key, Value> newRoot = no.esq;
        no.esq = newRoot.dir;
        newRoot.dir = no;
        newRoot.cor = no.cor;
        no.cor = RED;
        atualizarTamanho(no);
        atualizarTamanho(newRoot);
        return newRoot;
    }

    private void inverterCores(Node<Key, Value> no) {
        no.cor = !no.cor;
        no.esq.cor = !no.esq.cor;
        no.dir.cor = !no.dir.cor;
    }

    private boolean isRed(Node<Key, Value> no) {
        if (no == null) {
            return false;
        }
        return no.cor == RED;
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

        if (!contains(chave)) {
            return;
        }

        if (!isRed(raiz.esq) && !isRed(raiz.dir)) {
            raiz.cor = RED;
        }

        raiz = delete(raiz, chave);

        if (!isEmpty()) {
            raiz.cor = BLACK;
        }
    }

    private Node<Key, Value> delete(Node<Key, Value> no, Key chave) {
        if (chave.compareTo(no.chave) < 0) {
            if (!isRed(no.esq) && !isRed(no.esq.esq)) {
                no = moverRedEsquerda(no);
            }
            no.esq = delete(no.esq, chave);
        } else {
            if (isRed(no.esq)) {
                no = rotacaoDireita(no);
            }
            if (chave.compareTo(no.chave) == 0 && (no.dir == null)) {
                return null;
            }
            if (!isRed(no.dir) && !isRed(no.dir.esq)) {
                no = moverRedDireita(no);
            }
            if (chave.compareTo(no.chave) == 0) {
                Node<Key, Value> temp = min(no.dir);
                no.chave = temp.chave;
                no.valor = temp.valor;
                no.dir = deleteMin(no.dir);
            } else {
                no.dir = delete(no.dir, chave);
            }
        }
        return balancear(no);
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> no) {
        if (no.esq == null) {
            return null;
        }

        if (!isRed(no.esq) && !isRed(no.esq.esq)) {
            no = moverRedEsquerda(no);
        }

        no.esq = deleteMin(no.esq);
        return balancear(no);
    }

    private Node<Key, Value> moverRedEsquerda(Node<Key, Value> no) {
        inverterCores(no);
        if (isRed(no.dir.esq)) {
            no.dir = rotacaoDireita(no.dir);
            no = rotacaoEsquerda(no);
            inverterCores(no);
        }
        return no;
    }

    private Node<Key, Value> moverRedDireita(Node<Key, Value> no) {
        inverterCores(no);
        if (isRed(no.esq.esq)) {
            no = rotacaoDireita(no);
            inverterCores(no);
        }
        return no;
    }

    private Node<Key, Value> balancear(Node<Key, Value> no) {
        if (isRed(no.dir)) {
            no = rotacaoEsquerda(no);
        }
        if (isRed(no.esq) && isRed(no.esq.esq)) {
            no = rotacaoDireita(no);
        }
        if (isRed(no.esq) && isRed(no.dir)) {
            inverterCores(no);
        }

        atualizarTamanho(no);
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
