package com.mercado360.preview;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuscaPreviewApp extends Application {

    @Override
    public void start(Stage palco) throws Exception {

        Parent raiz = FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/view/busca.fxml")
            )
        );

        Scene cena = new Scene(raiz);

        palco.setTitle("Mercado360 - Busca de Produtos");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
