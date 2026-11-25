package com.mercado360.model;

public class Banca extends EntidadeBase {

    private String nome;
    private String numero;  // ex.: "A-12"
    private String setor;   // ex.: "Hortifruti"

    public Banca() { }

    public Banca(String nome, String numero, String setor) {
        this.nome = nome;
        this.numero = numero;
        this.setor = setor;
    }

    public Banca(Long id, String nome, String numero, String setor) { // sobrecarga
        super(id);
        this.nome = nome;
        this.numero = numero;
        this.setor = setor;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    @Override
    public String toString() { // sobrescrita
        return nome + " (Banca " + numero + " - " + setor + ")";
    }
}