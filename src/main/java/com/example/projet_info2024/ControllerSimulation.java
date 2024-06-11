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
    public void setEnvironmentSize(double width, double height) {
        this.ZONE_MAX_X = width;
        this.ZONE_MAX_Y = height;
    }

    public void setTargetPosition(double x, double y) {
        this.diplomeX = x;
        this.diplomeY = y;
    }

    public void setAgentsSpeed(double speed) {
        for (int i = 0; i < NUM_STUDENTS; i++) {
            xVelocity[i] = speed;
            yVelocity[i] = speed;
        }
    }

    public void setAgentsDetectionRange(double detectionRange) {
        this.detectionRayon = detectionRange;
    }

    public void setAgentsCommunicationRange(double communicationRange) {
        this.COMMUNICATION_RADIUS = communicationRange;
    }

    private static final double ZONE_MIN_X = 0;
    private double ZONE_MAX_X;
    private double ZONE_MIN_Y = 0;
    private double ZONE_MAX_Y;
    private double diplomeX = 192;  // Position X du diplome
    private double diplomeY = 192;  // Position Y du diplome
    private double diplomeRayon = 20;  // Rayon du diplome
    private double detectionRayon = 10;  // Rayon de détection du diplome
    private List<Integer> studentsFoundDiplome = new ArrayList<>();
    private double COMMUNICATION_RADIUS = 100;  // Rayon de communication entre étudiants

    private static final int NUM_STUDENTS = 5;
    private double[] newX = new double[NUM_STUDENTS];
    private double[] newY = new double[NUM_STUDENTS];
    private double[] xVelocity = new double[NUM_STUDENTS];
    private double[] yVelocity = new double[NUM_STUDENTS];
    private int rayon = 20;  // Rayon des étudiants
    private double attractionStrength = 0.05;  // Force d'attraction vers le diplome

    @FXML
    private Canvas canvasSimu;

    @FXML
    public void initialize() {
        GraphicsContext gc = canvasSimu.getGraphicsContext2D();
        ZONE_MAX_X = canvasSimu.getWidth();
        ZONE_MAX_Y = canvasSimu.getHeight();

        Image student = new Image("Louis.jpeg");
        Image diplome = new Image("Jeremy.jpeg");

        Random random = new Random();
        for (int i = 0; i < NUM_STUDENTS; i++) {
            newX[i] = random.nextInt((int) (ZONE_MAX_X - rayon));
            newY[i] = random.nextInt((int) (ZONE_MAX_Y - rayon));
            xVelocity[i] = 5;
            yVelocity[i] = 5;
        }

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);

                // Dessiner les étudiants
                for (int i = 0; i < NUM_STUDENTS; i++) {
                    if (xVelocity[i] != 0 || yVelocity[i] != 0) {
                        double distanceToDiplome = Math.sqrt(Math.pow(newX[i] + rayon / 2 - diplomeX, 2) + Math.pow(newY[i] + rayon / 2 - diplomeY, 2));

                        if (distanceToDiplome < detectionRayon + diplomeRayon) {
                            xVelocity[i] = 0;
                            yVelocity[i] = 0;
                            if (!studentsFoundDiplome.contains(i)) {
                                studentsFoundDiplome.add(i);
                                informOtherStudents(i);
                            }
                        } else {
                            double angle = Math.atan2(diplomeY - (newY[i] + rayon / 2), diplomeX - (newX[i] + rayon / 2));
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

                    gc.drawImage(student, newX[i], newY[i], rayon, rayon);
                }

                // Dessiner le diplome
                gc.drawImage(diplome, diplomeX - diplomeRayon / 2, diplomeY - diplomeRayon / 2, diplomeRayon, diplomeRayon);
            }
        }.start();

    }

    private void informOtherStudents(int studentIndex) {
        for (int i = 0; i < NUM_STUDENTS; i++) {
            if (i != studentIndex) {
                double distance = Math.sqrt(Math.pow(newX[i] - newX[studentIndex], 2) + Math.pow(newY[i] - newY[studentIndex], 2));
                if (distance <= COMMUNICATION_RADIUS) {
                    double angle = Math.atan2(diplomeY - (newY[i] + rayon / 2), diplomeX - (newX[i] + rayon / 2));
                    double distanceToDiplome = Math.sqrt(Math.pow(diplomeX - (newX[i] + rayon / 2), 2) + Math.pow(diplomeY - (newY[i] + rayon / 2), 2));
                    double speed = 2;

                    xVelocity[i] = Math.cos(angle) * speed;
                    yVelocity[i] = Math.sin(angle) * speed;

                    if (distanceToDiplome < speed) {
                        newX[i] = diplomeX - rayon / 2;
                        newY[i] = diplomeY - rayon / 2;
                        xVelocity[i] = 0;
                        yVelocity[i] = 0;
                    }

                    studentsFoundDiplome.add(i);
                }
            }
        }
    }

}

