package com.mapki.sudokuslam.gui;

import java.awt.Container;
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
    private SudokuGame game;

    /**
     * @param game
     */
    public SudokuBoard(SudokuGame game) {
        super("SudokuSlam");
        this.setSize(500,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.game = game;
        initGui();
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
                    game.toggleSelectedCellOption(1);
                } else if(e.getKeyCode() == KeyEvent.VK_2) {
                    game.toggleSelectedCellOption(2);
                } else if(e.getKeyCode() == KeyEvent.VK_3) {
                    game.toggleSelectedCellOption(3);
                } else if(e.getKeyCode() == KeyEvent.VK_4) {
                    game.toggleSelectedCellOption(4);
                } else if(e.getKeyCode() == KeyEvent.VK_5) {
                    game.toggleSelectedCellOption(5);
                } else if(e.getKeyCode() == KeyEvent.VK_6) {
                    game.toggleSelectedCellOption(6);
                } else if(e.getKeyCode() == KeyEvent.VK_7) {
                    game.toggleSelectedCellOption(7);
                } else if(e.getKeyCode() == KeyEvent.VK_8) {
                    game.toggleSelectedCellOption(8);
                } else if(e.getKeyCode() == KeyEvent.VK_9) {
                    game.toggleSelectedCellOption(9);
                }
                repaint();
            }
        });
        
        c.add(sPanel);
    }

    /**
     * 
     */
    public void start() {
        this.setVisible(true);
    }

}
