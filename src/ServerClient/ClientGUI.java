package ServerClient;

import java.util.Arrays;

import Sudoku.SudokuController;
import Sudoku.SudokuModel;
import Sudoku.SudokuView;

public class ClientGUI {
	SudokuModel theModel;
	SudokuView theView;
	SudokuController theController;
	String id;
	
	void startGame(String id) {
		theModel = new SudokuModel();
		theView = new SudokuView(theModel.getDim(),theModel.getGridSize(),id,true);
		theController = new SudokuController
        		(theModel, theView);
		theController.start();
		this.id = id;
	}
	
	void startGame(SudokuModel model, String id) {
		theView = new SudokuView(model.getDim(),model.getGridSize(),id,true);
		theController = new SudokuController
        		(model, theView);
		theController.start();
		this.id = id;
	}
	
	public String sendConfig() {
		if(theModel != null) {
			return theModel.getUserValuesAsString();
		}
		return null;
	}
	
	public void setSudokuConfig(String config, String id) {
		String line = config; 
		String[] sudoku = line.split(",");
		theModel = new SudokuModel(Integer.parseInt(sudoku[0]));
		theModel.setUserValuesFromFile(sudoku[1]);
		theModel.setUserValues(sudoku[1]);		
		startGame(theModel, id);
	}

}
