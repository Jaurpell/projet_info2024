package com.example.projet_info2024;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControllerSimulation extends Application {

    private static final double ZONE_MIN_X = 0;
    private static final double ZONE_MAX_X = 800;
    private static final double ZONE_MIN_Y = 0;
    private static final double ZONE_MAX_Y = 800;

    private static final int NUM_PLANETS = 5;
    private double[] newX = new double[NUM_PLANETS];
    private double[] newY = new double[NUM_PLANETS];
    private int[] steps = new int[NUM_PLANETS];
    private int rayon = 40;
    private int n = 1;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Exemple de chronologie");
        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image student = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/earth.png");
        Image sun = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/sun.png");

        // Initialisation des positions et étapes pour chaque students
        for (int i = 0; i < NUM_PLANETS; i++) {
            newX[i] = 0;
            newY[i] = i * 2 * rayon;
            steps[i] = 1;
        }

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1_000_000_000.0;

                // Efface le canvas
                gc.clearRect(0, 0, 800, 800);

                // Met à jour les positions et dessine chaque students
                for (int i = 0; i < NUM_PLANETS; i++) {
                    // Met à jour l'étape en fonction de la position
                    if (newX[i] >= ZONE_MAX_X - student.getWidth()) {
                        steps[i] = 2;
                    }
                    if ((newY[i] >= rayon *2*NUM_PLANETS*n + (i+1)*rayon*2 ) && (steps[i] == 2)) {
                        steps[i] = 3;
                    }
                    if (newX[i] <= ZONE_MIN_X && steps[i] == 3) {
                        steps[i] = 4;
                    }
                    if ((newY[i] >= rayon * 4 * NUM_PLANETS * n + (i+1)*rayon*2) && (steps[i] == 4)) {
                        steps[i] = 5;
                    }

                    // Met à jour la position en fonction de l'étape
                    switch (steps[i]) {
                        case 1:
                        case 5:
                            newX[i] += 1;
                            break;
                        case 2:
                        case 4:
                            newY[i] += 1;
                            break;
                        case 3:
                            newX[i] -= 1;
                            break;
                    }

                    // Dessine les students à la nouvelle position
                    gc.drawImage(student, newX[i], newY[i]);
                }

                // Dessine le soleil (position fixe)
                gc.drawImage(sun, 196, 196);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


