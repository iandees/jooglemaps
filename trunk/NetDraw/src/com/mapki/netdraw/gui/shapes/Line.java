package com.mapki.netdraw.gui.shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.mapki.netdraw.gui.NetDrawable;

public class Line implements NetDrawable {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    public Line(Point point1, Point point2) {
        this.x1 = point1.x;
        this.y1 = point1.y;
        this.x2 = point2.x;
        this.y2 = point2.y;
    }

    public String serialize() {
        return "LINE{"+x1+","+y1+","+x2+","+y2+"}\n";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawLine(x1, y1, x2, y2);
        
    }

}
