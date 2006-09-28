/**
 * 
 */
package com.mapki.threedee.gui;

import java.awt.Container;

import javax.swing.JFrame;

import com.mapki.threedee.universe.ObjectUniverse;

/**
 * @author Ian Dees
 *
 */
public class LookingGlass {
    
    private JFrame frame;
    private ObjectUniverse universe;

    public LookingGlass(ObjectUniverse universe) {
        this.universe = universe;
        initGUI();
    }

    private void initGUI() {
        this.frame = new JFrame("Looking Glass");
        frame.setSize(500, 500);
        
        Container c = frame.getContentPane();
        GlassPane panel = new GlassPane(universe);
        c.add(panel);
    }

    public void start() {
        // TODO Auto-generated method stub
        
    }

}
