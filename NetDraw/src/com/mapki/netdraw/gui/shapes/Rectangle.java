package com.mapki.netdraw.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.mapki.netdraw.Drawer;
import com.mapki.netdraw.gui.NetDrawable;
import com.mapki.netdraw.gui.NetDrawableInterface;

public class Rectangle extends NetDrawable implements NetDrawableInterface {
    private Point nw;
    private Point se;
    private int weight;
    private Color color;
    
    public Rectangle() {
        this.nw = new Point();
        this.se = new Point();
        this.weight = 0;
        this.setStroke(new BasicStroke(1.0f));
    }
    
    public Rectangle(Color color, Point point1, Point point2, int weight) {
        this.color = color;
        setPoint1(point1);
        setPoint2(point2);
        this.weight = weight;
    }

    public String serialize() {
        return "RECT{"+nw.x+","+nw.y+","+se.x+","+se.y+"} WEIGHT{"+this.weight+"}";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        Stroke orig = g2d.getStroke();
        g2d.setStroke(this.stroke);
        g2d.drawRect(nw.x, nw.y, Math.abs(se.x-nw.x), Math.abs(se.y-nw.y));
        g2d.setStroke(orig);
        
    }

    public String getName() {
        return "Rectangle";
    }

    public void setPoint2(Point point2) {
        if(point2.x < this.nw.x) {
            if(point2.y < this.nw.y) {
                this.se = this.nw;
                this.nw = point2;
            } else {
                this.se = new Point(this.nw.x, point2.y);
                this.nw = new Point(point2.x, this.nw.y);
            }
        } else {
            if(point2.y < this.nw.y) {
                this.se = new Point(point2.x, this.nw.y);
                this.nw = new Point(this.nw.x, point2.y);
            } else {
                this.se = point2;
            }
        }
    }
}
