package interfaces;

public interface Tree<Key extends Comparable<Key>, Value> {

    int tamanho();

    boolean isEmpty();

    void put(Key chave, Value valor);

    Value get(Key chave);

    boolean contains(Key chave);

    void delete(Key chave);

    void printTree();

}
