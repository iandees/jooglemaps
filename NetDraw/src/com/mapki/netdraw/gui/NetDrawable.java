package com.mapki.netdraw.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;

import com.mapki.netdraw.Drawer;

public class NetDrawable implements NetDrawableInterface {

    protected Point nw;
    protected Point se;
    protected String name;
    protected Color color;
    protected Drawer owner;
    protected Stroke stroke;
    protected int weight;
    
    protected NetDrawable(String incomingString) {
        String splits[] = incomingString.split("/\t/");
        if(splits.length < 4) {
            // Don't do anything here.
        } else {
            this.name = splits[0];
            String points = splits[1];
            String color = splits[2];
            String stroke = splits[3];
            
            String pSplits[] = points.split("/,/");
            if(pSplits.length == 4) {
                int nwX = Integer.parseInt(pSplits[0]);
                int nwY = Integer.parseInt(pSplits[1]);
                this.nw = new Point(nwX, nwY);
                
                int seX = Integer.parseInt(pSplits[2]);
                int seY = Integer.parseInt(pSplits[3]);
                this.se = new Point(seX, seY);
            }
            
            this.color = Color.decode(color);
            
            int strokeW = Integer.parseInt(stroke);
            this.stroke = new BasicStroke(strokeW);
            this.weight = strokeW;
            
        }
    }
    
    public NetDrawable() {
        this.nw = new Point();
        this.se = new Point();
        this.name = "";
        this.color = Color.BLACK;
        this.weight = 1;
        this.stroke = new BasicStroke(this.weight);
    }
    
    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public Stroke getStroke() {
        return this.stroke;
    }

    public void paint(Graphics g) {
        return;
    }

    public String serialize() {
        return this.getName() + "\t" + 
        nw.x + "," + nw.y + "," + se.x + "," + se.y + "\t" +
        this.color.getRGB() + "\t" +
        this.stroke;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPoint1(Point point) {
        this.nw = point;
    }

    public void setPoint2(Point point) {
        this.se = point;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
        this.stroke = new BasicStroke(weight);
    }
}
