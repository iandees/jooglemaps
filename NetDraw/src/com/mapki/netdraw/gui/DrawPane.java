package com.mapki.netdraw.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
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
        
        Graphics2D g2d = (Graphics2D) g;
        
        Enumeration<NetDrawable> drawEnum = this.draws.elements();
        
        if(drawing) {
            Color pColor = g2d.getColor();
            g2d.setColor(currentlyDrawing.getColor());
            Stroke pStroke = g2d.getStroke();
            g2d.setStroke(currentlyDrawing.getStroke());
            currentlyDrawing.paint(g2d);
            g2d.setColor(pColor);
            g2d.setStroke(pStroke);
        }
        
        while(drawEnum.hasMoreElements()) {
            NetDrawable d = drawEnum.nextElement();
            Color pColor = g2d.getColor();
            Stroke pStroke = g2d.getStroke();
            g2d.setColor(d.getColor());
            g2d.setStroke(d.getStroke());
            d.paint(g2d);
            g2d.setColor(pColor);
            g2d.setStroke(pStroke);
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
                currentlyDrawing.setColor(connector.getSelf().getDrawColor());
                currentlyDrawing.setPoint1(point1);
                currentlyDrawing.setPoint2(e.getPoint());
                currentlyDrawing.setWeight(connector.getSelf().getDrawWeight());
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
        this.connector.mouseAt(e.getX(), e.getY());
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
    
    public void dumpBoard() {
        Enumeration<NetDrawable> drawEnum = this.draws.elements();
        
        while(drawEnum.hasMoreElements()) {
            NetDrawable draw = drawEnum.nextElement();
            System.err.println(draw.serialize());
        }
    }
}
