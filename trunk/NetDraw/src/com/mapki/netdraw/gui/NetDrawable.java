package com.mapki.netdraw.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.mapki.netdraw.Drawer;

public interface NetDrawable {

    public void paint(Graphics g);

    public String serialize();

    public String getName();

    public void setPoint1(Point point1);

    public void setPoint2(Point point);

    public void setWeight(int weight);

    public Color getColor();

    public void setColor(Color color);
}
