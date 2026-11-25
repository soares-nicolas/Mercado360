package com.mercado360.service;

import static org.junit.jupiter.api.Assertions.*;

import com.mercado360.dao.memory.BancaDaoMemoria;
import com.mercado360.dao.memory.ProdutoDaoMemoria;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import com.mercado360.service.ordenacao.OrdenacaoFactory;
import com.mercado360.service.ordenacao.TipoOrdenacao;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuscaServiceTest {

    private BuscaService buscaService;

    @BeforeEach
    void preparar() throws Exception {
        BancaDaoMemoria bancaDao = new BancaDaoMemoria();
        ProdutoDaoMemoria produtoDao = new ProdutoDaoMemoria();
        BancaService bancaService = new BancaService(bancaDao, produtoDao);
        ProdutoService produtoService = new ProdutoService(produtoDao, bancaDao);
        buscaService = new BuscaService(produtoDao);

        Banca maria = bancaService.cadastrar(new Banca("Dona Maria", "A-12", "Hortifruti"));
        Banca joao = bancaService.cadastrar(new Banca("Seu João", "B-03", "Mercearia"));

        produtoService.cadastrar(new Produto("Açaí", "Frutas", new BigDecimal("15.00"), maria));
        produtoService.cadastrar(new Produto("Manga", "Frutas", new BigDecimal("6.50"), maria));
        produtoService.cadastrar(new Produto("Farinha", "Mercearia", new BigDecimal("8.00"), joao));
    }

    @Test
    void buscaPorNomeIgnoraAcentoEMaiuscula() throws Exception {
        List<Produto> r = buscaService.buscar("ACAI");
        assertEquals(1, r.size());
        assertEquals("Açaí", r.get(0).getNome());
    }

    @Test
    void filtraPorCategoria() throws Exception {
        FiltroBusca f = new FiltroBusca();
        f.setCategoria("frutas");
        assertEquals(2, buscaService.buscar(f).size());
    }

    @Test
    void filtraPorFaixaDePrecoEOrdenaDoMaiorParaMenor() throws Exception {
        FiltroBusca f = new FiltroBusca();
        f.setPrecoMinimo(new BigDecimal("7"));
        f.setOrdenacao(OrdenacaoFactory.criar(TipoOrdenacao.PRECO_DECRESCENTE));

        List<Produto> r = buscaService.buscar(f);
        assertEquals(2, r.size());
        assertEquals("Açaí", r.get(0).getNome());
        assertEquals("Farinha", r.get(1).getNome());
    }

    @Test
    void ordenaPorBanca() throws Exception {
        FiltroBusca f = new FiltroBusca();
        f.setOrdenacao(OrdenacaoFactory.criar(TipoOrdenacao.BANCA));
        // "Dona Maria" vem antes de "Seu João"
        assertEquals("Seu João", buscaService.buscar(f).get(2).getBanca().getNome());
    }

    @Test
    void rejeitaFaixaDePrecoInvertida() {
        FiltroBusca f = new FiltroBusca();
        f.setPrecoMinimo(new BigDecimal("20"));
        f.setPrecoMaximo(new BigDecimal("10"));
        assertThrows(ValidacaoException.class, () -> buscaService.buscar(f));
    }

    @Test
    void listaCategoriasECountPorCategoria() throws Exception {
        assertEquals(2, buscaService.listarCategorias().size());
        assertEquals(2L, buscaService.contarPorCategoria().get("Frutas"));
    }
}