package com.mercado360.exception;

/**
 * Lançada pelo DAO quando ocorre erro de banco.
 * O DAO captura SQLException e relança como esta, assim service e controller
 * nunca precisam importar java.sql.
 */
public class PersistenciaException extends Mercado360Exception {

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}