package com.example.projet_info2024;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Diploma extends ObjectScheme{

    //Méthodes venant de la class abstract ObjectScheme

    @Override
    public void changeImage(Image image) {
        imageDiploma = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/Diplome.png");
    }

    @Override
    public void changePosition(double[] position_X, double[] position_Y) {
        for (int i = 0; i < 1; i++) {
            targetX[i] = position_X[i];
            targetY[i] = position_Y[i];
            // Dessine le soleil (position fixe)
            gc.drawImage(imageDiploma, targetX[i], targetY[i]);
        }


    }

    @Override
    public void changeRadiusCommunication(int radius) {
        this.radiusCommunication = radius;
    }

    @Override
    public ArrayList<double[]> getPosition() {
        ArrayList<double[]> position = new ArrayList();
        position.add(targetX);
        position.add(targetY);
        return position;
    }

    @Override
    public float getRadiusCommunication() {
        return this.radiusCommunication;
    }

    @Override
    public Image getImage() {
        return imageDiploma;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

}
