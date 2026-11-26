package com.mercado360.service.ordenacao;

/**
 * Padrão Factory: centraliza a criação das estratégias.
 * O controller só passa o TipoOrdenacao escolhido na tela e não precisa conhecer as classes.
 */
public final class OrdenacaoFactory {

    private OrdenacaoFactory() { }

    public static EstrategiaOrdenacao criar(TipoOrdenacao tipo) {
        return switch (tipo) {
            case NOME -> new OrdenacaoPorNome();
            case PRECO_CRESCENTE -> new OrdenacaoPorPreco(true);
            case PRECO_DECRESCENTE -> new OrdenacaoPorPreco(false);
            case BANCA -> new OrdenacaoPorBanca();
        };
    }
}