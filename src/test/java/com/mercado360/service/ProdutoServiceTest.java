package com.mercado360.service;

import static org.junit.jupiter.api.Assertions.*;

import com.mercado360.dao.memory.BancaDaoMemoria;
import com.mercado360.dao.memory.ProdutoDaoMemoria;
import com.mercado360.exception.RegistroNaoEncontradoException;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdutoServiceTest {

    private ProdutoService produtoService;
    private Banca banca;

    @BeforeEach
    void preparar() throws Exception {
        BancaDaoMemoria bancaDao = new BancaDaoMemoria();
        ProdutoDaoMemoria produtoDao = new ProdutoDaoMemoria();
        BancaService bancaService = new BancaService(bancaDao, produtoDao);
        produtoService = new ProdutoService(produtoDao, bancaDao);
        banca = bancaService.cadastrar(new Banca("Dona Maria", "A-12", "Hortifruti"));
    }

    @Test
    void cadastraProdutoValidoGerandoIdEArredondandoPreco() throws Exception {
        Produto p = produtoService.cadastrar(
                new Produto("  Açaí  ", "Frutas", new BigDecimal("12.345"), banca));

        assertNotNull(p.getId());
        assertEquals("Açaí", p.getNome());
        assertEquals(new BigDecimal("12.35"), p.getPreco());
    }

    @Test
    void rejeitaPrecoZeroOuNegativo() {
        Produto p = new Produto("Manga", "Frutas", BigDecimal.ZERO, banca);
        assertThrows(ValidacaoException.class, () -> produtoService.cadastrar(p));
    }

    @Test
    void rejeitaNomeVazio() {
        Produto p = new Produto("  ", "Frutas", BigDecimal.TEN, banca);
        assertThrows(ValidacaoException.class, () -> produtoService.cadastrar(p));
    }

    @Test
    void rejeitaBancaInexistente() {
        Banca fantasma = new Banca(999L, "X", "Z-1", "Outros");
        Produto p = new Produto("Manga", "Frutas", BigDecimal.TEN, fantasma);
        assertThrows(RegistroNaoEncontradoException.class, () -> produtoService.cadastrar(p));
    }

    @Test
    void excluirProdutoInexistenteLancaExcecao() {
        assertThrows(RegistroNaoEncontradoException.class, () -> produtoService.excluir(42L));
    }

    @Test
    void atualizaEExclui() throws Exception {
        Produto p = produtoService.cadastrar(
                new Produto("Manga", "Frutas", new BigDecimal("5"), banca));
        p.setPreco(new BigDecimal("7"));
        produtoService.atualizar(p);
        assertEquals(new BigDecimal("7.00"), produtoService.buscarPorId(p.getId()).getPreco());

        produtoService.excluir(p); // usa a sobrecarga com objeto
        assertTrue(produtoService.listarTodos().isEmpty());
    }
}