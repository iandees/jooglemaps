package com.mapki.netdraw.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import com.mapki.netdraw.gui.shapes.Line;
import com.mapki.netdraw.network.DrawConnector;

public class DrawPane extends JPanel implements MouseListener, MouseMotionListener {

    private Vector<NetDrawable> draws;
    private DrawConnector connector;
    
    private boolean drawing = false;
    private Point point1;
    private Point point2;
    private NetDrawable currentlyDrawing;
    
    public DrawPane(DrawConnector connector) {
        this.draws = new Vector<NetDrawable>();
        this.connector = connector;
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        Enumeration<NetDrawable> drawEnum = this.draws.elements();
        
        if(drawing) {
            currentlyDrawing.paint(g);
        }
        
        while(drawEnum.hasMoreElements()) {
            NetDrawable d = drawEnum.nextElement();
            d.paint(g);
        }
        
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        this.drawing = true;
        this.point1 = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        this.drawing = false;
        this.point2 = e.getPoint();
        this.draws.add(this.currentlyDrawing);
    }

    public void mouseDragged(MouseEvent e) {
        if(this.drawing) {
            this.currentlyDrawing = new Line(point1, e.getPoint(), 5);
            this.repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
}
