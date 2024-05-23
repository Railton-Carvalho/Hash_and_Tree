package estrutura;

public class Generic<Key , Value> implements Comparable<Generic<Key, Value>> {
    private Key chave;
    private Value valor;

    public Generic(Key chave, Value valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public Key getKey() {
        return this.chave;
    }

    public Value getValue() {
        return this.valor;
    }

    @Override
    public int compareTo(Generic o) {
        if(this.chave instanceof Integer){
            return((Integer) this.chave).compareTo((Integer) o.getKey());
        }else if(this.chave instanceof String){
            return((String) this.chave).compareTo((String) o.getKey());
        }else{
            return((Double) this.chave).compareTo((Double) o.getKey());
        }
    }
}
