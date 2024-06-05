package com.example.projet_info2024;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class ControllerAffichage {

    @FXML
    private Button buttonStartInter;

    @FXML
    private Button buttonFileTxt;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println("Primary stage set: " + primaryStage);
    }

    @FXML
    private void handleStartButton() {
        System.out.println("Start button clicked");
        try {
            openSimulationScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFileTxtButton() {
        // Crée un FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier de données");

        // Définit le filtre de fichiers pour n'afficher que les fichiers .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers TXT (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Ouvre la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        // Vérifie si un fichier a été sélectionné
        if (selectedFile != null) {
            // Traitez le fichier sélectionné ici
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
            // Vous pouvez lire les données du fichier et les traiter en conséquence
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    private void openSimulationScene() throws IOException {
        System.out.println("Opening simulation scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        // Afficher la scène de simulation dans le même stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();
    }
}
