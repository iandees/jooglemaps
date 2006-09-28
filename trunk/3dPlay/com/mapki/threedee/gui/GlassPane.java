/**
 * 
 */
package com.mapki.threedee.gui;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import com.mapki.threedee.universe.ObjectUniverse;
import com.mapki.threedee.universe.UniverseObject;

/**
 * @author Ian Dees
 *
 */
public class GlassPane extends JPanel {
    private ObjectUniverse universe;

    public GlassPane(ObjectUniverse universe) {
        this.universe = universe;
    }

    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        
        Iterator<UniverseObject> it = universe.getObjects();
        
        while(it.hasNext()) { 
            UniverseObject o = it.next();
            o.paint(g);
        }
    }
}
