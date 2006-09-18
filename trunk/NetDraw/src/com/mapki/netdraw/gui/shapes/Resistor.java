package com.mapki.netdraw.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import com.mapki.netdraw.gui.NetDrawable;

public class Resistor implements NetDrawable {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int weight;
    
    public Resistor() {
        this.x1 = 0;
        this.x2 = 0;
        this.y1 = 0;
        this.y2 = 0;
        this.weight = 0;
    }
    
    public Resistor(Point point1, Point point2, int weight) {
        this.x1 = point1.x;
        this.y1 = point1.y;
        this.x2 = point2.x;
        this.y2 = point2.y;
        this.weight = weight;
    }

    public String serialize() {
        return "RESISTOR{"+x1+","+y1+","+x2+","+y2+"} WEIGHT{"+this.weight+"}\n";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        Stroke orig = g2d.getStroke();
        g2d.setStroke(new BasicStroke(this.weight));
        double distance = Point.distance(x1, y1, x2, y2);
        double slope = 0;
        try {
            slope = (y2-y1)/(x2-x1);
        } catch(ArithmeticException ae) {
        }
        
        if(distance > 20) {
            Point resistorSpotOnLine = new Point((x2-x1)/2, (y2-y1)/2);
            g2d.drawLine(x1, y1, x1+resistorSpotOnLine.x, y1+resistorSpotOnLine.y);
        }
        //g2d.drawLine(x1, y1, x2, y2);
        g2d.setStroke(orig);
        
    }

    public String getName() {
        return "Resistor";
    }

    public void setPoint1(Point point1) {
        // TODO Auto-generated method stub
        
    }

    public void setPoint2(Point point) {
        // TODO Auto-generated method stub
        
    }

    public void setWeight(int weight) {
        // TODO Auto-generated method stub
        
    }

}
