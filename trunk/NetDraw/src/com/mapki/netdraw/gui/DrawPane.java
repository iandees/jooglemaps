package com.mapki.netdraw.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import com.mapki.netdraw.gui.shapes.Line;
import com.mapki.netdraw.gui.shapes.Oval;
import com.mapki.netdraw.gui.shapes.Rectangle;
import com.mapki.netdraw.gui.shapes.Resistor;
import com.mapki.netdraw.network.DrawConnector;

public class DrawPane extends JPanel implements MouseListener, MouseMotionListener {

    private Vector<NetDrawable> draws;
    private HashMap<String, Class> supportedShapes;
    private DrawConnector connector;
    
    private boolean drawing = false;
    private Point point1;
    private Point point2;
    private NetDrawable currentlyDrawing;
    private Class toolChoice;
    
    public DrawPane(DrawConnector connector) {
        this.draws = new Vector<NetDrawable>();
        this.supportedShapes = new HashMap<String, Class>();
        this.connector = connector;
        
        this.supportedShapes.put("Line", Line.class);
        this.toolChoice = Line.class;
        this.supportedShapes.put("Rectangle", Rectangle.class);
        this.supportedShapes.put("Resistor", Resistor.class);
        this.supportedShapes.put("Oval", Oval.class);
        
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
            try {
                this.currentlyDrawing = (NetDrawable) toolChoice.newInstance();
                currentlyDrawing.setPoint1(point1);
                currentlyDrawing.setPoint2(e.getPoint());
                currentlyDrawing.setWeight(1);
                this.repaint();
            } catch (InstantiationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void doUndo() {
        if(draws.size() > 0) {
            draws.remove(draws.size()-1);
            this.repaint();
            //TODO: Hook to send new drawing space to clients here.
        }
    }

    public Iterator<String> getSupportedTools() {
        return this.supportedShapes.keySet().iterator();
    }
    
    public void setTool(String string) {
        this.toolChoice = supportedShapes.get(string);
    }
}
