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

        // Carrega a interface FXML.
        FXMLLoader carregador = new FXMLLoader(
            Objects.requireNonNull(
                getClass().getResource("/view/busca.fxml")
            )
        );

        Parent raiz = carregador.load();

        // Obtém o Controller associado ao FXML.
        BuscaController controller = carregador.getController();

        // Cria os DAOs temporários em memória.
        BancaDaoMemoria bancaDao = new BancaDaoMemoria();
        ProdutoDaoMemoria produtoDao = new ProdutoDaoMemoria();

        // Utiliza os serviços da Pessoa 2.
        BancaService bancaService =
            new BancaService(bancaDao, produtoDao);

        ProdutoService produtoService =
            new ProdutoService(produtoDao, bancaDao);

        BuscaService buscaService =
            new BuscaService(produtoDao);

        // Cadastra bancas fictícias para testar a tela.
        Banca maria = bancaService.cadastrar(
            new Banca("Dona Maria", "A-12", "Hortifruti")
        );

        Banca joao = bancaService.cadastrar(
            new Banca("Seu João", "B-03", "Mercearia")
        );

        // Cadastra produtos fictícios.
        produtoService.cadastrar(
            new Produto(
                "Açaí",
                "Frutas",
                new BigDecimal("15.00"),
                maria
            )
        );

        produtoService.cadastrar(
            new Produto(
                "Manga",
                "Frutas",
                new BigDecimal("6.50"),
                maria
            )
        );

        produtoService.cadastrar(
            new Produto(
                "Farinha",
                "Mercearia",
                new BigDecimal("8.00"),
                joao
            )
        );

        // Conecta o serviço ao Controller.
        controller.configurarServico(buscaService);

        // Exibe a janela.
        Scene cena = new Scene(raiz);

        palco.setTitle("Mercado360 - Busca de Produtos");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
