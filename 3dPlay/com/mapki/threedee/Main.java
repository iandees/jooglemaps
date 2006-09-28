/**
 * 
 */
package com.mapki.threedee;

import com.mapki.threedee.gui.LookingGlass;
import com.mapki.threedee.universe.ObjectUniverse;

/**
 * @author Ian Dees
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ObjectUniverse universe = new ObjectUniverse();
        LookingGlass glass = new LookingGlass(universe);
        glass.start();
    }

}
