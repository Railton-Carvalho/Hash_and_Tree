package interfaces;

import java.util.List;

public interface Hash<Key extends Comparable<Key>, Value> {

    void put(Key key, Value value);

    void delete(Key key);

    Value get(Key key);

    void resize();

    List<Value> searchByName(Key key);

    List<Value> searchByKeyword(Key key);
}
