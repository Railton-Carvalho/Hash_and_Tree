package hash;

import interfaces.Hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashEncadeado<Key extends Comparable<Key>, Value> implements Hash<Key, Value> {
    private int N;
    private int M = 16;
    private List<LinkedList<Node<Key, Value>>> table;

    public HashEncadeado() {
        table = new ArrayList<>(M);
        for (int i = 0; i < M; i++) {
            table.add(new LinkedList<>());
        }
    }

    private static class Node<Key, Value> {
        private Key key;
        private Value value;
        private Node<Key, Value> next;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(Key key) {
        return Math.abs(key.hashCode() % M);
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (N >= M / 2) {
            resize(2 * M);
        }

        int index = hash(key);
        LinkedList<Node<Key, Value>> chain = table.get(index);
        for (Node<Key, Value> node : chain) {
            if (node.key.equals(key)) {
                node.value = val;
                return;
            }
        }

        chain.add(new Node<>(key, val));
        N++;
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to delete() cannot be null");
        }

        int index = hash(key);
        LinkedList<Node<Key, Value>> chain = table.get(index);
        for (Node<Key, Value> node : chain) {
            if (node.key.equals(key)) {
                chain.remove(node);
                N--;
                break;
            }
        }

        if (N > 0 && N == M / 8) {
            resize(M / 2);
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        LinkedList<Node<Key, Value>> chain = table.get(index);
        for (Node<Key, Value> node : chain) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    @Override
    public void resize() { }

    public void resize(int cap) {
        List<LinkedList<Node<Key, Value>>> t = new ArrayList<>(cap);

        for (int i = 0; i < cap; i++) {
            t.add(new LinkedList<>());
        }

        for (LinkedList<Node<Key, Value>> chain : table) {
            for (Node<Key, Value> node : chain) {
                int index = hash(node.key);
                t.get(index).add(node);
            }
        }

        table = t;
        M = cap;
    }

    @Override
    public List<Value> searchByName(Key key) {
        List<Value> result = new ArrayList<>();

        for (LinkedList<Node<Key, Value>> chain : table) {
            for (Node<Key, Value> node : chain) {
                if (node.key != null && node.key.equals(key)) {
                    result.add(node.value);
                }
            }
        }

        return result;
    }

    @Override
    public List<Value> searchByKeyword(Key key) {
        List<Value> result = new ArrayList<>();

        for (LinkedList<Node<Key, Value>> chain : table) {
            for (Node<Key, Value> node : chain) {
                if (node.key != null && node.key.toString().contains(key.toString())) {
                    result.add(node.value);
                }
            }
        }

        return result;
    }
}
