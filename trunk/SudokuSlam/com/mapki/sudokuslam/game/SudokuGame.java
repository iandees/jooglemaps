package com.mapki.sudokuslam.game;

/**
 * @author Ian Dees
 *
 */
public class SudokuGame {
    private Board board;
    
    public SudokuGame() {
        this.board = new Board();
    }

    /**
     * @return
     */
    public int getWidth() {
        return board.getWidth();
    }

    /**
     * @return
     */
    public int getHeight() {
        return board.getHeight();
    }

    /**
     * @param i
     * @param row
     * @param col
     * @return
     */
    public boolean isOptionSelected(int i, int row, int col) {
        return this.board.isOptionSelected(i, row, col);
    }

    /**
     * @param row
     * @param col
     * @param i
     */
    public void clickedCellOption(int i, int row, int col) {
        this.board.toggleOption(i, row, col);
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public boolean cellIsChosen(int row, int col) {
        return this.board.cellIsChosen(row, col);
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public int chosenNumber(int row, int col) {
        return this.board.cellNumber(row, col);
    }

    /**
     * @param row
     * @param col
     * @param i
     */
    public void setCellNumber(int i, int row, int col) {
        this.board.setCellNumber(i, row, col);
    }

    /**
     * @param row
     * @param col
     */
    public void selectCell(int row, int col) {
        this.board.setSelectedCell(row, col);
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public boolean cellIsSelected(int row, int col) {
        return this.board.isCellSelected(row, col);
    }

    /**
     * @param dx
     * @param dy
     */
    public void moveSelectedCell(int dx, int dy) {
        Cell c = this.board.getSelectedCell();
        
        int newRow = c.getRow() + dx;
        int newCol = c.getCol() + dy;
        
        if(newRow < 1 || newRow > getHeight()) {
            newRow = c.getRow();
        }
        
        if(newCol < 1 || newCol > getWidth()) {
            newCol = c.getCol();
        }
        
        this.board.setSelectedCell(newCol, newRow);
    }

    /**
     * @param i
     */
    public void toggleSelectedCellOption(int i) {
        Cell c = this.board.getSelectedCell();
        
        c.toggle(i-1);
    }

    /**
     * @param n
     * @param row
     * @param col
     * @return
     */
    public boolean cellHasOption(short n, int row, int col) {
        Cell c = this.board.getCell(col, row);
        if(c.isNumberSelected(n)) {
            return true;
        } else {
            return false;
        }
    }

}
