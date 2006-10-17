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
     * @return The width of the board in cells.
     */
    public int getWidth() {
        return board.getWidth();
    }

    /**
     * @return The height of the board in cells.
     */
    public int getHeight() {
        return board.getHeight();
    }

    /**
     * 
     * @param i
     *            The option (1-indexed) to check for.
     * @param row
     *            The row of the cell to check.
     * @param col
     *            The column of the cell to check.
     * @return True if the option has been toggled on previously by the user. This does
     *         <em>not</em> check for the number that was selected for the whole cell.
     */
    public boolean isOptionSelected(int i, int row, int col) {
        return this.board.isOptionSelected(i, row, col);
    }

    /**
     * Causes the corresponding cell to remember that an option was toggled. If the option
     * had not been previously toggled on, it gets selected as an option, otherwise it
     * gets deselected.
     * 
     * @param i
     *            The option (1-indexed) to toggle.
     * @param row
     *            The row of the cell in which we should toggle the option.
     * @param col
     *            The column of the cell in which we should toggle the option.
     */
    public void clickedCellOption(int i, int row, int col) {
        this.board.toggleOption(i, row, col);
    }

    /**
     * @param row
     *            The row of the cell to check.
     * @param col
     *            The column of the cell to check.
     * @return True if the cell specified has a "big number" chosen (i.e. not just an
     *         option).
     */
    public boolean cellIsChosen(int row, int col) {
        return this.board.cellIsChosen(row, col);
    }

    /**
     * @param row
     *            The row of the cell to check.
     * @param col
     *            The column of the cell to check.
     * @return The "big number" that has been chosen for this cell. Zero if no number has
     *         been chosen.
     */
    public int chosenNumber(int row, int col) {
        return this.board.cellNumber(row, col);
    }

    /**
     * @param i
     *            The number to set the cell to.
     * @param row
     *            The row of the cell to set.
     * @param col
     *            The column of the cell to set.
     */
    public void setCellNumber(int i, int row, int col) {
        this.board.setCellNumber(i, row, col);
    }

    /**
     * Sets the board's current "selected cell" to the specified row and column.
     * 
     * @param row
     *            The row of the cell to select.
     * @param col
     *            The column of the cell to select.
     */
    public void selectCell(int row, int col) {
        this.board.setSelectedCell(row, col);
    }

    /**
     * @param row
     *            The cell in question's row.
     * @param col
     *            The cell in question's column.
     * @return True if the cell is selected. False otherwise.
     */
    public boolean cellIsSelected(int row, int col) {
        return this.board.isCellSelected(row, col);
    }

    /**
     * Moves the selection box around on the board. Will not wrap around to the other side
     * or allow the box to go outside of the board's bounds.
     * 
     * @param dx
     *            The amount along the "x" axis to move the selection box.
     * @param dy
     *            The amount along the "y" axis to move the selection box.
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
     * Toggle's the selected cell's specified option.
     * 
     * @param i
     *            The option to toggle.
     */
    public void toggleSelectedCellOption(int i) {
        Cell c = this.board.getSelectedCell();
        
        c.toggle(i-1);
    }

    /**
     * @param n
     *            The option to check.
     * @param row
     *            The row of the cell to check.
     * @param col
     *            The column of the cell to check.
     * @return True if the specified cell has the specified option selected. False
     *         otherwise.
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
