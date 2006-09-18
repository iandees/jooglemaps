package com.mapki.netdraw.gui;

import java.awt.Graphics;
import java.awt.Point;

public interface NetDrawable {

    public void paint(Graphics g);

    public String serialize();

    public String getName();

    public void setPoint1(Point point1);

    public void setPoint2(Point point);

    public void setWeight(int weight);
}
