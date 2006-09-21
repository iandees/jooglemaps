/**
 * 
 */
package com.mapki.netdraw.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import com.mapki.netdraw.network.DrawConnector;

/**
 * @author Ian Dees
 *
 */
public class DrawingGui {
    private static final String APPLICATION_TITLE = "Ian Draw";

    private DrawConnector connector;

    private JFrame frame;
    private JPanel statusBarPanel;
    private JLabel statusBar;
    private DrawPane drawPane;

    private JMenuItem selectedToolMenuItem;
    
    public DrawingGui(DrawConnector connector) {
        this.connector = connector;
        
        init();
    }

    private void init() {
        this.frame = new JFrame(APPLICATION_TITLE);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                endGracefully();
            }
        });
        
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        frame.setSize(1000, 800);
        
        // Add the status bar
        this.statusBarPanel = new JPanel();
        this.statusBar = new JLabel("Hello World");
        this.statusBarPanel.setLayout(new BorderLayout());
        this.statusBarPanel.add(statusBar, BorderLayout.CENTER);
        this.statusBarPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        c.add(statusBarPanel, BorderLayout.SOUTH);

        // Add the drawing area
        drawPane = new DrawPane(connector);
        c.add(drawPane, BorderLayout.CENTER);
        
        // Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Host...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doHost();
            }
        });
        fileMenu.add(menuItem);
        
        menuItem = new JMenuItem("Join...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String address = JOptionPane.showInputDialog("Enter the address for the drawer you're connecting to:");
                doJoin(address);
            }
        });
        fileMenu.add(menuItem);
        
        fileMenu.addSeparator();
        
        menuItem = new JMenuItem("Quit");
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
            JRadioButtonMenuItem item;
            item = new JRadioButtonMenuItem(tool, false);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chooseTool(e.getSource());
                }
            });
            toolsMenu.add(item);
        }
        toolsMenu.addSeparator();
        menuItem = new JMenuItem("Select Color...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(frame, "Choose your pen color", Color.black);
                connector.setColor(connector.getSelf(), c);
            }
        });
        toolsMenu.add(menuItem);
        menuItem = new JMenuItem("Select Stroke...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sStroke = JOptionPane.showInputDialog(frame, "Enter a number for your line pen width:");
                connector.setWeight(connector.getSelf(), Integer.parseInt(sStroke));
            }
        });
        toolsMenu.add(menuItem);
        
        toolsMenu.addSeparator();
        menuItem = new JMenuItem("Dump board");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dumpBoard();   
            }
        });
        toolsMenu.add(menuItem);
        menuBar.add(toolsMenu);
        
        frame.setJMenuBar(menuBar);
        
        
    }

    protected void dumpBoard() {
        this.drawPane.dumpBoard();
    }

    protected void doHost() {
        if(connector.hasDrawers() == false) {
            JOptionPane.showMessageDialog(this.frame, "You need to set up your local user before you can host.");
            return;
        }
        
        connector.startHosting();
    }

    protected void doJoin(String address) {
        connector.connectTo(address);
    }

    protected void chooseTool(Object source) {
        if(source instanceof JRadioButtonMenuItem) {
            JRadioButtonMenuItem sourceMenuItem = (JRadioButtonMenuItem) source;
            drawPane.setTool(sourceMenuItem.getText());
            
            if (this.selectedToolMenuItem != null) {
                this.selectedToolMenuItem.setSelected(false);
            }
            this.selectedToolMenuItem = sourceMenuItem;
            this.selectedToolMenuItem.setSelected(true);
        }
    }

    protected void doUndo() {
        drawPane.doUndo();
    }

    protected boolean endGracefully() {
        if(connector.isHosting() || connector.isConnected()) {
            int result = JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to close your drawing session?");
            
            if(result != JOptionPane.OK_OPTION) {
                return false;
            }
        }
        
        if(connector.isHosting()) {
            connector.stopHosting();
        }
        
        if(connector.isConnected()) {
            connector.closeConnections();
        }
        
        System.exit(0);
        return true;
    }
    
    public void setStatusText(String text) {
        this.statusBar.setText(text);
    }

    public void start() {
        frame.setVisible(true);
    }
}
