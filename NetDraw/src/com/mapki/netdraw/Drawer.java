package com.mapki.netdraw;

import java.awt.Color;
import java.net.InetAddress;
import java.net.Socket;

import com.mapki.netdraw.network.DrawerSocket;

public class Drawer {
    private Color drawColor;
    private String name;
    private InetAddress address;
    
    public Drawer(Color drawColor, String name, InetAddress address) {
        this.drawColor = drawColor;
        this.name = name;
        this.address = address;
    }
    
    public Drawer(DrawerSocket drawSocket) {
        // TODO Talking to the client over the socket should be done somewhere else
        this.address = drawSocket.getInetAddress();
        this.name = drawSocket.getName();
        this.drawColor = drawSocket.getColor();
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
