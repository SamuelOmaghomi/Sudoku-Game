package Sudoku;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Team Members: Samuel Omaghomi/ Nathan Ploughman
 * Assessment: Assignment A22
 * Professor: Paulo Sousa
 */

public class SudokuModel {

	private int gridSize;
	private int dim;
	private String userValues[][];
	private String solution[][];
	private String userValuesFromFile;
	
	public SudokuModel(){
		dim = 2;
		gridSize = dim*dim;
		userValues = new String[gridSize][gridSize];
		initUserValues();
	}
	
	public SudokuModel(int dim){
		this.dim = dim;
		gridSize = dim*dim;
		userValues = new String[gridSize][gridSize];
		initUserValues();
	}
	
	  /**
     * Initializes user values array
     */
    private void initUserValues() {
    	for(int row = 0; row < gridSize; row++) {
    		for(int col = 0;  col < gridSize; col++) {
    			userValues[row][col] = ".";
    		}
    	}
    }
    
    public void setUserValues(String input) {
    	userValuesFromFile = input;
    }
    
    public String getUserValuesString() {
    	return userValuesFromFile;
    }
    
	public void setGridSize(int size) {
		gridSize = size;
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public int getDim() {
		return dim;
	}
	
	public void setDim(int dim) {
		this.dim = dim;
		gridSize = dim*dim;
	}
	
	public void setValue(int row, int col, String value) {
		userValues[row][col] = value;
	}
	
	public String getValue(int row, int col) {
		return(userValues[row][col]);
	}
	
	public String[][] getUserValues() {
		return userValues;
	}
	
	public String getUserValuesAsString() {
		String values = "";
		int i = 0;
		for(int row = 0; row < gridSize; row++) {
    		for(int col = 0;  col < gridSize; col++) {
//    			if(values == null) {
//    				values = userValues[row][col];
//    				break;
//    			}
    			values = values.concat(userValues[row][col]);
    			i++;
    		}
    	}
		System.out.println(i);
		return values;
	}

	
	/**
	 * Checks if character is in the row of the grid
	 * @param row grid row
	 * @param charcter character to be checked
	 * @return TrueOrFalse
	 */
	private boolean isInRow(int row, String charcter) {
		for (int i = 0; i < gridSize; i++)
			if(userValues[row][i] != null) {
				if (userValues[row][i].equals(charcter)) {
					return true;
				}					
			}
		return false;
	}
	
	/**
	 * Checks if character is in the column of the grid
	 * @param col grid column
	 * @param charcter character to be checked
	 * @return TrueOrFalse
	 */
	private boolean isInCol(int col, String charcter) {
		for (int i = 0; i < gridSize; i++)
			if(userValues[i][col] != null) {
				if (userValues[i][col].equals(charcter))
					return true;
			}
		return false;
	}
	
	private boolean isInBox(int row, int col, String character) {
		int rowBox = whatBox(row);
		int colBox = whatBox(col);
		System.out.println(dim);
		System.out.printf("%d %d %d %d \n",row,col,rowBox,colBox);
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(userValues[i+rowBox][j+colBox] != null) {
					if (userValues[i+rowBox][j+colBox].equals(character))
						return true;
				}
			}
		}
		return false;	
	}
	
	private int whatBox(int num) {
		int box=0;

		if(num < dim) {
			box = 0;
		} else if(num/dim >= 3) {
			box = dim*3;
		} else if(num/dim >= 2) {
			box = dim*2;
		} else if(num/dim >= 1) {
			box = dim;
		}
		return box;
	}

	/**
	 * Checks if character is in not in row and not in column
	 * @param row grid row
	 * @param col grid column
	 * @param character character to be checked
	 * @return TrueOrFalse
	 */
	public boolean isValid(int row, int col, String character) {
		return !isInRow(row, character) && !isInCol(col, character) && !isInBox(row,col,character);
	}
	
	/**
     * Prints Values of Users
     */
    public void printUserValues() {
    	for(int row = 0; row < gridSize; row++) {
    		for(int col = 0;  col < gridSize; col++) {
    			System.out.print(userValues[row][col]);
    		}
    		System.out.print("\n");
    	}
    }
    
    public void setUserValuesFromFile(String input) {
    	char[] ch = input.toCharArray();
    	int i = 0;
    	for(int row = 0; row < gridSize; row++) {
    		for(int col = 0;  col < gridSize; col++) {
    			userValues[row][col] = Character.toString(ch[i]);
    			i++;
    		}
    	}
    	solution = userValues;
    }
    
    public void hideUserValues(String mode, int size, int dim) {
    	Random randNum = new Random();
    	//int[] positionsToHide = new int[size];
    	
    	//   randNum.setSeed(100);
    	dim = dim*dim;
    	 ArrayList<Integer> rowPositionsToHide = null;
    	 ArrayList<Integer> colPositionsToHide = null;
    	 
    	 int amountToHide = 0;
    	 System.out.println(size);

    	switch(mode) {
    	
    	case "Easy":
    		amountToHide = (int) Math.round(0.25 * size);	
    		break;
    	case "Medium":
    		amountToHide = (int) Math.round(0.50 * size);	
    		break;
    	case "Hard":
    		amountToHide = (int) Math.round(0.75 * size);
    		break;
    	}
    	
    	rowPositionsToHide = new ArrayList<Integer>(amountToHide);
		colPositionsToHide = new ArrayList<Integer>(amountToHide);
		System.out.print("Numbers to hide: ");
		
		while(rowPositionsToHide.size() < amountToHide) {
			System.out.print(randNum.nextInt(amountToHide));
			rowPositionsToHide.add(randNum.nextInt(dim));
			
		}
		
		while(colPositionsToHide.size() < amountToHide) {
			System.out.print(randNum.nextInt(amountToHide));
			colPositionsToHide.add(randNum.nextInt(dim));
		}
		System.out.println("");
    	
    	Iterator<Integer> rowIt = rowPositionsToHide.iterator();
    	Iterator<Integer> colIt = colPositionsToHide.iterator();
    	
    	System.out.println(rowPositionsToHide);
    	System.out.println(colPositionsToHide);
    	
    	
    	System.out.println(amountToHide);
    	while(rowIt.hasNext()) {
    		int rowValue = rowIt.next();
    		int colValue = colIt.next();
    		
    		userValues[rowValue][colValue] = ".";
    	 }
    }
}


