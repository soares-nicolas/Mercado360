package com.mercado360.model;

import java.math.BigDecimal;

public class Produto extends EntidadeBase {

    private String nome;
    private String categoria;
    private BigDecimal preco;
    private Banca banca; // preenchida pelo DAO via INNER JOIN

    public Produto() { }

    public Produto(String nome, String categoria, BigDecimal preco, Banca banca) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.banca = banca;
    }

    public Produto(Long id, String nome, String categoria, BigDecimal preco, Banca banca) {
        super(id);
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.banca = banca;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Banca getBanca() { return banca; }
    public void setBanca(Banca banca) { this.banca = banca; }

    @Override
    public String toString() {
        return nome + " - R$ " + preco + " [" + categoria + "] @ " + banca;
    }
}