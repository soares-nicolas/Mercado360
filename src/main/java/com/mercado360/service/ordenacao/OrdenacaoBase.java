package com.mercado360.service.ordenacao;

import com.mercado360.model.Produto;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Classe abstrata (Template Method): o passo de copiar e ordenar é igual para todas;
 * cada subclasse só informa QUAL comparador usar.
 */
public abstract class OrdenacaoBase implements EstrategiaOrdenacao {

    /** Collator compara texto em português (acentos corretos) e ignora maiúsculas. */
    protected static final Collator COLATOR = criarColator();

    private static Collator criarColator() {
        Collator c = Collator.getInstance(Locale.of("pt", "BR"));
        c.setStrength(Collator.SECONDARY);
        return c;
    }

    /** Cada subclasse define seu critério. */
    protected abstract Comparator<Produto> comparador();

    @Override
    public final List<Produto> ordenar(List<Produto> produtos) {
        List<Produto> copia = new ArrayList<>(produtos);
        copia.sort(comparador());
        return copia;
    }
}