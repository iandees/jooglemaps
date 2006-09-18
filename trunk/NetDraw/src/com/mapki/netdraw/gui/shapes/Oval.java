package com.mapki.netdraw.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.mapki.netdraw.gui.NetDrawable;

public class Oval implements NetDrawable {
    private Point nw;
    private Point se;
    private int weight;
    
    public Oval() {
        this.nw = new Point();
        this.se = new Point();
        this.weight = 0;
    }
    
    public Oval(Point point1, Point point2, int weight) {
        this.nw = point1;
        this.se = point2;
        this.weight = weight;
    }

    public String serialize() {
        return "OVAL{"+nw.x+","+nw.y+","+se.x+","+se.y+"} WEIGHT{"+this.weight+"}\n";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        Stroke orig = g2d.getStroke();
        g2d.setStroke(new BasicStroke(this.weight));
        g2d.drawOval(nw.x, nw.y, se.x-nw.x, se.y-nw.y);
        g2d.setStroke(orig);
        
    }

    public String getName() {
        return "Oval";
    }

    public void setPoint1(Point point1) {
        this.nw = point1;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
