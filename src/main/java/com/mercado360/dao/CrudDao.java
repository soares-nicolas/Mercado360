package com.mercado360.dao;

import com.mercado360.exception.PersistenciaException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico de CRUD.
 * @param <T>  tipo da entidade
 * @param <ID> tipo do identificador
 */
public interface CrudDao<T, ID> {

    /** Insere e devolve a entidade já com o id gerado pelo banco. */
    T inserir(T entidade) throws PersistenciaException;

    void atualizar(T entidade) throws PersistenciaException;

    void excluir(ID id) throws PersistenciaException;

    Optional<T> buscarPorId(ID id) throws PersistenciaException;

    List<T> listarTodos() throws PersistenciaException;
}