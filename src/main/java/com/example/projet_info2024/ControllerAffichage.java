package com.example.projet_info2024;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerAffichage {
    private double[] variable;

    @FXML
    private Button buttonStartInter;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    // ici int studentChoice corespond au choix de l etudiant
    private int studentChoice = 1;
    // Méthode pour définir le choix de l'étudiant sur A
    @FXML
    private void selectStudentA() {
        studentChoice = 1;
        System.out.println("Student A selected");
    }

    // Méthode pour définir le choix de l'étudiant sur B
    @FXML
    private void selectStudentB() {
        studentChoice = 2;
        System.out.println("Student B selected");
    }

    // Méthode pour définir le choix de l'étudiant sur C
    @FXML
    private void selectStudentC() {
        studentChoice = 3;
        System.out.println("Student C selected");
    }

    @FXML
    private void handleStartButton() throws IOException {
        System.out.println("Start button clicked");

        // Charger le fichier FXML de la nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Créer une nouvelle fenêtre et définir la scène
        Stage newStage = new Stage();
        newStage.setScene(scene);

        // Afficher la nouvelle fenêtre
        newStage.show();
    }

    @FXML
    private void openSimulationScene() throws IOException {
        System.out.println("Opening simulation scene");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            List<Double> variables = readDataFromFile(selectedFile);
            System.out.println(variables);
        }
    }

    public List<Double> readDataFromFile(File file) throws IOException {
        List<Double> variables = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            for (String part : parts) {
                try {
                    double value = Double.parseDouble(part);
                    variables.add(value);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric values
                }
            }
        }
        br.close();
        return variables;
    }

}
