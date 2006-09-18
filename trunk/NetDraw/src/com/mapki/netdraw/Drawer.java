package com.mapki.netdraw;

import java.awt.Color;
import java.net.InetAddress;

public class Drawer {
    private Color drawColor;
    private String name;
    private InetAddress address;
    
    public Drawer(Color drawColor, String name, InetAddress address) {
        this.drawColor = drawColor;
        this.name = name;
        this.address = address;
    }
    
    public InetAddress getAddress() {
        return address;
    }
    
    public Color getDrawColor() {
        return drawColor;
    }
    
    public String getName() {
        return name;
    }
}
