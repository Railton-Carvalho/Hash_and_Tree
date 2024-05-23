package arquivo;

import java.util.Date;

public class Arquivo {
    private String nome;
    private String caminho;
    private String tipo;
    private long tamanho;
    private Date dataCriacao;
    private Date dataModificacao;

    public Arquivo(String nome, String caminho, String tipo, long tamanho, Date dataCriacao, Date dataModificacao) {
        this.nome = nome;
        this.caminho = caminho;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "nome='" + this.getNome() + '\'' +
                ", caminho='" + this.getCaminho() + '\'' +
                ", tipo='" + this.getTipo() + '\'' +
                ", tamanho=" + this.getTamanho() +
                ", dataCriacao=" + this.getDataCriacao() +
                ", dataModificacao=" + this.getDataModificacao() +
                '}';
    }
}
