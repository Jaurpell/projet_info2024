package com.example.projet_info2024;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControllerSimulation {
    public void setEnvironmentSize(double width, double height) {
        ZONE_MAX_X = width;
        ZONE_MAX_Y = height;
    }

    public void setTargetPosition(double x, double y) {
        target_X = x;
        target_Y = y;
    }

    public void setAgentsSpeed(double speed) {
        vitesse = (int) speed;
    }

    public void setAgentsDetectionRange(double detectionRange) {
        // Modifier cette méthode en fonction de ce que représente la portée de détection des agents dans votre simulation
        // Par exemple, si cette valeur est en pixels, vous pouvez l'affecter directement à une variable correspondante.
        // Sinon, vous devrez peut-être ajuster la logique en conséquence.
    }

    public void setAgentsCommunicationRange(double communicationRange) {
        // Modifier cette méthode de la même manière que setAgentsDetectionRange, mais pour la portée de communication des agents.
    }

    private static final double ZONE_MIN_X = 0;
    private double ZONE_MAX_X;
    private double ZONE_MIN_Y = 0;
    private double ZONE_MAX_Y;
    private double target_X = 192;
    private double target_Y = 192;

    private static final int NUM_PLANETS = 5;
    private double[] newX = new double[NUM_PLANETS];
    private double[] newY = new double[NUM_PLANETS];
    private int[] steps = new int[NUM_PLANETS];
    private int rayon = 5;
    private int vitesse = 15;
    private double n;
    private int NbrDecalage = 1;

    @FXML
    private Canvas canvasSimu;

    @FXML
    public void initialize() {


        GraphicsContext gc = canvasSimu.getGraphicsContext2D();
        ZONE_MAX_X = canvasSimu.getWidth();
        ZONE_MAX_Y = canvasSimu.getHeight();
        n = ZONE_MAX_Y / (NUM_PLANETS * rayon + (2 * rayon));
        if (ZONE_MAX_Y / ((n % 2) - NbrDecalage) < newY[NUM_PLANETS - 1] && (ZONE_MAX_Y > newY[NUM_PLANETS - 1])) {
            NbrDecalage += 1;
        }

        Image student = new Image("earth.png");
        Image sun = new Image("Jeremy.jpeg");

        for (int i = 0; i < NUM_PLANETS; i++) {
            newX[i] = 0;
            newY[i] = i * 2 * rayon;
            steps[i] = 1;
        }

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1_000_000_000.0;

                gc.clearRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);

                for (int i = 0; i < NUM_PLANETS; i++) {
                    if (newX[i] >= ZONE_MAX_X - student.getWidth()) {
                        steps[i] = 2;
                    }
                    if ((newY[i] >= rayon * 2 * NUM_PLANETS * NbrDecalage + (i + 1) * rayon * 2) && (steps[i] == 2)) {
                        steps[i] = 3;
                    }
                    if (newX[i] <= ZONE_MIN_X && steps[i] == 3) {
                        steps[i] = 4;
                    }
                    if ((newY[i] >= rayon * 2 * NUM_PLANETS * NbrDecalage + (i + 1) * rayon * 2) && (steps[i] == 4)) {
                        steps[i] = 5;
                    }
                    if (newY[i] >= ZONE_MAX_Y - rayon * 2) {
                        steps[i] = 6;
                    }
                    if (newX[i] >= ZONE_MAX_X - student.getWidth() && steps[i] == 6) {
                        steps[i] = 7;
                    }

                    switch (steps[i]) {
                        case 1:
                        case 5:
                        case 6:
                            newX[i] += vitesse;
                            break;
                        case 2:
                        case 4:
                            newY[i] += vitesse;
                            break;
                        case 3:
                            newX[i] -= vitesse;
                            break;
                    }

                    gc.drawImage(student, newX[i], newY[i]);
                }

                gc.drawImage(sun, target_X, target_Y);
            }
        }.start();
    }



}
