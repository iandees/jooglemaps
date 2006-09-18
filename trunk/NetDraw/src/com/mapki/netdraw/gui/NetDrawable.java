package com.mapki.netdraw.gui;

import java.awt.Graphics;

public interface NetDrawable {

    public void paint(Graphics g);

    public String serialize();
}
