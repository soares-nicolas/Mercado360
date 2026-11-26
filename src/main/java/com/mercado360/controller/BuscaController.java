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

    // Opção padrão do filtro de categorias.
    private static final String TODAS = "Todas as categorias";

    // COMPONENTES DA INTERFACE

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

    // SERVIÇO DE BUSCA

    private BuscaService buscaService;

    // FORMATAÇÃO DOS PREÇOS EM REAIS

    private final NumberFormat formatoMoeda =
            NumberFormat.getCurrencyInstance(
                    Locale.forLanguageTag("pt-BR")
            );

    // INICIALIZAÇÃO DA INTERFACE

    @FXML
    private void initialize() {

        // Coluna do nome do produto.
        colunaProduto.setCellValueFactory(dados ->
                new ReadOnlyStringWrapper(
                        dados.getValue().getNome()
                )
        );

        // Coluna da categoria.
        colunaCategoria.setCellValueFactory(dados ->
                new ReadOnlyStringWrapper(
                        dados.getValue().getCategoria()
                )
        );

        // Coluna do preço.
        colunaPreco.setCellValueFactory(dados -> {

            Produto produto = dados.getValue();

            String preco = produto.getPreco() == null
                    ? "Não informado"
                    : formatoMoeda.format(produto.getPreco());

            return new ReadOnlyStringWrapper(preco);
        });

        // Coluna do nome da banca.
        colunaBanca.setCellValueFactory(dados -> {

            Banca banca = dados.getValue().getBanca();

            String nome = banca == null
                    ? "Não informada"
                    : banca.getNome();

            return new ReadOnlyStringWrapper(nome);
        });

        // Coluna da localização da banca.
        colunaLocalizacao.setCellValueFactory(dados -> {

            Banca banca = dados.getValue().getBanca();

            String localizacao = banca == null
                    ? "Não informada"
                    : banca.getSetor() + " - " + banca.getNumero();

            return new ReadOnlyStringWrapper(localizacao);
        });

        // Configura o filtro inicial.
        filtroCategoria.getItems().setAll(TODAS);

        filtroCategoria.getSelectionModel().selectFirst();
    }

    // INTEGRAÇÃO COM O SERVIÇO DA PESSOA 2

    public void configurarServico(BuscaService servico) {

        this.buscaService = Objects.requireNonNull(servico);

        try {

            // Atualiza as categorias disponíveis.
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

    // PESQUISAR PRODUTOS

    @FXML
    private void pesquisar() {

        if (buscaService == null) {

            labelStatus.setText(
                    "Serviço de busca indisponível."
            );

            return;
        }

        // Cria os filtros da pesquisa.
        FiltroBusca filtro = new FiltroBusca();

        // Obtém o nome digitado.
        filtro.setTermo(campoPesquisa.getText());

        // Obtém a categoria selecionada.
        String categoria = filtroCategoria.getValue();

        if (categoria != null && !TODAS.equals(categoria)) {

            filtro.setCategoria(categoria);
        }

        try {

            // Executa a busca utilizando o serviço.
            List<Produto> resultados =
                    buscaService.buscar(filtro);

            // Atualiza a tabela.
            tabelaResultados.getItems().setAll(resultados);

            // Exibe a quantidade encontrada.
            if (resultados.isEmpty()) {

                labelStatus.setText(
                        "Nenhum produto encontrado."
                );

            } else {

                labelStatus.setText(
                        resultados.size() +
                        " produto(s) encontrado(s)."
                );
            }

        } catch (Mercado360Exception e) {

            tabelaResultados.getItems().clear();

            labelStatus.setText(
                    "Erro na pesquisa: " + e.getMessage()
            );
        }
    }

    // LIMPAR PESQUISA E FILTROS

    @FXML
    private void limpar() {

        // Apaga o texto digitado.
        campoPesquisa.clear();

        // Restaura a categoria padrão.
        filtroCategoria.getSelectionModel().selectFirst();

        // Limpa os resultados da tabela.
        tabelaResultados.getItems().clear();

        // Atualiza a mensagem da interface.
        labelStatus.setText(
                "Pesquisa limpa. Digite um produto para começar."
        );

        // Devolve o foco ao campo de pesquisa.
        campoPesquisa.requestFocus();
    }
}
