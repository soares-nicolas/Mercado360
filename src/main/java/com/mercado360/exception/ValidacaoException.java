package com.mercado360.exception;

/** Lançada quando um dado informado viola uma regra de negócio. */
public class ValidacaoException extends Mercado360Exception {

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}