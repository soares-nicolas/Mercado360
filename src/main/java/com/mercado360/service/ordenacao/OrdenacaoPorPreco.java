package com.mercado360.service.ordenacao;

import com.mercado360.model.Produto;
import java.util.Comparator;

public class OrdenacaoPorPreco extends OrdenacaoBase {

    private final boolean crescente;

    /** Sobrecarga: sem argumento, o padrão é crescente. */
    public OrdenacaoPorPreco() {
        this(true);
    }

    public OrdenacaoPorPreco(boolean crescente) {
        this.crescente = crescente;
    }

    @Override
    protected Comparator<Produto> comparador() {
        Comparator<Produto> porPreco = Comparator.comparing(Produto::getPreco);
        return crescente ? porPreco : porPreco.reversed();
    }

    @Override
    public String getDescricao() {
        return crescente ? "Menor preço" : "Maior preço";
    }
}