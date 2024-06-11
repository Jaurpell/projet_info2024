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

public class ControllerAffichage extends MainApplication {
    private List<Double> variable;
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

        // Ensure that the cs object is initialized and variable is set
        if (cs == null || variable == null) {
            System.out.println("Simulation controller or variable list not initialized!");
            return;
        }

        // Load the FXML file for the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        Parent root = loader.load();
        cs = loader.getController();
        cs.setVariable(variable);
        cs.startButton();

        // Create a new scene
        Scene scene = new Scene(root);

        // Create a new window and set the scene
        Stage newStage = new Stage();
        newStage.setScene(scene);

        // Show the new window
        newStage.show();
    }

    @FXML
    private void openSimulationScene() throws IOException {
        System.out.println("Opening simulation scene");

        if (cs == null) {
            initializeSimulationController();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            List<Double> variables = readDataFromFile(selectedFile);
            System.out.println(variables);

            // Ensure that the cs object is not null before calling its methods
            if (cs != null) {
                cs.setVariable(variables);
            }
        }
    }

    private void initializeSimulationController() throws IOException {
        // Load the FXML file for the simulation window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FxmlMain.fxml"));
        Parent root = loader.load();
        cs = loader.getController();
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
                    // Ignore non-numeric values
                }
            }
        }
        br.close();
        this.variable = variable; // Assign the list to the instance variable
        return variable;
    }

    public List<Double> getList() {
        return this.variable;
    }
}
