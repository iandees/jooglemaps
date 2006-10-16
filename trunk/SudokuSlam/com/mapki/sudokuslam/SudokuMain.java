package com.mapki.sudokuslam;

import com.mapki.sudokuslam.game.SudokuGame;
import com.mapki.sudokuslam.gui.SudokuBoard;

/**
 * @author Ian Dees
 *
 */
public class SudokuMain {
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        SudokuBoard board = new SudokuBoard(game);
        board.start();
    }
    
}
