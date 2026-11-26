package com.mercado360.service;

import static org.junit.jupiter.api.Assertions.*;

import com.mercado360.dao.memory.BancaDaoMemoria;
import com.mercado360.dao.memory.ProdutoDaoMemoria;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancaServiceTest {

    private BancaService bancaService;
    private ProdutoService produtoService;

    @BeforeEach
    void preparar() {
        BancaDaoMemoria bancaDao = new BancaDaoMemoria();
        ProdutoDaoMemoria produtoDao = new ProdutoDaoMemoria();
        bancaService = new BancaService(bancaDao, produtoDao);
        produtoService = new ProdutoService(produtoDao, bancaDao);
    }

    @Test
    void rejeitaNumeroDuplicado() throws Exception {
        bancaService.cadastrar(new Banca("Dona Maria", "A-12", "Hortifruti"));
        assertThrows(ValidacaoException.class,
                () -> bancaService.cadastrar(new Banca("Seu João", "a-12", "Carnes")));
    }

    @Test
    void naoExcluiBancaComProdutos() throws Exception {
        Banca b = bancaService.cadastrar(new Banca("Dona Maria", "A-12", "Hortifruti"));
        produtoService.cadastrar(new Produto("Manga", "Frutas", BigDecimal.TEN, b));

        assertThrows(ValidacaoException.class, () -> bancaService.excluir(b.getId()));
    }

    @Test
    void excluiBancaSemProdutos() throws Exception {
        Banca b = bancaService.cadastrar(new Banca("Dona Maria", "A-12", "Hortifruti"));
        bancaService.excluir(b.getId());
        assertTrue(bancaService.listarTodos().isEmpty());
    }
}