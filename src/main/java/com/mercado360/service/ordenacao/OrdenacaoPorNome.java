package com.mercado360.service.ordenacao;

import com.mercado360.model.Produto;
import java.util.Comparator;

public class OrdenacaoPorNome extends OrdenacaoBase {

    @Override
    protected Comparator<Produto> comparador() {
        return Comparator.comparing(Produto::getNome, COLATOR);
    }

    @Override
    public String getDescricao() {
        return "Nome (A-Z)";
    }
}