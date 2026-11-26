package com.mercado360.dao.memory;

import com.mercado360.dao.ProdutoDao;
import com.mercado360.model.Produto;
import com.mercado360.util.TextoUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** Implementação temporária de ProdutoDao, só em memória. Será substituída pelo JDBC. */
public class ProdutoDaoMemoria implements ProdutoDao {

    private final Map<Long, Produto> dados = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Produto inserir(Produto produto) {
        produto.setId(sequencia.incrementAndGet());
        dados.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public void atualizar(Produto produto) {
        dados.put(produto.getId(), produto);
    }

    @Override
    public void excluir(Long id) {
        dados.remove(id);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public List<Produto> buscarPorNome(String termo) {
        String alvo = TextoUtil.normalizar(termo);
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : dados.values()) {
            if (TextoUtil.normalizar(p.getNome()).contains(alvo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}