package com.mercado360.dao;

import com.mercado360.exception.PersistenciaException;
import com.mercado360.model.Produto;
import java.util.List;

public interface ProdutoDao extends CrudDao<Produto, Long> {

    /** Busca produtos cujo nome contém o termo (LIKE, sem diferenciar maiúsculas). */
    List<Produto> buscarPorNome(String termo) throws PersistenciaException;
}