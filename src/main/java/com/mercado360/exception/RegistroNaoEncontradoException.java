package com.mercado360.exception;

/** Lançada quando um registro buscado por ID não existe. */
public class RegistroNaoEncontradoException extends Mercado360Exception {

    public RegistroNaoEncontradoException(String entidade, Long id) {
        super(entidade + " não encontrado(a) com id " + id);
    }
}