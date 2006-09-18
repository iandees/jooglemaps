package com.mapki.netdraw;

import com.mapki.netdraw.gui.DrawPane;
import com.mapki.netdraw.network.DrawConnector;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DrawConnector connector = new DrawConnector();
        DrawPane gui = new DrawPane(connector);
        gui.start();
    }

}
