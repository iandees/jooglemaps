package com.mapki.sudokuslam.game;

/**
 * @author Ian Dees
 *
 */
public class Board {
    private Cell[][] boardInfo;
    private int width;
    private int height;
    private Cell selectedCell;
    
    public Board() {
        this(9,9);
    }

    /**
     * @param width
     * @param height
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.boardInfo = new Cell[width][height];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.boardInfo[i][j] = new Cell(i+1, j+1);
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return boardInfo[x-1][y-1];
    }

    /**
     * @return
     */
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }

    /**
     * @param i
     * @param row
     * @param col
     */
    public boolean isOptionSelected(int i, int row, int col) {
        Cell c = this.getCell(col, row);
        return c.isNumberSelected(i);
    }

    /**
     * @param i
     * @param row
     * @param col
     */
    public void toggleOption(int i, int row, int col) {
        getCell(col, row).toggle(i);
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public boolean cellIsChosen(int row, int col) {
        return getCell(col, row).hasNumber();
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public int cellNumber(int row, int col) {
        return getCell(col, row).getSelected();
    }

    /**
     * @param i
     * @param row
     * @param col
     */
    public void setCellNumber(int i, int row, int col) {
        getCell(col, row).setNumber(i);
    }

    /**
     * @param row
     * @param col
     */
    public void setSelectedCell(int row, int col) {
        this.selectedCell = getCell(col, row);
    }

    /**
     * @return
     */
    public boolean isCellSelected(int row, int col) {
        if(getCell(col, row) == selectedCell) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return
     */
    public Cell getSelectedCell() {
        return selectedCell;
    }
}
