package com.mapki.netdraw.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;

public interface NetDrawableInterface {

    public void paint(Graphics g);

    public String serialize();

    public String getName();

    public void setPoint1(Point point1);

    public void setPoint2(Point point);

    public Color getColor();

    public void setColor(Color color);

    public Stroke getStroke();
    
    public void setStroke(Stroke stroke);
    
}
