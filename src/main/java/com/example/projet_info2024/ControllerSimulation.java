package com.example.projet_info2024;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

public class ControllerSimulation {

    @FXML
    private Canvas canvasSimu;

    private double ZONE_MAX_X;
    private double ZONE_MAX_Y;
    private double n;
    private int NUM_PLANETS = 5;  // Exemple de valeur, ajustez selon votre besoin
    private double rayon = 10;    // Exemple de valeur, ajustez selon votre besoin
    private int NbrDecalage = 0;
    private double[] newY = new double[NUM_PLANETS];  // Exemple de tableau, ajustez selon votre besoin

    public void initialize() {
        // Code de démarrage de la simulation
        GraphicsContext gc = canvasSimu.getGraphicsContext2D();
        ZONE_MAX_X = canvasSimu.getWidth();
        ZONE_MAX_Y = canvasSimu.getHeight();
        n = ZONE_MAX_Y / (NUM_PLANETS * rayon + (2* rayon));

        if (ZONE_MAX_Y / ((n % 2) - NbrDecalage) < newY[NUM_PLANETS - 1] && (ZONE_MAX_Y > newY[NUM_PLANETS - 1])) {
            NbrDecalage += 1;
        }

        // Ajoutez ici votre logique de simulation
        // Par exemple : gc.fillRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);
        startSimulation(gc);
    }

    private void startSimulation(GraphicsContext gc) {
        // Votre logique de simulation ici
        // Exemple : dessiner des cercles représentant des planètes
        gc.clearRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);  // Effacer le canvas
        gc.strokeOval(ZONE_MAX_X / 2 - rayon, ZONE_MAX_Y / 2 - rayon, 2 * rayon, 2 * rayon);
    }
}
