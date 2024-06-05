package com.example.projet_info2024;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerSimulation {

    private static final double ZONE_MIN_X = 0;
    private double ZONE_MAX_X;
    private double ZONE_MIN_Y = 0;
    private double ZONE_MAX_Y;
    private double sunX = 192;  // Position X du soleil
    private double sunY = 192;  // Position Y du soleil
    private double sunRayon = 20;  // Rayon du soleil
    private double detectionRayon = 10;  // Rayon de détection du soleil
    private List<Integer> planetsFoundSun = new ArrayList<>();
    private static final double COMMUNICATION_RADIUS = 30;  // Rayon de communication entre planètes

    private static final int NUM_PLANETS = 5;
    private double[] newX = new double[NUM_PLANETS];
    private double[] newY = new double[NUM_PLANETS];
    private double[] xVelocity = new double[NUM_PLANETS];
    private double[] yVelocity = new double[NUM_PLANETS];
    private int rayon = 20;  // Rayon des planètes
    private double attractionStrength = 0.05;  // Force d'attraction vers le soleil

    @FXML
    private Canvas canvasSimu;
    @FXML
    public void initialize() {


        GraphicsContext gc = canvasSimu.getGraphicsContext2D();
        ZONE_MAX_X = canvasSimu.getWidth();
        ZONE_MAX_Y = canvasSimu.getHeight();

        Image earth = new Image("earth.png");
        Image sun = new Image("Jeremy.jpeg");

        Random random = new Random();
        for (int i = 0; i < NUM_PLANETS; i++) {
            newX[i] = random.nextInt((int) (ZONE_MAX_X - rayon));
            newY[i] = random.nextInt((int) (ZONE_MAX_Y - rayon));
            xVelocity[i] = 5;  // Vitesse initiale entre -3 et 3
            yVelocity[i] = 5;
        }

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);

                // Dessiner les planètes
                for (int i = 0; i < NUM_PLANETS; i++) {
                    if (xVelocity[i] != 0 || yVelocity[i] != 0) {
                        double distanceToSun = Math.sqrt(Math.pow(newX[i] + rayon / 2 - sunX, 2) + Math.pow(newY[i] + rayon / 2 - sunY, 2));

                        if (distanceToSun < detectionRayon + sunRayon) {
                            xVelocity[i] = 0;
                            yVelocity[i] = 0;
                            if (!planetsFoundSun.contains(i)) {
                                planetsFoundSun.add(i);
                                informOtherPlanets(i);
                            }
                        } else {
                            double angle = Math.atan2(sunY - (newY[i] + rayon / 2), sunX - (newX[i] + rayon / 2));
                            xVelocity[i] += Math.cos(angle) * attractionStrength;
                            yVelocity[i] += Math.sin(angle) * attractionStrength;

                            newX[i] += xVelocity[i];
                            newY[i] += yVelocity[i];

                            if (newX[i] <= ZONE_MIN_X || newX[i] >= ZONE_MAX_X - rayon) {
                                xVelocity[i] = -xVelocity[i];
                            }
                            if (newY[i] <= ZONE_MIN_Y || newY[i] >= ZONE_MAX_Y - rayon) {
                                yVelocity[i] = -yVelocity[i];
                            }
                        }
                    }

                    gc.drawImage(earth, newX[i], newY[i], rayon, rayon);
                }

                // Dessiner le soleil
                gc.drawImage(sun, sunX - sunRayon / 2, sunY - sunRayon / 2, sunRayon, sunRayon);
            }
        }.start();

        // Lire les données à partir du fichier
        readDataFromFile("config.txt");
    }

    private void informOtherPlanets(int planetIndex) {
        for (int i = 0; i < NUM_PLANETS; i++) {
            if (i != planetIndex) {
                double distance = Math.sqrt(Math.pow(newX[i] - newX[planetIndex], 2) + Math.pow(newY[i] - newY[planetIndex], 2));
                if (distance <= COMMUNICATION_RADIUS) {
                    double angle = Math.atan2(sunY - (newY[i] + rayon / 2), sunX - (newX[i] + rayon / 2));
                    double distanceToSun = Math.sqrt(Math.pow(sunX - (newX[i] + rayon / 2), 2) + Math.pow(sunY - (newY[i] + rayon / 2), 2));
                    double speed = 2;

                    xVelocity[i] = Math.cos(angle) * speed;
                    yVelocity[i] = Math.sin(angle) * speed;

                    if (distanceToSun < speed) {
                        newX[i] = sunX - rayon / 2;
                        newY[i] = sunY - rayon / 2;
                        xVelocity[i] = 0;
                        yVelocity[i] = 0;
                    }

                    planetsFoundSun.add(i);
                }
            }
        }
    }

    private void readDataFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    switch (parts[0]) {
                        case "environment_size_W_H":
                            ZONE_MAX_X = Double.parseDouble(parts[1]);
                            ZONE_MAX_Y = Double.parseDouble(parts[2]);
                            break;
                        // Ajoutez d'autres cas pour les autres variables à mettre à jour
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
