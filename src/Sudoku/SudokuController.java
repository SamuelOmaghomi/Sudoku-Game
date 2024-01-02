package Sudoku;
/**
 * Team Members: Samuel Omaghomi/ Nathan Ploughman
 * Assessment: Assignment A22
 * Professor: Paulo Sousa
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SudokuController {
	private SudokuModel sudokuModel;
	private SudokuView sudokuView;
	
	public SudokuController(SudokuModel sudokuModel, SudokuView sudokuView){
		this.sudokuModel = sudokuModel;
		this.sudokuView = sudokuView;
	}
	
	public void start() {
		sudokuView.setDim(sudokuModel.getDim());
		sudokuView.setGridSize(sudokuModel.getGridSize());
		
		sudokuView.addButttonGridListener(new SudokuListener());
		sudokuView.addSaveListener(new SudokuListener());
		sudokuView.addApplyListener(new SudokuListener());
		sudokuView.addResetListener(new SudokuListener());
		sudokuView.addImageListener(new SudokuListener());
		sudokuView.addLoadListener(new SudokuListener());
		sudokuView.addLeftClickListener(new SudokuListener());
		sudokuView.addStartListener(new SudokuListener());
		sudokuView.addShowListener(new SudokuListener());
		sudokuView.addHideListener(new SudokuListener());
		sudokuView.addDeisgnListener(new SudokuListener());
		sudokuView.addPlayListener(new SudokuListener());
		sudokuView.disableButtons();
		sudokuView.setVisible(true);
		restartTimer();
	}
	
	private void restartGame(int dim) {
		sudokuView.discard();
		sudokuModel = new SudokuModel(dim);
		sudokuView = new SudokuView(sudokuModel.getDim(),sudokuModel.getGridSize());
		System.out.println(seconds);
		start();
	}
	
	private void restartGame(String config, int dim) {
		sudokuView.discard();
		sudokuModel = new SudokuModel(dim);
		sudokuView = new SudokuView(sudokuModel.getDim(),sudokuModel.getGridSize());
		System.out.println(seconds);
		start();
	}
	
	class SudokuListener implements ActionListener, MouseListener {
		String action = null;
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton) {
				JButton but = (JButton) e.getSource();
				if(but.getName() == "ButtonGrid" || but.getName() == "BottomButtonGrid") {
					for(int row = 0; row < sudokuModel.getGridSize(); row++) {
						if(e.getSource() == sudokuView.getBottomGridButton(row)) {
							action = e.getActionCommand();
							System.out.println(action + " is Pressed");
							break;
						}
						for(int col = 0; col < sudokuModel.getGridSize(); col++) {
							if(e.getSource() == sudokuView.getGridButton(row, col)) {
								System.out.printf("but[%d][%d} pressed\n",row,col);
								String text = sudokuView.getBottomGridButton(row).getText();	
								if(text != null) {
									if(sudokuModel.isValid(row, col, action) == true) {
										sudokuView.getGridButton(row, col).setText(action);
										sudokuModel.setValue(row, col, sudokuView.getGridButton(row, col).getText());
										if(action == null) {
											sudokuModel.setValue(row, col, ".");
										}
										sudokuModel.printUserValues();
									}
								}	
							}
						}
					}
				}
				
				if(but.getText() == "Save") {
					//System.out.println(but);
					Formatter output = null;
					JFileChooser fileChooser = new JFileChooser();
			    	File file = null;
			    	
			    	if(fileChooser.showSaveDialog(sudokuView) == JFileChooser.APPROVE_OPTION) {
			    		file = fileChooser.getSelectedFile();
            		}
	            	if(file != null) {
		            	try {
		            		output = new Formatter(file);
		            		output.format("%d,",sudokuModel.getDim());
		            		for(int row=0; row < sudokuModel.getGridSize(); row++) {
		            			for(int col=0; col < sudokuModel.getGridSize(); col++) {
		            				output.format("%s", sudokuModel.getValue(row, col));
		            			}
		            		}
		            		JOptionPane.showMessageDialog(sudokuView, "File Saved Successfully");
		            	} catch(Exception ex) {
		            		System.err.println("Error while writing to file");
		            	} finally {
		            		output.close();
		            	}
	            	}
				}
				
				if(but.getText() == "Apply") {
					 //Get the dimension value
	                String newDim = sudokuView.getDimSelectedItem();
	                sudokuModel.setDim(Integer.parseInt(newDim));
	                System.out.println( newDim);
	                restartGame(Integer.parseInt(newDim));
				}
				
				if(but.getText() == "Reset") {
					restartGame(sudokuModel.getDim());
				}
				
				if(but.getText() == "Image") {
					JOptionPane.showMessageDialog(sudokuView, "This is the game of sudoku. More information will be added.");
				}
				
				if(but.getText() == "Load") {
					File file = null;
					JFileChooser fileChooser = new JFileChooser();
					if(fileChooser.showOpenDialog(sudokuView) == JFileChooser.APPROVE_OPTION) {
            			file = fileChooser.getSelectedFile();
            		}
					try (BufferedReader input = new BufferedReader(new FileReader(file))) {	//opens the file
		   				String line = input.readLine();
		   				System.out.println(line);
		   				String[] sudoku = line.split(",");
		   				sudokuModel.setDim(Integer.parseInt(sudoku[0]));
		   				restartGame(sudokuModel.getDim());
		   				sudokuModel.setUserValuesFromFile(sudoku[1]);
		   				sudokuModel.setUserValues(sudoku[1]);
		   				//sudokuView.setGridFromFile(sudoku[1]);
//		   				printUserValues();
//		   				inputUserValues();
		   			}
		    		catch (Exception ex) {
		    			System.err.println("Error opening file");
		    		} 
				}
				
				if(but.getText() == "Show") {
					//	System.out.println("jww");
						//sudokuView.showButtonGrid();
					sudokuView.setGridFromFile(sudokuModel.getUserValuesString());
				}
				
				if(but.getText() == "Hide") {
					//	System.out.println("jww");
						//sudokuView.showButtonGrid();
					sudokuView.hideGrid();
				}
				
				if(but.getText() == "Start") {
				//	System.out.println("jww");
					sudokuView.setGridFromFile(sudokuModel.getUserValuesString());
					sudokuModel.hideUserValues(sudokuView.getDifficultySelectedItem(), (sudokuModel.getGridSize()*sudokuModel.getGridSize()), sudokuModel.getDim());
					sudokuView.setGridFromFile(sudokuModel.getUserValues());
					startTimer();
				}
					
			}
			
			if(e.getSource() instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) e.getSource();
				if(item.getText() == "Design") {
					sudokuView.setDesignOrPlay(false);
					sudokuView.disableButtons();
				}
				if(item.getText() == "Play") {
					sudokuView.setDesignOrPlay(true);
					sudokuView.disableButtons();
					
				}
			}

		}
		
		/*
   		 * Checks if the left-click of the mouse was clicked
   		 */
		@Override
		public void mouseClicked(MouseEvent e) {
	          if(e.getButton() == MouseEvent.BUTTON3) {
	        	  if(e.getSource() instanceof JButton) {
	        		  JButton but = (JButton) e.getSource();
	        		  	if(but.getName() == "ButtonGrid") {
	  					  for(int row = 0; row < sudokuModel.getGridSize(); row++) {
	  						for(int col = 0; col < sudokuModel.getGridSize(); col++) {
	  							if(e.getSource() == sudokuView.getGridButton(row, col)) {
	  								sudokuView.getGridButton(row, col).setText("");
		  							sudokuModel.setValue(row, col, ".");
		  							
	  							}	  							
	  						}
	  					}
	  					sudokuModel.printUserValues();
	  				}
	        	  }
	          }
		}

		@Override
		public void mousePressed(MouseEvent e) {	}

		@Override
		public void mouseReleased(MouseEvent e) {	}

		@Override
		public void mouseEntered(MouseEvent e) {	}

		@Override
		public void mouseExited(MouseEvent e) {	}

	}
	
	private int seconds = 0;
    
    Timer timer;
    TimerTask timerTask;
    
    public void startTimer(){
        // Timer task
        timer = new Timer();
        timerTask = new TimerTask(){
            public void run(){
                seconds++;
                // update your interface
                sudokuView.timerNumber.setText(Integer.toString(seconds));
            }
        };
        try {
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }catch(Exception e){
            
        }
    }
    
    public void stopTimer(){
        timer.cancel();
        timerTask.cancel();
    }
    public void restartTimer(){
        seconds = 0;
    }
	
	
}
