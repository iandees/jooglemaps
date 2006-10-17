package com.mapki.sudokuslam.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * @author Ian Dees
 *
 */
public class ShowOptionsPanel extends JPanel {
    public static final Color IDLE_BACKGROUND = new Color(1f, 0.94f, 0.7f);

    public static final Color SELECTED_BACKGROUND = new Color(1f, 0.84f, 0.4f);

    private SudokuGame game;
    
    private SudokuBoard board;
    
    private ShowOptionsPanelSegment[] segments;

    private ShowOptionsPanelSegment selectedSegment;
    
    public ShowOptionsPanel(SudokuGame game, SudokuBoard board) {
        super();
        this.game = game;
        this.board = board;
        initGui();
        this.setVisible(true);
    }

    /**
     * 
     */
    private void initGui() {
        this.setBorder(new LineBorder(Color.BLACK, 1));
        segments = new ShowOptionsPanelSegment[9];
        this.setLayout(new GridLayout(3, 3));
        
        for(int i = 1; i <= 9; i++) {
            segments[i-1] = new ShowOptionsPanelSegment(this, i);
            segments[i-1].setBackground(IDLE_BACKGROUND);
            segments[i-1].setVisible(true);
            this.add(segments[i-1]);
        }
    }

    /**
     * 
     */
    public void start() {
        this.setVisible(true);
    }

    /**
     * @param n
     */
    public void selectSegment(short n) {
        if (segments[n - 1].getBackground() == IDLE_BACKGROUND) {
            if (selectedSegment == null) {
                // Set a segment if there aren't any others selected first
                segments[n - 1].setBackground(SELECTED_BACKGROUND);
            } else {
                // There was another segment selected:
                // unset that one first
                selectedSegment.setBackground(IDLE_BACKGROUND);
                // then set this one
                segments[n - 1].setBackground(SELECTED_BACKGROUND);
            }
            // update the selectedSegment to the one the user just selected
            selectedSegment = segments[n - 1];
            // tell the game to highlight all of the cells with the number as an option
            board.highlightOptions(n);
        } else {
            // If it was already selected, toggle it off
            segments[n - 1].setBackground(IDLE_BACKGROUND);
            selectedSegment = null;
            // tell the game to turn off highlighting
            board.highlightNone();
        }

        super.repaint();
    }
}

class ShowOptionsPanelSegment extends JPanel {
    private ShowOptionsPanel panel;
    
    private short n;
    
    /**
     * @param panel 
     * @param i
     */
    public ShowOptionsPanelSegment(final ShowOptionsPanel panel, int i) {
        super();
        this.panel = panel;
        this.n = (short) i;
        this.setBorder(new LineBorder(Color.ORANGE, 1));
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panel.selectSegment(n);
            }
        });
    }
    
    /**
     * @return
     */
    public short getNumber() {
        return this.n;
    }

    public Dimension getMinimumSize() {
        return new Dimension(25, 25);
    }
    
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Font oFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("" + this.n, getWidth() / 2 - 2, getHeight() / 2 + 4);
        g.setFont(oFont);
    }
}