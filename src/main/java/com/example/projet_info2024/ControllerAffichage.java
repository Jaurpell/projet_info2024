package com.example.projet_info2024;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerAffichage {
    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonStop;

    @FXML
    private Button buttonRes;

    @FXML
    private Canvas canvasSimu;

    @FXML
    private ProgressBar progresseBarTime;

    private List<Double> variable;

    @FXML
    public void handleStartButtonAction() {
        // Implémentation pour démarrer la simulation
        System.out.println("Simulation started with variables: " + variable);
    }

    @FXML
    public void handleStopButtonAction() {
        // Implémentation pour arrêter la simulation
        System.out.println("Simulation stopped.");
    }

    @FXML
    public void handleRestartButtonAction() {
        // Implémentation pour redémarrer la simulation
        System.out.println("Simulation restarted.");
    }


    private ControllerSimulation cs;

    @FXML
    private Button buttonStartInter;

    private Stage primaryStage;
    private int studentChoice = 1;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void selectStudentA() {
        this.studentChoice = 1;
        System.out.println("Student A selected");

    }

    @FXML
    private void selectStudentB() {
        this.studentChoice = 2;
        System.out.println("Student B selected");

    }

    @FXML
    private void selectStudentC() {
        this.studentChoice = 3;
        System.out.println("Student C selected");

    }

    @FXML
    private void handleStartButton() throws IOException {
        System.out.println("Start button clicked");
        if (cs == null) {
            initializeSimulationController();
        }

        // Assurez-vous que l'objet cs est initialisé et que la variable est définie
        if (cs == null || variable == null) {
            System.out.println("Simulation controller or variable list not initialized!");
            return;
        }

        // Chargez le fichier FXML pour la nouvelle fenêtre
        cs.setVariable(variable);

    }

    @FXML
    private void fileButtonOn() throws IOException {
        System.out.println("Opening file");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            List<Double> variables = readDataFromFile(selectedFile);
            System.out.println(variables);
        }
    }

    @FXML
    private void initializeSimulationController() throws IOException {
        // Chargez le fichier FXML pour la fenêtre de simulation
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        AnchorPane root = loader.load();
        cs = loader.getController();
        Scene scene2 = new Scene(root);

        // Assurez-vous que le primaryStage est défini avant de l'utiliser
        if (this.primaryStage != null) {
            primaryStage.setScene(scene2);
            primaryStage.show();
            cs.setVariable(this.variable);
            cs.setImage(studentChoice);
        } else {
            throw new IllegalStateException("Primary stage is not set");
        }
    }

    public List<Double> readDataFromFile(File file) throws IOException {
        List<Double> variable = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            for (String part : parts) {
                try {
                    double value = Double.parseDouble(part);
                    variable.add(value);
                } catch (NumberFormatException e) {
                    // Ignorez les valeurs non numériques
                }
            }
        }
        br.close();
        this.variable = variable; // Assignez la liste à la variable d'instance
        return variable;
    }

    public List<Double> getList() {
        return this.variable;
    }
}
