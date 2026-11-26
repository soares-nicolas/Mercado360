package com.mercado360.dao.memory;

import com.mercado360.dao.BancaDao;
import com.mercado360.model.Banca;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** Implementação temporária de BancaDao, só em memória. Será substituída pelo JDBC. */
public class BancaDaoMemoria implements BancaDao {

    private final Map<Long, Banca> dados = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Banca inserir(Banca banca) {
        banca.setId(sequencia.incrementAndGet()); // simula o id gerado pelo banco
        dados.put(banca.getId(), banca);
        return banca;
    }

    @Override
    public void atualizar(Banca banca) {
        dados.put(banca.getId(), banca);
    }

    @Override
    public void excluir(Long id) {
        dados.remove(id);
    }

    @Override
    public Optional<Banca> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Banca> listarTodos() {
        return new ArrayList<>(dados.values()); // cópia: quem chama não mexe no Map interno
    }
}