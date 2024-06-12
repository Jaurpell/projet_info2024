package com.example.projet_info2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Interface de simulation");

        // Charger le fichier FXML pour la première interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlInterfaceV1.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        // Obtenir le contrôleur et lui passer le primaryStage
        ControllerAffichage controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        // Afficher la première interface
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
