package com.mercado360.service.ordenacao;

import com.mercado360.model.Produto;
import java.util.List;

/** Contrato do padrão Strategy: cada implementação define uma forma de ordenar. */
public interface EstrategiaOrdenacao {

    /** Devolve uma NOVA lista ordenada, sem alterar a original. */
    List<Produto> ordenar(List<Produto> produtos);

    /** Texto amigável para mostrar na interface (ex.: ComboBox). */
    String getDescricao();
}