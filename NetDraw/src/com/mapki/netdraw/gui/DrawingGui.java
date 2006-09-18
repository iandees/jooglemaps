/**
 * 
 */
package com.mapki.netdraw.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

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
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                endGracefully();
            }
        });
        
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
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
        drawPane = new DrawPane(connector);
        c.add(drawPane, BorderLayout.CENTER);
    }

    protected void endGracefully() {
        System.exit(0);
    }

    public void start() {
        frame.setVisible(true);
    }
}
