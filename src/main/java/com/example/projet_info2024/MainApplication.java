package com.example.projet_info2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Interface de simulation");

        // Charger le fichier FXML pour la première interface
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("FxmlInterfaceV1.fxml"));
        AnchorPane root1 = loader1.load();
        Scene scene1 = new Scene(root1);

        // Afficher la première interface
        primaryStage.setScene(scene1);
        primaryStage.show();

        // Ajouter un gestionnaire d'événements pour détecter la fermeture de la fenêtre
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            try {
                openSimulationScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Méthode pour ouvrir la scène de simulation
    private void openSimulationScene() throws IOException {
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Simulation");

        // Charger le fichier FXML pour la scène de simulation
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        AnchorPane root2 = loader2.load();
        Scene scene2 = new Scene(root2);

        // Afficher la scène de simulation
        simulationStage.setScene(scene2);
        simulationStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
