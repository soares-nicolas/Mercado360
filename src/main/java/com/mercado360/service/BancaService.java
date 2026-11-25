package com.mercado360.service;

import com.mercado360.dao.BancaDao;
import com.mercado360.dao.ProdutoDao;
import com.mercado360.exception.Mercado360Exception;
import com.mercado360.exception.RegistroNaoEncontradoException;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import com.mercado360.util.TextoUtil;
import java.util.List;

/** Regras de negócio de Banca. Recebe os DAOs pelo construtor (injeção de dependência). */
public class BancaService {

    private final BancaDao bancaDao;
    private final ProdutoDao produtoDao;

    public BancaService(BancaDao bancaDao, ProdutoDao produtoDao) {
        this.bancaDao = bancaDao;
        this.produtoDao = produtoDao;
    }

    public Banca cadastrar(Banca banca) throws Mercado360Exception {
        validar(banca);
        return bancaDao.inserir(banca);
    }

    public void atualizar(Banca banca) throws Mercado360Exception {
        if (banca == null || banca.getId() == null) {
            throw new ValidacaoException("A banca precisa ter um id para ser atualizada.");
        }
        buscarPorId(banca.getId()); // lança exceção se não existir
        validar(banca);
        bancaDao.atualizar(banca);
    }

    /** Regra: não é possível excluir uma banca que ainda tem produtos. */
    public void excluir(Long id) throws Mercado360Exception {
        buscarPorId(id);
        for (Produto p : produtoDao.listarTodos()) {
            if (p.getBanca() != null && id.equals(p.getBanca().getId())) {
                throw new ValidacaoException(
                        "Não é possível excluir: a banca possui produtos cadastrados.");
            }
        }
        bancaDao.excluir(id);
    }

    public Banca buscarPorId(Long id) throws Mercado360Exception {
        return bancaDao.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Banca", id));
    }

    public List<Banca> listarTodos() throws Mercado360Exception {
        return bancaDao.listarTodos();
    }

    private void validar(Banca banca) throws Mercado360Exception {
        if (banca == null) {
            throw new ValidacaoException("Banca não informada.");
        }
        if (TextoUtil.vazio(banca.getNome())) {
            throw new ValidacaoException("O nome da banca é obrigatório.");
        }
        if (TextoUtil.vazio(banca.getNumero())) {
            throw new ValidacaoException("O número da banca é obrigatório.");
        }
        if (TextoUtil.vazio(banca.getSetor())) {
            throw new ValidacaoException("O setor da banca é obrigatório.");
        }
        banca.setNome(banca.getNome().trim());
        banca.setNumero(banca.getNumero().trim());
        banca.setSetor(banca.getSetor().trim());

        // Regra: o número identifica o local, então não pode repetir em outra banca.
        for (Banca outra : bancaDao.listarTodos()) {
            boolean mesmoNumero = outra.getNumero().equalsIgnoreCase(banca.getNumero());
            boolean outraBanca = !outra.getId().equals(banca.getId());
            if (mesmoNumero && outraBanca) {
                throw new ValidacaoException(
                        "Já existe uma banca com o número " + banca.getNumero() + ".");
            }
        }
    }
}