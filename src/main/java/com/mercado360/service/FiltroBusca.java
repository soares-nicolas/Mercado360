package com.mercado360.service;

import com.mercado360.service.ordenacao.EstrategiaOrdenacao;
import com.mercado360.service.ordenacao.OrdenacaoFactory;
import com.mercado360.service.ordenacao.TipoOrdenacao;
import java.math.BigDecimal;

/** Parâmetros de uma busca. Campos nulos/vazios significam "sem filtro". */
public class FiltroBusca {

    private String termo;
    private String categoria;
    private BigDecimal precoMinimo;
    private BigDecimal precoMaximo;
    private EstrategiaOrdenacao ordenacao = OrdenacaoFactory.criar(TipoOrdenacao.NOME);

    public String getTermo() { return termo; }
    public void setTermo(String termo) { this.termo = termo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPrecoMinimo() { return precoMinimo; }
    public void setPrecoMinimo(BigDecimal precoMinimo) { this.precoMinimo = precoMinimo; }

    public BigDecimal getPrecoMaximo() { return precoMaximo; }
    public void setPrecoMaximo(BigDecimal precoMaximo) { this.precoMaximo = precoMaximo; }

    public EstrategiaOrdenacao getOrdenacao() { return ordenacao; }
    public void setOrdenacao(EstrategiaOrdenacao ordenacao) { this.ordenacao = ordenacao; }
}