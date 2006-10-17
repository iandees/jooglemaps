package com.mapki.sudokuslam.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * @author Ian Dees
 *
 */
public class SudokuBoard extends JFrame {

    private SudokuPanel sPanel;
    private MovePanel mPanel;
    private SudokuGame game;

    /**
     * @param game
     */
    public SudokuBoard(SudokuGame game) {
        super("SudokuSlam");
        this.setSize(700,700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.game = game;
        initGui();
    }

    public Dimension getMinimumSize() {
        return new Dimension(700, 512);
    }
    
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }
    
    /**
     * 
     */
    private void initGui() {
        this.sPanel = new SudokuPanel(this.game, game.getWidth(), game.getHeight());
        
        Container c = this.getContentPane();
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int dx = 0;
                int dy = 0;
                
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    dy = -1;
                    game.moveSelectedCell(dx, dy);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    dy = 1;
                    game.moveSelectedCell(dx, dy);
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    dx = -1;
                    game.moveSelectedCell(dx, dy);
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    dx = 1;
                    game.moveSelectedCell(dx, dy);
                }
                
                if(e.getKeyCode() == KeyEvent.VK_1) {
                    toggleSelectedCellOption(1);
                } else if(e.getKeyCode() == KeyEvent.VK_2) {
                    toggleSelectedCellOption(2);
                } else if(e.getKeyCode() == KeyEvent.VK_3) {
                    toggleSelectedCellOption(3);
                } else if(e.getKeyCode() == KeyEvent.VK_4) {
                    toggleSelectedCellOption(4);
                } else if(e.getKeyCode() == KeyEvent.VK_5) {
                    toggleSelectedCellOption(5);
                } else if(e.getKeyCode() == KeyEvent.VK_6) {
                    toggleSelectedCellOption(6);
                } else if(e.getKeyCode() == KeyEvent.VK_7) {
                    toggleSelectedCellOption(7);
                } else if(e.getKeyCode() == KeyEvent.VK_8) {
                    toggleSelectedCellOption(8);
                } else if(e.getKeyCode() == KeyEvent.VK_9) {
                    toggleSelectedCellOption(9);
                }
                repaint();
            }
        });
        
        c.add(sPanel);
        
        this.mPanel = new MovePanel(this.game, this);
        
        c.add(mPanel);
    }

    /**
     * Toggles the selected cell's option.
     * 
     * @param i The option to toggle.
     */
    protected void toggleSelectedCellOption(int i) {
        game.toggleSelectedCellOption(i);
        repaint();
    }

    /**
     * Makes the board's parts visible.
     */
    public void start() {
        this.sPanel.start();
        this.mPanel.start();
        this.setVisible(true);
    }

    /**
     * @param n Highlights all cells that have <code>n</code> selected in some way (as an option or as the "big number").
     */
    public void highlightOptions(short n) {
        sPanel.highlightOptions(n);
    }

    /**
     * Removes all highlighted cells.
     */
    public void highlightNone() {
        sPanel.highlightNone();
    }

}
