package com.mercado360.service;

import com.mercado360.dao.ProdutoDao;
import com.mercado360.exception.Mercado360Exception;
import com.mercado360.exception.ValidacaoException;
import com.mercado360.model.Produto;
import com.mercado360.util.TextoUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Funcionalidade principal do MVP: busca de produtos com filtros e ordenação. */
public class BuscaService {

    private final ProdutoDao produtoDao;

    public BuscaService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    /** Sobrecarga: busca simples só por nome (campo de pesquisa da tela inicial). */
    public List<Produto> buscar(String termo) throws Mercado360Exception {
        FiltroBusca filtro = new FiltroBusca();
        filtro.setTermo(termo);
        return buscar(filtro);
    }

    /** Busca completa: nome + categoria + faixa de preço + ordenação. */
    public List<Produto> buscar(FiltroBusca filtro) throws Mercado360Exception {
        validar(filtro);

        // 1) O termo é filtrado pelo DAO (no banco será um LIKE/ILIKE).
        List<Produto> base = TextoUtil.vazio(filtro.getTermo())
                ? produtoDao.listarTodos()
                : produtoDao.buscarPorNome(filtro.getTermo());

        // 2) Demais filtros em memória, com Stream.
        String categoria = TextoUtil.normalizar(filtro.getCategoria());
        List<Produto> filtrados = base.stream()
                .filter(p -> categoria.isEmpty()
                        || TextoUtil.normalizar(p.getCategoria()).equals(categoria))
                .filter(p -> filtro.getPrecoMinimo() == null
                        || p.getPreco().compareTo(filtro.getPrecoMinimo()) >= 0)
                .filter(p -> filtro.getPrecoMaximo() == null
                        || p.getPreco().compareTo(filtro.getPrecoMaximo()) <= 0)
                .collect(Collectors.toList());

        // 3) Polimorfismo: não importa qual estratégia, basta chamar ordenar().
        return filtro.getOrdenacao().ordenar(filtrados);
    }

    /** Categorias existentes, sem repetição e em ordem alfabética (para o filtro da tela). */
    public Set<String> listarCategorias() throws Mercado360Exception {
        Set<String> categorias = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Produto p : produtoDao.listarTodos()) {
            categorias.add(p.getCategoria());
        }
        return categorias;
    }

    /** Quantos produtos há em cada categoria. */
    public Map<String, Long> contarPorCategoria() throws Mercado360Exception {
        return produtoDao.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        Produto::getCategoria, TreeMap::new, Collectors.counting()));
    }

    private void validar(FiltroBusca filtro) throws ValidacaoException {
        if (filtro == null) {
            throw new ValidacaoException("Filtro de busca não informado.");
        }
        BigDecimal min = filtro.getPrecoMinimo();
        BigDecimal max = filtro.getPrecoMaximo();
        if ((min != null && min.signum() < 0) || (max != null && max.signum() < 0)) {
            throw new ValidacaoException("O preço do filtro não pode ser negativo.");
        }
        if (min != null && max != null && min.compareTo(max) > 0) {
            throw new ValidacaoException("O preço mínimo não pode ser maior que o máximo.");
        }
        if (filtro.getOrdenacao() == null) {
            throw new ValidacaoException("Escolha uma forma de ordenação.");
        }
    }
}