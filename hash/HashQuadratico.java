package hash;

import interfaces.Hash;

import java.util.ArrayList;
import java.util.List;

public class HashQuadratico<Key extends Comparable<Key>, Value> implements Hash<Key, Value> {
    private int size;
    private int capacidade;
    private Key[] keys;
    private Value[] values;
    private double fatordeCarga = 0.7;
    private int colisoes;

    public HashQuadratico(int size) {
        this.size = 0;
        this.capacidade = size;
        this.keys = (Key[]) new Comparable[capacidade];
        this.values = (Value[]) new Object[capacidade];
        this.colisoes = 0;
    }

    private int hash(Key key, int attempt) {
        return (Math.abs(key.hashCode()) + attempt) % capacidade;
    }

    @Override
    public void put(Key key, Value value) {
        if ((double) size / capacidade >= fatordeCarga) {
            resize();
        }

        int attempt = 0;
        int index = hash(key, attempt);

        while (keys[index] != null) {
            colisoes++;
            attempt++;
            index = hash(key, attempt);
        }

        keys[index] = key;
        values[index] = value;
        size++;
    }

    @Override
    public void delete(Key key) {
        int attempt = 0;
        int index = hash(key, attempt);

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                keys[index] = null;
                values[index] = null;
                size--;
                return;
            }
            attempt++;
            index = hash(key, attempt);
        }
    }

    @Override
    public Value get(Key key) {
        int attempt = 0;
        int index = hash(key, attempt);

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index];
            }
            attempt++;
            index = hash(key, attempt);
        }

        return null;
    }

    @Override
    public void resize() {
        capacidade *= 2;
        Key[] oldKeys = keys;
        Value[] oldValues = values;

        keys = (Key[]) new Comparable[capacidade];
        values = (Value[]) new Object[capacidade];
        size = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    @Override
    public List<Value> searchByName(Key key) {
        List<Value> result = new ArrayList<>();

        int attempt = 0;
        int hash = hash(key, attempt);

        while (keys[hash] != null && !keys[hash].equals(key)) {
            attempt++;
            hash = hash(key, attempt);
        }

        if (keys[hash] != null && keys[hash].equals(key)) {
            result.add(values[hash]);
        }

        return result;
    }

    @Override
    public List<Value> searchByKeyword(Key key) {
        List<Value> result = new ArrayList<>();
        for (int i = 0; i < capacidade; i++) {
            if (keys[i] != null && keys[i].toString().contains(key.toString())) {
                result.add(values[i]);
            }
        }

        return result;
    }

}
