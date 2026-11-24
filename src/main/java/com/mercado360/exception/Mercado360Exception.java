package com.mercado360.exception;

/**
 * Exceção base de todo o sistema. Checked: obriga quem chama a tratar o erro.
 */
public class Mercado360Exception extends Exception {

    public Mercado360Exception(String mensagem) {
        super(mensagem);
    }

    public Mercado360Exception(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}