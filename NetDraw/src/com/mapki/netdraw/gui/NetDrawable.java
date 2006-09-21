package com.mapki.netdraw.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;

import com.mapki.netdraw.Drawer;

public class NetDrawable implements NetDrawableInterface {

    protected Point nw;
    protected Point se;
    protected String name;
    protected Color color;
    protected Drawer owner;
    protected Stroke stroke;
    protected int weight;
    
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
        return "";
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
