/**
 * 
 */
package com.mapki.netdraw.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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

        // Add the drawing area
        drawPane = new DrawPane(connector);
        c.add(drawPane, BorderLayout.CENTER);
        
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
        
        JMenu editMenu = new JMenu("Edit");
        menuItem = new JMenuItem("Undo");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doUndo();
            }
        });
        editMenu.add(menuItem);
        menuBar.add(editMenu);
        
        JMenu toolsMenu = new JMenu("Tools");
        Iterator<String> tools = drawPane.getSupportedTools();
        while(tools.hasNext()) {
            String tool = tools.next();
            JMenuItem item;
            item = new JMenuItem(tool);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chooseTool(e.getSource());
                }
            });
            toolsMenu.add(item);
            menuBar.add(toolsMenu);
        }
        
        frame.setJMenuBar(menuBar);
        
        
    }

    protected void chooseTool(Object source) {
        if(source instanceof JMenuItem) {
            JMenuItem sourceMenuItem = (JMenuItem) source;
            drawPane.setTool(sourceMenuItem.getText());
        }
    }

    protected void doUndo() {
        drawPane.doUndo();
    }

    protected void endGracefully() {
        System.exit(0);
    }

    public void start() {
        frame.setVisible(true);
    }
}
