package com.mercado360.controller;

import com.mercado360.exception.Mercado360Exception;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import com.mercado360.service.BuscaService;
import com.mercado360.service.FiltroBusca;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BuscaController {

    private static final String TODAS = "Todas as categorias";

    @FXML
    private TextField campoPesquisa;

    @FXML
    private ComboBox<String> filtroCategoria;

    @FXML
    private TableView<Produto> tabelaResultados;

    @FXML
    private TableColumn<Produto, String> colunaProduto;

    @FXML
    private TableColumn<Produto, String> colunaCategoria;

    @FXML
    private TableColumn<Produto, String> colunaPreco;

    @FXML
    private TableColumn<Produto, String> colunaBanca;

    @FXML
    private TableColumn<Produto, String> colunaLocalizacao;

    @FXML
    private Label labelStatus;

    private BuscaService buscaService;

    private final NumberFormat formatoMoeda =
            NumberFormat.getCurrencyInstance(
                    Locale.forLanguageTag("pt-BR")
            );

    @FXML
    private void initialize() {

        colunaProduto.setCellValueFactory(dados ->
                new ReadOnlyStringWrapper(
                        dados.getValue().getNome()
                )
        );

        colunaCategoria.setCellValueFactory(dados ->
                new ReadOnlyStringWrapper(
                        dados.getValue().getCategoria()
                )
        );

        colunaPreco.setCellValueFactory(dados -> {
            Produto produto = dados.getValue();

            String preco = produto.getPreco() == null
                    ? "Não informado"
                    : formatoMoeda.format(produto.getPreco());

            return new ReadOnlyStringWrapper(preco);
        });

        colunaBanca.setCellValueFactory(dados -> {
            Banca banca = dados.getValue().getBanca();

            String nome = banca == null
                    ? "Não informada"
                    : banca.getNome();

            return new ReadOnlyStringWrapper(nome);
        });

        colunaLocalizacao.setCellValueFactory(dados -> {
            Banca banca = dados.getValue().getBanca();

            String localizacao = banca == null
                    ? "Não informada"
                    : banca.getSetor() + " - " + banca.getNumero();

            return new ReadOnlyStringWrapper(localizacao);
        });

        filtroCategoria.getItems().setAll(TODAS);
        filtroCategoria.getSelectionModel().selectFirst();
    }

    // Recebe o serviço de busca da aplicação.
    public void configurarServico(BuscaService servico) {
        this.buscaService = Objects.requireNonNull(servico);

        try {
            filtroCategoria.getItems().setAll(TODAS);
            filtroCategoria.getItems().addAll(
                    buscaService.listarCategorias()
            );
            filtroCategoria.getSelectionModel().selectFirst();

        } catch (Mercado360Exception e) {
            labelStatus.setText(
                    "Erro ao carregar categorias: " + e.getMessage()
            );
        }
    }

    @FXML
    private void pesquisar() {

        if (buscaService == null) {
            labelStatus.setText("Serviço de busca indisponível.");
            return;
        }

        FiltroBusca filtro = new FiltroBusca();

        filtro.setTermo(campoPesquisa.getText());

        String categoria = filtroCategoria.getValue();

        if (categoria != null && !TODAS.equals(categoria)) {
            filtro.setCategoria(categoria);
        }

        try {
            List<Produto> resultados = buscaService.buscar(filtro);

            tabelaResultados.getItems().setAll(resultados);

            if (resultados.isEmpty()) {
                labelStatus.setText("Nenhum produto encontrado.");
            } else {
                labelStatus.setText(
                        resultados.size() + " produto(s) encontrado(s)."
                );
            }

        } catch (Mercado360Exception e) {
            tabelaResultados.getItems().clear();
            labelStatus.setText(
                    "Erro na pesquisa: " + e.getMessage()
            );
        }
    }
}
