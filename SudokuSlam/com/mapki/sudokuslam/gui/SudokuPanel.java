package com.mapki.sudokuslam.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * The overall, 9-tile sudoku board.
 * 
 * @author Ian Dees
 *
 */
public class SudokuPanel extends JPanel {
    public static final Color HIGHLIGHT_COLOR = new Color(1.0f, .9f, .5f);

    public static final Color NORMAL_COLOR = Color.WHITE;

    private SudokuGame game;
    
    private SudokuCell[][] cells;
    private int cellWidth;
    private int cellHeight;

    private short higlightedOption;
    
    public SudokuPanel(SudokuGame game, int width, int height) {
        this.game = game;
        this.cells = new SudokuCell[width][height];
        this.cellHeight = height;
        this.cellWidth = width;
        init();
    }

    private void init() {
        this.setLayout(new GridLayout(this.cellHeight, this.cellWidth));
        
        for(int i = 0; i < cellWidth; i++) {
            for(int j = 0; j < cellHeight; j++) {
                cells[i][j] = new SudokuCell(this.game, this, i+1, j+1);
                this.add(cells[i][j]);
            }
        }
        
        selectCell(1,1);
    }
    
    /**
     * Sets all of the panel's parts visible.
     */
    public void start() {
        for (int i = 0; i < cellWidth; i++) {
            for (int j = 0; j < cellHeight; j++) {
                cells[i][j].setVisible(true);
            }
        }
        repaint();
    }

    /**
     * @param row The row of the cell to select.
     * @param col The column of the cell to select.
     */
    public void selectCell(int row, int col) {
        game.selectCell(row, col);
        repaint();
    }

    /**
     * Highlights all of the cells that have the specified option selected or have the
     * specified number as a "big number".
     * 
     * @param n
     *            The option to highlight.
     */
    public void highlightOptions(short n) {
        this.higlightedOption = n;

        if (higlightedOption < 1) {
            return;
        } else {
            for (int i = 0; i < cellWidth; i++) {
                for (int j = 0; j < cellHeight; j++) {
                    if (game.cellHasOption((short) (n - 1), i + 1, j + 1) || game.chosenNumber(i + 1, j + 1) == n) {
                        cells[i][j].setBackground(HIGHLIGHT_COLOR);
                        cells[i][j].repaint();
                    } else {
                        cells[i][j].setBackground(NORMAL_COLOR);
                        cells[i][j].repaint();
                    }
                }
            }
        }
    }

    /**
     * Removes all highlighting from the panel.
     */
    public void highlightNone() {
        this.higlightedOption = 0;
        
        for (int i = 0; i < cellWidth; i++) {
            for (int j = 0; j < cellHeight; j++) {
                cells[i][j].setBackground(NORMAL_COLOR);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        int firstThirdHeight = getHeight() / 3;
        int firstThirdWidth = getWidth() / 3;
        
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine(firstThirdWidth, 0, firstThirdWidth, getHeight());
        g2d.drawLine(firstThirdWidth * 2, 0, firstThirdWidth * 2, getHeight());
        g2d.drawLine(0, firstThirdHeight, getWidth(), firstThirdHeight);
        g2d.drawLine(0, firstThirdHeight * 2, getWidth(), firstThirdHeight * 2);
    }

    /**
     * Updates the highlighted cells with any changes.
     */
    public void updateHighlights() {
        highlightOptions(this.higlightedOption);
    }
}
