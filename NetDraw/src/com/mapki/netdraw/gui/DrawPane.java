package com.mapki.netdraw.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import com.mapki.netdraw.network.DrawConnector;

public class DrawPane extends JPanel implements MouseListener, MouseMotionListener {

    public Vector<NetDrawable> draws;
    private DrawConnector connector;
    
    public DrawPane(DrawConnector connector) {
        this.draws = new Vector<NetDrawable>();
        this.connector = connector;
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        Enumeration<NetDrawable> drawEnum = this.draws.elements();
        
        while(drawEnum.hasMoreElements()) {
            NetDrawable d = drawEnum.nextElement();
            d.paint(g);
        }
        
        super.paint(g);
    }

    public void mouseClicked(MouseEvent e) {
        System.err.println("Click: " + e.getX() + " " + e.getY());
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
