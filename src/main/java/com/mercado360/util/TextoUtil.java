package com.mercado360.util;

import java.text.Normalizer;

/** Funções utilitárias de texto. Classe final com construtor privado: só métodos estáticos. */
public final class TextoUtil {

    private TextoUtil() { }

    public static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }

    /** "  Açaí " -> "acai". Usado para comparar textos sem acento e sem maiúsculas. */
    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String decomposto = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD);
        return decomposto.replaceAll("\\p{M}", "").toLowerCase();
    }
}