package com.example.projet_info2024;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Students extends ObjectScheme{

    int modeChoisi = 1;

    //Méthodes



    //Méthodes venant de la class abstract ObjectScheme

    @Override
    public void changeImage(Image image) {
        switch (modeChoisi){
            case 1 : //Mode avec 5 chercheurs
                imageStudents = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/Samuel.png");
                break;
            case 2 : //Mode avec 4 chercheurs
                imageStudents = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/Louis.png");
                break;
            case 3 : //Mode avec 3 chercheurs
                imageStudents = new Image("file:C:/Users/popo1/OneDrive - HESSO/HES/inf/javaFX/projectTest/Jeremie.png");
                break;
        }
    }

    @Override
    public void changePosition(double[] position_X, double[] position_Y) {
        // Met à jour les positions et dessine chaque students
        for (int i = 0; i < nbrStudents; i++) {
            // Met à jour l'étape en fonction de la position
            if (positionX[i] >= ZoneMax_X - imageStudents.getWidth()) {
                steps[i] = 2;
            }
            if ((positionY[i] >= radiusFinal *2* nbrStudents * nbrDecalage + (i+1)* radiusFinal *2 ) && (steps[i] == 2)) {
                steps[i] = 3;
            }
            if (positionX[i] <= ZoneMin_X && steps[i] == 3) {
                steps[i] = 4;
            }
            if ((positionY[i] >= radiusFinal * 4 * nbrStudents * nbrDecalage + (i+1)* radiusFinal *2) && (steps[i] == 4)) {
                steps[i] = 5;
            }

            // Met à jour la position en fonction de l'étape
            switch (steps[i]) {
                case 1:
                case 5:
                    positionX[i] += velocityMagnitude;
                    break;
                case 2:
                case 4:
                    positionY[i] += velocityMagnitude;
                    break;
                case 3:
                    positionX[i] -= velocityMagnitude;
                    break;
            }

            // Dessine les élèves à la nouvelle position
            gc.drawImage(imageStudents, positionX[i], positionY[i]);

        }
    }

    @Override
    public void changeRadiusCommunication(int radius) {
        this.radiusFinal = radius;
    }

    @Override
    public ArrayList<double[]> getPosition() {
        ArrayList<double[]> position = new ArrayList<>();
        position.add(positionX);
        position.add(positionY);
        return position;
    }

    @Override
    public float getRadiusCommunication() {
        return this.radiusCommunication;
    }

    @Override
    public Image getImage() {
        return imageStudents;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        switch (modeChoisi){
            case 1 : // 5 chercheurs
                if (positionY[1] - positionY[0] < radiusCommunication && positionY[2] - positionY[1] < radiusCommunication && positionY[3] - positionY[2] < radiusCommunication && positionY[4] - positionY[3] < radiusCommunication) {
                    communicationState = true;
                }
            case 2 : // 4 chercheurs
                if (positionY[1] - positionY[0] < radiusCommunication && positionY[2] - positionY[1] < radiusCommunication && positionY[3] - positionY[2] < radiusCommunication) {
                    communicationState = true;
                }
            case 3 : // 3 chercheurs
                if (positionY[1] - positionY[0] < radiusCommunication && positionY[2] - positionY[1] < radiusCommunication) {
                    communicationState = true;
                }
        }
        return communicationState;
    }

}
