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
        primaryStage.setTitle("Exemple de chronologie");


        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        AnchorPane root = loader.load();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        primaryStage.setTitle("Simu");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

