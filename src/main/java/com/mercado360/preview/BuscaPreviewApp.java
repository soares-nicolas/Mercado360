package com.mercado360.preview;

import com.mercado360.controller.BuscaController;
import com.mercado360.dao.memory.BancaDaoMemoria;
import com.mercado360.dao.memory.ProdutoDaoMemoria;
import com.mercado360.model.Banca;
import com.mercado360.model.Produto;
import com.mercado360.service.BancaService;
import com.mercado360.service.BuscaService;
import com.mercado360.service.ProdutoService;

import java.math.BigDecimal;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuscaPreviewApp extends Application {

    @Override
    public void start(Stage palco) throws Exception {

        // Carrega a interface gráfica.
        FXMLLoader carregador = new FXMLLoader(
            Objects.requireNonNull(
                getClass().getResource("/view/busca.fxml")
            )
        );

        Parent raiz = carregador.load();

        BuscaController controller = carregador.getController();

        // DAOs temporários em memória.
        BancaDaoMemoria bancaDao = new BancaDaoMemoria();
        ProdutoDaoMemoria produtoDao = new ProdutoDaoMemoria();

        // Serviços desenvolvidos pela Pessoa 2.
        BancaService bancaService =
            new BancaService(bancaDao, produtoDao);

        ProdutoService produtoService =
            new ProdutoService(produtoDao, bancaDao);

        BuscaService buscaService =
            new BuscaService(produtoDao);

        // ------------------------------------
        // CADASTRO DAS BANCAS DE DEMONSTRAÇÃO
        // ------------------------------------

        Banca maria = bancaService.cadastrar(
            new Banca("Dona Maria", "A-12", "Hortifruti")
        );

        Banca joao = bancaService.cadastrar(
            new Banca("Seu João", "B-03", "Mercearia")
        );

        Banca carlos = bancaService.cadastrar(
            new Banca("Seu Carlos", "C-05", "Açougue")
        );

        // ------------------------------------
        // FRUTAS
        // ------------------------------------

        produtoService.cadastrar(
            new Produto("Açaí", "Frutas",
                new BigDecimal("15.00"), maria)
        );

        produtoService.cadastrar(
            new Produto("Manga", "Frutas",
                new BigDecimal("6.50"), maria)
        );

        produtoService.cadastrar(
            new Produto("Banana", "Frutas",
                new BigDecimal("5.00"), maria)
        );

        produtoService.cadastrar(
            new Produto("Maçã", "Frutas",
                new BigDecimal("9.90"), maria)
        );

        produtoService.cadastrar(
            new Produto("Melancia", "Frutas",
                new BigDecimal("18.00"), maria)
        );

        produtoService.cadastrar(
            new Produto("Laranja", "Frutas",
                new BigDecimal("4.50"), maria)
        );

        // ------------------------------------
        // VERDURAS E LEGUMES
        // ------------------------------------

        produtoService.cadastrar(
            new Produto("Tomate", "Verduras e Legumes",
                new BigDecimal("7.50"), maria)
        );

        produtoService.cadastrar(
            new Produto("Alface", "Verduras e Legumes",
                new BigDecimal("3.00"), maria)
        );

        produtoService.cadastrar(
            new Produto("Cenoura", "Verduras e Legumes",
                new BigDecimal("5.50"), maria)
        );

        produtoService.cadastrar(
            new Produto("Batata", "Verduras e Legumes",
                new BigDecimal("6.00"), maria)
        );

        produtoService.cadastrar(
            new Produto("Cebola", "Verduras e Legumes",
                new BigDecimal("5.90"), maria)
        );

        // ------------------------------------
        // CARNES
        // ------------------------------------

        produtoService.cadastrar(
            new Produto("Carne bovina", "Carnes",
                new BigDecimal("38.90"), carlos)
        );

        produtoService.cadastrar(
            new Produto("Frango", "Carnes",
                new BigDecimal("14.90"), carlos)
        );

        produtoService.cadastrar(
            new Produto("Linguiça", "Carnes",
                new BigDecimal("19.90"), carlos)
        );

        produtoService.cadastrar(
            new Produto("Costela bovina", "Carnes",
                new BigDecimal("29.90"), carlos)
        );

        // ------------------------------------
        // MERCEARIA
        // ------------------------------------

        produtoService.cadastrar(
            new Produto("Farinha", "Mercearia",
                new BigDecimal("8.00"), joao)
        );

        produtoService.cadastrar(
            new Produto("Arroz", "Mercearia",
                new BigDecimal("25.90"), joao)
        );

        produtoService.cadastrar(
            new Produto("Feijão", "Mercearia",
                new BigDecimal("9.50"), joao)
        );

        produtoService.cadastrar(
            new Produto("Açúcar", "Mercearia",
                new BigDecimal("4.90"), joao)
        );

        produtoService.cadastrar(
            new Produto("Café", "Mercearia",
                new BigDecimal("18.90"), joao)
        );

        produtoService.cadastrar(
            new Produto("Óleo", "Mercearia",
                new BigDecimal("7.90"), joao)
        );

        // Conecta a busca com a interface.
        controller.configurarServico(buscaService);

        // Exibe o Mercado360.
        Scene cena = new Scene(raiz);

        palco.setTitle("Mercado360 - Busca de Produtos");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
