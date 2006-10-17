package com.mapki.sudokuslam.game;

/**
 * @author Ian Dees
 *
 */
public class Cell {
    private byte selected;
    private boolean[] options;
    private int row;
    private int col;
    private int panel;
    
    public Cell(int row, int col) {
        this(row, col, 0, new boolean[] {
                false, false, false,
                false, false, false,
                false, false, false
        });
    }

    /**
     * @param row 
     * @param col 
     * @param selected
     * @param numberChoices
     */
    public Cell(int row, int col, int selected, boolean[] numberChoices) {
        if(selected > 9 || selected < 0) {
            return;
        }
        
        this.row = row;
        this.col = col;
        
        this.selected = (byte) selected;
        this.options = numberChoices;
        
        panel = getPanel(row,col);
    }
    
    /**
     * @param row
     * @param col
     * @return
     */
    private int getPanel(int row, int col) {
        int panely = 0;
        int panelx = 0;
        
        if(row <= 9) {
            panely = 2;
        }
        if(row <= 6) {
            panely = 1;
        }
        if(row <= 3) {
            panely = 0;
        }
        
        panelx = (col-1) / 3;
        
        return (panely * 3) + panelx;
    }

    public int getSelected() {
        return this.selected;
    }
    
    public boolean[] getOptions() {
        return this.options;
    }

    /**
     * @param i
     * @return
     */
    public boolean isNumberSelected(int i) {
        if(i >= options.length) {
            return false;
        }
        
        return options[i];
    }

    /**
     * @param i
     */
    public void toggle(int i) {
        if(i > options.length) {
            return;
        }
        
        if(this.options[i] == true) {
            this.options[i] = false;
        } else {
            this.options[i] = true;
        }
    }

    /**
     * 
     */
    public boolean hasNumber() {
        if(selected > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param i
     */
    public void setNumber(int i) {
        if(i > 9 || i < 1) {
            return;
        }
        
        this.selected = (byte) i;
    }

    /**
     * @return
     */
    public int getRow() {
        return this.row;
    }
    
    /**
     * 
     * @return
     */
    public int getCol() {
        return this.col;
    }
}
