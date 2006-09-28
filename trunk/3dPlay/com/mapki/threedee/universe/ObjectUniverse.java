/**
 * 
 */
package com.mapki.threedee.universe;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Ian Dees
 *
 */
public class ObjectUniverse {
    
    private double width; // x
    private double height; // y
    private double depth; // z
    
    private int screenWidth;
    private int screenHeight;
    
    private Vector<UniverseObject> objects;
    
    public ObjectUniverse(int screenWidth, int screenHeight, double width, double height, double depth) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public Iterator<UniverseObject> getObjects() {
        return this.objects.iterator();
    }

    public static double getGlobalScaleX() {
        return this.screenWidth / this.width;
    }

}
