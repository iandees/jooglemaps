package com.mapki.sudokuslam.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * @author Ian Dees
 *
 */
public class MovePanel extends JPanel {

    private SudokuGame game;
    private ShowOptionsPanel soPanel;
    private SudokuBoard board;
    
    /**
     * @param game
     */
    public MovePanel(SudokuGame game, SudokuBoard board) {
        this.game = game;
        this.board = board;
        initGui();
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(200, 100);
    }
    
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    /**
     * 
     */
    private void initGui() {
        soPanel = new ShowOptionsPanel(game, board);
        this.add(soPanel);
    }
    
    public void start() {
        this.soPanel.start();
        this.setVisible(true);
    }

}
