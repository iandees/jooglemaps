package com.mapki.netdraw.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.mapki.netdraw.gui.NetDrawable;

public class Rectangle implements NetDrawable {
    private Point nw;
    private Point se;
    private int weight;
    
    public Rectangle() {
        this.nw = new Point();
        this.se = new Point();
        this.weight = 0;
    }
    
    public Rectangle(Point point1, Point point2, int weight) {
        setPoint1(point1);
        setPoint2(point2);
        this.weight = weight;
    }

    public String serialize() {
        return "RECT{"+nw.x+","+nw.y+","+se.x+","+se.y+"} WEIGHT{"+this.weight+"}\n";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        Stroke orig = g2d.getStroke();
        g2d.setStroke(new BasicStroke(this.weight));
        g2d.drawRect(nw.x, nw.y, se.x-nw.x, se.y-nw.y);
        g2d.setStroke(orig);
        
    }

    public String getName() {
        return "Rectangle";
    }

    public void setPoint1(Point point1) {
        this.nw = point1;
    }

    public void setPoint2(Point point2) {
        this.se = point2;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
