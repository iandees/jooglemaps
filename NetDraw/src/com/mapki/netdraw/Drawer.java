package com.mapki.netdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.mapki.netdraw.network.DrawerSocket;

public class Drawer {
    private Color drawColor;
    private String name;
    private InetAddress address;
    private Stroke stroke;
    
    public Drawer() throws UnknownHostException {
        this(Color.BLACK, new BasicStroke(1.0f), "me", InetAddress.getLocalHost());
    }
    
    public Drawer(Color drawColor, Stroke drawStroke, String name, InetAddress address) {
        this.drawColor = drawColor;
        this.stroke = drawStroke;
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

    public void setColor(Color c) {
        this.drawColor = c;
    }

    public Stroke getDrawStroke() {
        return this.stroke;
    }
    
    public void setDrawStroke(int weight) {
        this.stroke = new BasicStroke(weight);
    }
}
