package com.mapki.sudokuslam.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.mapki.sudokuslam.game.SudokuGame;

/**
 * @author Ian Dees
 *
 */
public class SudokuCell extends JPanel {
    private static final Border SELECTED_BORDER = new LineBorder(Color.RED, 2);
    private static final Border PLAIN_BORDER = new LineBorder(Color.BLACK, 1);
    private Color color;
    private SudokuGame game;
    private SudokuPanel parentPanel;
    private int row;
    private int col;
    
    public SudokuCell(final SudokuGame game, final SudokuPanel parent, final int row, final int col) {
        this.row = row;
        this.col = col;
        this.game = game;
        this.parentPanel = parent;
        
        this.color = Color.WHITE;
        this.setBorder(new LineBorder(Color.BLACK, 1));
        this.setBackground(this.color);
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = (e.getX() / (getWidth() / 3));
                int y = (e.getY() / (getHeight() / 3));

                int i = (y * 3) + x;

                if (e.getClickCount() == 2) {
                    game.setCellNumber(i + 1, row, col);
                } else if (e.getClickCount() == 1) {
                    game.clickedCellOption(i, row, col);
                    parent.selectCell(row, col);
                    parent.updateHighlights();
                }
                
                repaint();
            }
        });
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(51, 51);
    }
    
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        if (game.cellIsSelected(row, col)) {
            this.setBorder(SELECTED_BORDER);
        } else {
            this.setBorder(PLAIN_BORDER);
        }
        
        if(game.cellIsChosen(row, col)) {
            int selected = game.chosenNumber(row, col);
            int x = (getWidth() / 2) - 6;
            int y = (getHeight() / 2) + 9;
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("" + selected, x, y);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            

            int height = this.getHeight();
            int firstThirdHeight = height / 3;
            int width = this.getWidth();
            int firstThirdWidth = width / 3;
            
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(1, firstThirdHeight, width-2, firstThirdHeight);
            g2d.drawLine(1, 2*firstThirdHeight, width-2, 2*firstThirdHeight);
            g2d.drawLine(firstThirdWidth, 1, firstThirdWidth, height-2);
            g2d.drawLine(2*firstThirdWidth, 1, 2*firstThirdWidth, height-2);
            
            int firstSixthHeight = height / 6;
            int firstSixthWidth = width / 6;

            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            for(int i = 1; i <= 9; i++) {
                if (game.isOptionSelected(i - 1, row, col)) {
                    int h = ((i - 1) % 3) + 1;
                    int w = ((i - 1) / 3) + 1;
                    g2d.drawString("" + i, (((2 * h) - 1) * firstSixthWidth) - 2, (((2 * w) - 1) * firstSixthHeight) + 6);
                }
            } 
        }
    }
    
    public void setColor(Color c) {
        this.color = c;
        this.setBackground(this.color);
        repaint();
    }
}
