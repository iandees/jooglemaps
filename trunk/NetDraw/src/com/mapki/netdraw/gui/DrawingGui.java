/**
 * 
 */
package com.mapki.netdraw.gui;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.mapki.netdraw.network.DrawConnector;

/**
 * @author Ian Dees
 *
 */
public class DrawingGui {
    private static final String APPLICATION_TITLE = "Ian Draw";

    private DrawConnector connector;

    private JFrame frame;
    private DrawPane drawPane;
    
    public DrawingGui(DrawConnector connector) {
        this.connector = connector;
        
        init();
    }

    private void init() {
        this.frame = new JFrame(APPLICATION_TITLE);
        Container c = frame.getContentPane();
        frame.setSize(1000, 800);

        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                endGracefully();
            }
        
        });
        fileMenu.add(menuItem);
        menuBar.add(fileMenu);
        
        frame.setJMenuBar(menuBar);
        
        // Add the drawing area
        drawPane = new DrawPane();
        c.add(drawPane);
    }

    protected void endGracefully() {
        System.exit(0);
    }

    public void start() {
        frame.setVisible(true);
    }
}
