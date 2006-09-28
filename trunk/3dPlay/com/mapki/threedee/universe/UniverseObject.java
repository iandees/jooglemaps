/**
 * 
 */
package com.mapki.threedee.universe;

import java.awt.Graphics;

/**
 * @author Ian Dees
 *
 */
public class UniverseObject {
    private double x;
    private double y;
    private double z;
    
    public void paint(Graphics g) {
        // Translate from object-local to screen-local
        int screenX = ObjectUniverse.getGlobalScale() * x;
        int screenY = ObjectUniverse.getGlobalScale() * y;
    }

    
}
