package com.example.projet_info2024;

import javafx.scene.image.Image;

public class Diploma extends ObjectScheme{

    //Méthodes venant de la class abstract ObjectScheme

    @Override
    public void changeImage(Image image) {
        imageDiploma = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/Diplome.png");
    }

    @Override
    public void changePosition(double[] position_X, double[] position_Y) {
        for (int i = 0; i < 1; i++) {
            targetX[i] = 0;
            targetY[i] = 0;
            // Dessine le soleil (position fixe)
            gc.drawImage(imageDiploma, position_X[i], position_Y[i]);
        }


    }

    @Override
    public void changeRadiusCommunication(int radius) {

    }

    @Override
    public double[] getPosition() {
        return new double[0];
    }

    @Override
    public float getRadiusCommunication() {
        return 0;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

}
