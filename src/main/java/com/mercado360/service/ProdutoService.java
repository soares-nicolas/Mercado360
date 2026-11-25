package com.mercado360.service;

import com.mercado360.dao.BancaDao;
import com.mercado360.dao.ProdutoDao;
import com.mercado360.exception.Mercado360Exception;
import com.mercado360.exception.RegistroNaoEncontradoException;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Produto;
import com.mercado360.util.TextoUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Regras de negócio de Produto (CRUD com validação). */
public class ProdutoService {

    private final ProdutoDao produtoDao;
    private final BancaDao bancaDao;

    public ProdutoService(ProdutoDao produtoDao, BancaDao bancaDao) {
        this.produtoDao = produtoDao;
        this.bancaDao = bancaDao;
    }

    public Produto cadastrar(Produto produto) throws Mercado360Exception {
        validar(produto);
        return produtoDao.inserir(produto);
    }

    public void atualizar(Produto produto) throws Mercado360Exception {
        if (produto == null || produto.getId() == null) {
            throw new ValidacaoException("O produto precisa ter um id para ser atualizado.");
        }
        buscarPorId(produto.getId());
        validar(produto);
        produtoDao.atualizar(produto);
    }

    public void excluir(Long id) throws Mercado360Exception {
        buscarPorId(id);
        produtoDao.excluir(id);
    }

    /** Sobrecarga: mesmo nome, parâmetro diferente. Útil quando a tela já tem o objeto. */
    public void excluir(Produto produto) throws Mercado360Exception {
        if (produto == null) {
            throw new ValidacaoException("Produto não informado.");
        }
        excluir(produto.getId());
    }

    public Produto buscarPorId(Long id) throws Mercado360Exception {
        return produtoDao.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto", id));
    }

    public List<Produto> listarTodos() throws Mercado360Exception {
        return produtoDao.listarTodos();
    }

    private void validar(Produto produto) throws Mercado360Exception {
        if (produto == null) {
            throw new ValidacaoException("Produto não informado.");
        }
        if (TextoUtil.vazio(produto.getNome()) || produto.getNome().trim().length() < 2) {
            throw new ValidacaoException("O nome do produto deve ter ao menos 2 caracteres.");
        }
        if (TextoUtil.vazio(produto.getCategoria())) {
            throw new ValidacaoException("A categoria do produto é obrigatória.");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoException("O preço deve ser maior que zero.");
        }
        if (produto.getBanca() == null || produto.getBanca().getId() == null) {
            throw new ValidacaoException("Selecione a banca responsável pelo produto.");
        }
        // Regra: a banca informada precisa existir de verdade.
        Long idBanca = produto.getBanca().getId();
        produto.setBanca(bancaDao.buscarPorId(idBanca)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Banca", idBanca)));

        // Normaliza os dados antes de salvar.
        produto.setNome(produto.getNome().trim());
        produto.setCategoria(produto.getCategoria().trim());
        produto.setPreco(produto.getPreco().setScale(2, RoundingMode.HALF_UP));
    }
}