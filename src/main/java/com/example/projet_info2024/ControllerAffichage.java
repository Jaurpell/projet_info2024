package com.example.projet_info2024;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerAffichage {

    @FXML
    private Button buttonStartInter;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleStartButton() {
        try {
            openSimulationScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openSimulationScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        // Afficher la scène de simulation dans le même stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();
    }
}
