package com.mapki.netdraw;

import com.mapki.netdraw.gui.DrawingGui;
import com.mapki.netdraw.network.DrawConnector;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DrawConnector connector = new DrawConnector();
        DrawingGui gui = new DrawingGui(connector);
        gui.start();
    }

}
