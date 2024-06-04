package com.example.projet_info2024;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Abstract class representing the different object that you should implement (agent and target).
 */
public abstract class ObjectScheme {
// Variables
protected int ZoneMax_X, ZoneMax_Y, ZoneMin_X, ZoneMin_Y;
protected int nbrStudents = 5;
protected double[] positionX = new double[nbrStudents];
protected double[] positionY = new double[nbrStudents];
protected int radiusCommunication;
protected int radiusDetection;
protected int radiusFinal;
protected float[] direction;
protected int velocityMagnitude;
protected Image imageStudents;
protected Image imageDiploma;
protected int[] steps = new int[nbrStudents];
protected int nbrDecalage = 1;
private Canvas canvas;
GraphicsContext gc = canvas.getGraphicsContext2D();
protected double[] targetX, targetY;

// Set methods
/**
 * Change the object image.
 * @param image The new image to load
 */
public abstract void changeImage(Image image);

/**
 * Change the object position (x,y).
 * @param positionX The new position in the x-axis
 * @param positionY The new position in the y-axis
 */
public abstract void changePosition(double[] positionX, double[] positionY);

/**
 * Change the communication radius value.
 * @param radius The new radius value
 */
public abstract void changeRadiusCommunication(int radius);

// Get methods
/**
 * This method returns the object position.
 * @return A float array containing the position {x,y}
 */
public abstract ArrayList<double[]> getPosition();

/**
 * This method returns the object communication radius.
 * @return A float containing the radius
 */
public abstract float getRadiusCommunication();

/**
 * This method returns the variable containing the image of the object.
 * @return The variable containing the image of the object
 */
public abstract Image getImage();

// Other method
/**
 * This method checks the distance between the current object (this) and another object.
 * If the distance is less than the communication radius, then the method returns true, indicating
 * that the objects can communicate. Otherwise, it returns false, meaning no communication is possible.
 * You must use this method before attempting to exchange information between objects.
 *
 * @param object The other ObjectScheme
 *
 * @return The possibility to communicate with the object (true or false)
 */
public abstract boolean isCommunication(ObjectScheme object);

}

