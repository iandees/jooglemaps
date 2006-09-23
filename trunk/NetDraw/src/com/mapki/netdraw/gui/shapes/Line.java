package com.mapki.netdraw.gui.shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.mapki.netdraw.gui.NetDrawable;
import com.mapki.netdraw.gui.NetDrawableInterface;

public class Line extends NetDrawable implements NetDrawableInterface {

    public String serialize() {
        return "LINE{"+nw.x+","+nw.y+","+se.x+","+se.y+"} WEIGHT{"+this.weight+"}";
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        Stroke orig = g2d.getStroke();
        g2d.setStroke(this.stroke);
        g2d.drawLine(nw.x, nw.y, se.x, se.y);
        g2d.setStroke(orig);
        
    }

    public String getName() {
        return "Line";
    }
}
