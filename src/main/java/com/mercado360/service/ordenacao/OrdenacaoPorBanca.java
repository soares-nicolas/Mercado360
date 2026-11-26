package com.mercado360.service.ordenacao;

import com.mercado360.model.Produto;
import java.util.Comparator;

public class OrdenacaoPorBanca extends OrdenacaoBase {

    @Override
    protected Comparator<Produto> comparador() {
        return Comparator
                .comparing((Produto p) -> p.getBanca().getNome(), COLATOR)
                .thenComparing(Produto::getNome, COLATOR); // desempate por nome do produto
    }

    @Override
    public String getDescricao() {
        return "Banca (A-Z)";
    }
}