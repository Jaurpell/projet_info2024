package com.example.projet_info2024;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerSimulation {
    private List<Double> ListVariable;  // Liste des variables paramétrables
    private double ZONE_MAX_X; // Taille maximale de la zone en X
    private double ZONE_MIN_X = 0; // Taille Minimale de la zone en X
    private double ZONE_MAX_Y;  // Taille maximale de la zone en Y
    private double ZONE_MIN_Y = 0;  // Taille minimale de la zone en Y (définie à 0)
    private double diplomeX;  // Position X du diplôme
    private double diplomeY;  // Position Y du diplôme
    private double diplomeRayon = 20;  // Rayon du diplôme
    private double detectionRayon;  // Rayon de détection du diplôme
    private List<Integer> studentsFoundDiplome = new ArrayList<>();  // Liste des étudiants ayant trouvé le diplôme
    private double COMMUNICATION_RADIUS;  // Rayon de communication entre étudiants
    private GraphicsContext gc;  // Contexte graphique pour le dessin sur Canvas


    private int rayon = 20;  // Rayon des étudiants pour leur tailles
    private double attractionStrength = 0.05;  // Force d'attraction vers le diplôme
    private Image student;  // Image représentant un étudiant
    private Image diplome;  // Image représentant un diplôme
    private int nbreStudent = 1;
    private boolean running = false;
    private int NUM_STUDENTS = 6;  // Nombre d'étudiants
    private double[] newX ;  // Nouvelles positions X des étudiants
    private double[] newY ;  // Nouvelles positions Y des étudiants
    private double[] xVelocity ;  // Vitesse en X des étudiants
    private double[] yVelocity ;  // Vitesse en Y des étudiants
    private AnimationTimer timer;  // Instance de l'AnimationTimer

    // Initialiser la taille de l'environnement
    public void setVariable(List<Double> variable) {
        this.ListVariable = variable;
    }

    public void setImage(int nbre) {
        this.nbreStudent = nbre;
    }

    @FXML
    private Canvas canvasSimu;

    @FXML
    public void initialize() {
        gc = canvasSimu.getGraphicsContext2D();
        ZONE_MAX_X = canvasSimu.getWidth();
        ZONE_MAX_Y = canvasSimu.getHeight();
    }

    // Gestionnaire pour le bouton Stop
    @FXML
    private void handleStopButtonAction() {
        running = false;
    }

    // Gestionnaire pour le bouton Restart
    @FXML
    private void handleRestartButtonAction() {
        running = true;
    }

    @FXML
    // Méthode appelée lors du démarrage de la simulation
    public void handleStartButtonAction() {
        if (running) {
            return;
        }

        running = true;

        // Arrête l'animation en cours si elle existe
        if (timer != null) {
            timer.stop();
        }

        // Initialisation des paramètres à partir de ListVariable
        switch (nbreStudent) {
            case 1:
                student = new Image("Louis.jpeg");
                this.NUM_STUDENTS = 4;
                break;
            case 2:
                student = new Image("Jeremy.jpeg");
                this.NUM_STUDENTS = 3;
                break;
            case 3:
                student = new Image("SamuelPetit.jpg");
                this.NUM_STUDENTS = 5;
                break;
        }
         newX = new double[NUM_STUDENTS];  // Nouvelles positions X des étudiants
         newY = new double[NUM_STUDENTS];  // Nouvelles positions Y des étudiants
         xVelocity = new double[NUM_STUDENTS];  // Vitesse en X des étudiants
         yVelocity = new double[NUM_STUDENTS];  // Vitesse en Y des étudiants

        diplome = new Image("earth.png");
        diplomeX = ListVariable.get(2);
        diplomeY = ListVariable.get(3);
        detectionRayon = ListVariable.get(5);
        COMMUNICATION_RADIUS = ListVariable.get(6);

        Random random = new Random();
        // Initialisation aléatoire des positions des étudiants
        for (int i = 0; i < NUM_STUDENTS; i++) {
            newX[i] = random.nextInt((int) (ZONE_MAX_X - rayon));
            newY[i] = random.nextInt((int) (ZONE_MAX_Y - rayon));
            xVelocity[i] = ListVariable.get(4);  // Vitesse initiale en X
            yVelocity[i] = ListVariable.get(4);  // Vitesse initiale en Y
        }

        // Création de l'instance d'AnimationTimer
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!running) {
                    return;
                }
                gc.clearRect(0, 0, ZONE_MAX_X, ZONE_MAX_Y);  // Efface le canvas

                // Dessine les étudiants et gère leur mouvement
                for (int i = 0; i < NUM_STUDENTS; i++) {
                    if (xVelocity[i] != 0 || yVelocity[i] != 0) {
                        double distanceToDiplome = Math.sqrt(Math.pow(newX[i] + rayon / 2 - diplomeX, 2) +
                                Math.pow(newY[i] + rayon / 2 - diplomeY, 2));

                        if (distanceToDiplome < detectionRayon + diplomeRayon) {
                            xVelocity[i] = 0;
                            yVelocity[i] = 0;
                            studentsFoundDiplome.add(i);
                            informOtherStudents(i);  // Informe les autres étudiants de la découverte

                        } else {
                            // Calcule la direction vers le diplôme
                            double angle = Math.random() * 6.28;
                            xVelocity[i] += Math.cos(angle) * attractionStrength;
                            yVelocity[i] += Math.sin(angle) * attractionStrength;

                            // Met à jour les positions des étudiants
                            newX[i] += xVelocity[i];
                            newY[i] += yVelocity[i];

                            // Gestion des rebonds sur les bords de la zone
                            if (newX[i] <= ZONE_MIN_X || newX[i] >= ZONE_MAX_X - rayon) {
                                xVelocity[i] = -xVelocity[i];
                            }
                            if (newY[i] <= ZONE_MIN_Y || newY[i] >= ZONE_MAX_Y - rayon) {
                                yVelocity[i] = -yVelocity[i];
                            }
                        }
                    }

                    // Dessine l'étudiant à sa position actuelle
                    gc.drawImage(student, newX[i], newY[i], rayon, rayon);
                }

                // Dessine le diplôme à sa position
                gc.drawImage(diplome, diplomeX - diplomeRayon / 2, diplomeY - diplomeRayon / 2, diplomeRayon, diplomeRayon);
            }
        };

        timer.start();  // Démarre l'animation
    }

    // Méthode pour informer les autres étudiants de la position du diplôme
    private void informOtherStudents(int studentIndex) {
        for (int i = 0; i < NUM_STUDENTS; i++) {
            if (i != studentIndex) {
                double distance = Math.sqrt(Math.pow(newX[i] - newX[studentIndex], 2) +
                        Math.pow(newY[i] - newY[studentIndex], 2));
                if (distance <= COMMUNICATION_RADIUS) {
                    // Calcule la direction vers le diplôme
                    double angle = Math.atan2(diplomeY - (newY[i] + rayon / 2), diplomeX - (newX[i] + rayon / 2));
                    double distanceToDiplome = Math.sqrt(Math.pow(diplomeX - (newX[i] + rayon / 2), 2) +
                            Math.pow(diplomeY - (newY[i] + rayon / 2), 2));
                    double speed = ListVariable.get(4);

                    // Déplace l'étudiant vers le diplôme
                    xVelocity[i] = Math.cos(angle) * speed;
                    yVelocity[i] = Math.sin(angle) * speed;

                    // Si l'étudiant atteint le diplôme, arrête son mouvement
                    if (distanceToDiplome < speed) {
                        newX[i] = diplomeX - rayon / 2;
                        newY[i] = diplomeY - rayon / 2;
                        xVelocity[i] = 0;
                        yVelocity[i] = 0;
                    }

                    studentsFoundDiplome.add(i);  // Ajoute l'étudiant à la liste des ayant trouvé le diplôme
                }
            }
        }
    }
}
