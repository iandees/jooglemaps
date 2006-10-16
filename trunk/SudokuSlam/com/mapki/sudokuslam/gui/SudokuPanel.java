package com.mapki.sudokuslam.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * The overall, 9-tile sudoku board.
 * 
 * @author Ian Dees
 *
 */
public class SudokuPanel extends JPanel {
    private SudokuGame game;
    
    private SudokuCell[][] cells;
    private int cellWidth;
    private int cellHeight;
    
    public SudokuPanel(SudokuGame game, int width, int height) {
        this.game = game;
        this.cells = new SudokuCell[width][height];
        this.cellHeight = height;
        this.cellWidth = width;
        init();
        
        this.setBorder(new LineBorder(Color.GREEN, 5));
    }
    
    private void init() {
        this.setLayout(new GridLayout(this.cellHeight, this.cellWidth));
        
        for(int i = 0; i < cellWidth; i++) {
            for(int j = 0; j < cellHeight; j++) {
                cells[i][j] = new SudokuCell(this.game, this, i+1, j+1);
                cells[i][j].setSize(100,100);
                this.add(cells[i][j]);
            }
        }
        
        selectCell(1,1);
    }
    
    public void start() {
        for (int i = 0; i < cellWidth; i++) {
            for (int j = 0; j < cellHeight; j++) {
                cells[i][j].setVisible(true);
            }
        }
    }

    /**
     * @param row
     * @param col
     */
    public void selectCell(int row, int col) {
        game.selectCell(row, col);
        repaint();
    }
}
