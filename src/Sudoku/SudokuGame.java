package Sudoku;
/**
 * Team Members: Samuel Omaghomi/ Nathan Ploughman
 * Assessment: Assignment A22
 * Professor: Paulo Sousa
 */

public class SudokuGame {

	public static void main(String[] args) {
		SudokuModel theModel = new SudokuModel();
		SudokuView theView = new SudokuView(theModel.getDim(),theModel.getGridSize());
		SudokuController theController = new SudokuController
        		(theModel, theView);
		theController.start();

	}

}
