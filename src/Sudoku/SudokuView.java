package Sudoku;
/**
 * Team Members: Samuel Omaghomi/ Nathan Ploughman
 * Assessment: Assignment A22
 * Professor: Paulo Sousa
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;




public class SudokuView extends JFrame{

	// splash window
	   class GameSplash extends JFrame {
		   private static final long serialVersionUID = 1L;
		   JWindow window = new JWindow();
	       public void displaySplash(){  
				window.getContentPane().add(
				    new JLabel("", new ImageIcon("src/Sudoku/sudoku_splash.png"), SwingConstants.CENTER));
				window.setSize(650,325);
				window.setLocationRelativeTo(null);
				window.setVisible(true);
				try {
	    			Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				window.setVisible(false);
	       }
	               
	   }
	    
	    GameSplash splashTest = new GameSplash();
		private static final long serialVersionUID = 1L;
		private static final String sudokuValues[] = {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"};
		public int gridSize;
		public int bottomGridSize;
		public JButton[][] buttonGrid;
		public JButton[][] temporaryButtonGrid;
		private JButton[] bottomButtonGrid;
		public JPanel game = new JPanel();
		
		boolean designOrPlay = false;
		
		public static final String dimList[] = {"2","3","4"};
	//	JComboBox<String> dimListComboBox;
	//	public static String userValues[][];
		
		private int dim;
		Color darkPurple = new Color(140,125,192);
		Color lightPurple = new Color(218,210,233);
		Color colors[] = {darkPurple, lightPurple};

        JPanel mainPanel = new JPanel(new GridLayout(8,0));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel timerPanel = new JPanel();
        JPanel pointPanel = new JPanel();
        JPanel usernamePanel = new JPanel();

	    // buttonsPanel
	    // buttons
        ImageIcon aboutImage = new ImageIcon("src/Sudoku/sudoku_about.png");
        Image image = aboutImage.getImage();
        Image newimg = image.getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newAboutImage = new ImageIcon(newimg);
            
        JButton imageButton = new JButton(newAboutImage);
        
        JMenu mode = new JMenu("Mode");
		JMenu help = new JMenu("Help");
		JMenuItem design = new JMenuItem("Design");
		JMenuItem play = new JMenuItem("Play");
           
        // fields for points
        JLabel pointLabel = new JLabel();
        
        JButton resetButton = new JButton("Reset");
	    JButton showButton = new JButton("Show");
	    JButton hideButton = new JButton("Hide");
	    JButton saveButton = new JButton("Save");
	    JButton loadButton = new JButton("Load");
	    JButton randomButton = new JButton("Random");
	    JButton startButton = new JButton("Start");
	    
	    // lists
	 //   String[] dimStrings = {"2", "3", "4"};
	    JComboBox<String> dimensions = new JComboBox<String>(dimList);
	    String[] levelStrings = {"Easy", "Medium", "Hard"};
	    JComboBox<String> levels = new JComboBox<String>(levelStrings);
	    
	    JTextField dimensionText = new JTextField("Dimension:");
	    JTextField levelText = new JTextField("Difficulty:");
	    JButton applyButton = new JButton("Apply");
	    
	    int timerValue = 0;
        JLabel timerNumber = new JLabel(Integer.toString(timerValue));
	    
	    JPanel gameGrid;
	    public String userName;
	     
	    public SudokuView(int size, int gridSize) {
	    	splashTest.displaySplash();
	    	dim = size;
	    	this.gridSize = gridSize;		      
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    UIManager.put("Label.font", new FontUIResource(new Font("Cambria", Font.PLAIN, 20)));
		    UIManager.put("Button.font", new FontUIResource(new Font("Times New Roman", Font.BOLD, 40)));
		    UIManager.put("TextField.font", new FontUIResource("Cambria", Font.PLAIN, 20));
		        
		        //Set up the content pane.
		//    userName = "Samuel";
		    this.addComponentsToPane();
		    this.pack();
		    this.setResizable(false);
			this.setTitle("Sudoku Game by Samuel Omaghomi & Nathan Ploughman");
	    }
	    
	    public SudokuView(int size, int gridSize, String id, boolean playMode) {
	    	splashTest.displaySplash();
	    	dim = size;
	    	this.gridSize = gridSize;		      
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    UIManager.put("Label.font", new FontUIResource(new Font("Cambria", Font.PLAIN, 20)));
		    UIManager.put("Button.font", new FontUIResource(new Font("Times New Roman", Font.BOLD, 40)));
		    UIManager.put("TextField.font", new FontUIResource("Cambria", Font.PLAIN, 20));
		        
		        //Set up the content pane.
		    userName = id;
		    this.addComponentsToPane();
		    designOrPlay = playMode;
		    this.pack();
		    this.setResizable(false);
			this.setTitle("Sudoku Game by Samuel Omaghomi & Nathan Ploughman");
	    }
	    
	    public void setDesignOrPlay(boolean designOrPlay) {
	    	this.designOrPlay = designOrPlay;
	    }
	    
	    public void setDim(int dim) {
	    	this.dim = dim;
	    }
	    
	    public void setGridSize(int size) {
	    	this.gridSize = size;
	    }
	   
	    public int getBottomGridButtonRow(JButton b) {
	    	for(int row = 0; row < gridSize; row++) {
				if(b == bottomButtonGrid[row]) {
					return row;
				}
			}
	    	return -1;
	    }
	    
	    public JButton getBottomGridButton(int row) {
	    	return bottomButtonGrid[row];
	    }
	    
	    public JButton getGridButton(int row, int col) {
	    	return buttonGrid[row][col];
	    }
	    
	    public int getGridButtonRow(JButton b) {
	    	for(int row = 0; row < gridSize; row++) {
				for(int col = 0; col < gridSize; col++) {
					if(b == buttonGrid[row][col]) {
						return row;
					}
				}
			}
	    	return -1;
	    }
	    
	    public int getGridButtonCol(JButton b) {
	    	for(int row = 0; row < gridSize; row++) {
				for(int col = 0; col < gridSize; col++) {
					if(b == buttonGrid[row][col]) {
						return col;
					}
				}
			}
	    	return -1;
	    }
	    
	    public void addComponentsToPane() {
	    	//initDim();	//initializes dimensions of dim box
	        bottomGridSize = gridSize;	
	        
	        
	    	gameGrid = new JPanel();
	    	
	    	gameGrid.setLayout(new GridLayout(gridSize+1, gridSize,2,2));
	    	JPanel gameControls = new JPanel();
	    	gameControls.setLayout(new GridLayout(2,3));
	    	
	    	
		    bottomButtonGrid = new JButton[bottomGridSize];
		    gameGrid.setPreferredSize(new Dimension(1050, 750));
		      
		    /*
		     * Initializes the top button grid
		     */
		    buttonGrid = new JButton[gridSize][gridSize];
		    for(int row =0; row < gridSize; row++) {
		      	for(int col= 0; col < gridSize; col++) {
		      		JButton btn = new JButton();
		       		btn.setName("ButtonGrid");		       		
		       		buttonGrid[row][col] = btn;
		       		buttonGrid[row][col].setBackground(whatColor(row,col));
		       		
		       		gameGrid.add(buttonGrid[row][col]);
		       	}
		    }
		    
		    /*
		     * Initializes the bottom button grid
		     */
		    for(int row =0; row < bottomGridSize; row++) {
		       	JButton btn = new JButton(sudokuValues[row]);
		       	btn.setBackground(new Color(231,229,230));
		       	btn.setForeground(Color.BLACK);
		       	btn.setName("BottomButtonGrid");
		       	btn.setActionCommand(sudokuValues[row]);
		       //	btn.addActionListener(this);
		       	bottomButtonGrid[row] = btn;
		       	gameGrid.add(bottomButtonGrid[row]);	
		       	
		    }
		    
		    addPanelsToFrame();
	    }
	    
	    private Color whatColor(int row, int col) {
	    	int rowBox = whatBox(row);
	    	int colBox = whatBox(col);
	    	if(rowBox == 0) {
	    		if(colBox == 0) {
	    			return colors[0];
	    		}else if(colBox == dim) {
	    			return colors[1];
		    	}else if(colBox == dim*2) {
		    		return colors[0];
		    	}else if(colBox == dim*3) {
		    		return colors[1];
		    	}
	    	}else if(rowBox == dim) {
	    		if(colBox == 0) {
	    			return colors[1];
	    		}else if(colBox == dim) {
	    			return colors[0];
		    	}else if(colBox == dim*2) {
		    		return colors[1];
		    	}else if(colBox == dim*3) {
		    		return colors[0];
		    	}
	    	}else if(rowBox == dim*2) {
	    		if(colBox == 0) {
	    			return colors[0];
	    		}else if(colBox == dim) {
	    			return colors[1];
		    	}else if(colBox == dim*2) {
		    		return colors[0];
		    	}else if(colBox == dim*3) {
		    		return colors[1];
		    	}
	    	}else if(rowBox == dim*3) {
	    		if(colBox == 0) {
	    			return colors[1];
	    		}else if(colBox == dim) {
	    			return colors[0];
		    	}else if(colBox == dim*2) {
		    		return colors[1];
		    	}else if(colBox == dim*3) {
		    		return colors[0];
		    	}
	    	}
	    	
			return null;
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
	    
	    private void addPanelsToFrame() {
	    	// Adding buttons to panels 1 through 5
	    	usernamePanel.add(new Label("Username: "));
	    	usernamePanel.add(new JLabel(userName));
	    	//JLabel timerNumber = new JLabel(Integer.toString(timerValue));
	        panel1.add(imageButton);
	
			panel2.add(new Label("Dim:"));
			panel2.add(new Label(" "));
			panel2.add(dimensions);
			panel2.add(applyButton);
			panel3.add(showButton);
			panel3.add(hideButton);
	        panel3.add(resetButton);
	        panel4.add(saveButton);
		    panel4.add(loadButton);
		    panel4.add(randomButton);
		    panel5.add(levelText);
		    panel5.add(levels);
	        panel5.add(startButton);
	        timerPanel.add(new Label("Current Time: "));
            timerPanel.add(timerNumber);
            pointPanel.add(new Label("Points: "));
            pointPanel.add(pointLabel);
	                
	      // adding panels 1 through 5 to mainPanel
            mainPanel.add(usernamePanel);
	        mainPanel.add(panel1);
	        mainPanel.add(panel2);
	        mainPanel.add(panel3);
	        mainPanel.add(panel4);
	        mainPanel.add(panel5);
	        mainPanel.add(timerPanel);
            mainPanel.add(pointPanel);

	        
	       // Creating menu
			 JMenuBar menuBar = new JMenuBar();
			 this.setJMenuBar(menuBar);
			 // Creating menu option
			
			 menuBar.add(mode);
			 menuBar.add(help);
			 mode.add(design);
			 mode.add(play);
	      
	//    System.out.println(gameGrid);
			game.add(gameGrid, BorderLayout.WEST);
		  	game.add(new JSeparator(), BorderLayout.CENTER);
		    game.add(mainPanel, BorderLayout.LINE_END);
                    
		    this.add(game);
	    }
	    
	    public String getDimSelectedItem() {
	    	return (String)dimensions.getSelectedItem();
	    }
	    
	    public String getDifficultySelectedItem() {
	    	return (String)levels.getSelectedItem();
	    }
	    
	    public void setGridFromFile(String input) {
	    	char[] ch = input.toCharArray();
	    	int i = 0;
	    	for(int row = 0; row < gridSize; row++) {
	    		for(int col = 0;  col < gridSize; col++) {
	    			//userValues[row][col] = Character.toString(ch[i]);
	    			if(Character.toString(ch[i]) == ".") {
	    				buttonGrid[row][col].setText(" ");
	    				break;
	    			}
	    			buttonGrid[row][col].setText(Character.toString(ch[i]));
	    		//	userValues[row][col] = Character.toString(ch[i]);
	    			i++;
	    		}
	    	}
	    }
	    
	    public void setGridFromFile(String[][] input) {
	    	for(int row = 0; row < gridSize; row++) {
	    		for(int col = 0;  col < gridSize; col++) {
	    			if(input[row][col] == ".") {
	    				buttonGrid[row][col].setText(" ");
	    				break;
	    			}
	    			buttonGrid[row][col].setText(input[row][col]);
	    		}
	    	}
	    }
	    
	    /**
	     * Restarts the game
	     */
	    public void discard() {
	    	this.dispose();	//disposes the current frame 
	    }
	    
	    public void addButttonGridListener(ActionListener listerForCell) {
	    	for(int row =0; row < gridSize; row++) {
	    		bottomButtonGrid[row].addActionListener(listerForCell);
	    		for(int col = 0; col < gridSize; col++) {
	    			buttonGrid[row][col].addActionListener(listerForCell);
	    		}
	    	}
	    }
	    public void addSaveListener(ActionListener listenForSave) {
	    	saveButton.addActionListener(listenForSave);
	    }
	    public void addLoadListener(ActionListener listenForLoad) {
	    	loadButton.addActionListener(listenForLoad);
	    }
	    
	    public void addResetListener(ActionListener listenForReset) {
	    	resetButton.addActionListener(listenForReset);
	    }
	    
	    public void addApplyListener(ActionListener listonForApply) {
	    	applyButton.addActionListener(listonForApply);
	    }
	    
	    public void addImageListener(ActionListener listenForImage) {
	    	imageButton.addActionListener(listenForImage);
	    }
	    
	    public void addStartListener(ActionListener listenForStart) {
	    	startButton.addActionListener(listenForStart);
	    }
	    
	    public void addShowListener(ActionListener listenForShow) {
	    	showButton.addActionListener(listenForShow);
	    }
	    
	    public void addHideListener(ActionListener listenForHide) {
	    	hideButton.addActionListener(listenForHide);
	    }
	    
	    public void addDeisgnListener(ActionListener listenForDesign) {
	    	design.addActionListener(listenForDesign);
	    }
	    
	    public void addPlayListener(ActionListener listenForPlay) {
	    	play.addActionListener(listenForPlay);
	    }
	    
	    public void addLeftClickListener(MouseListener listenForClick) {
	    	for(int row =0; row < gridSize; row++) {
	    		for(int col = 0; col < gridSize; col++) {
	    			buttonGrid[row][col].addMouseListener(listenForClick);
	    		}
	    	}
	    	
	    }
	    
	    public void hideGrid() {
	    	for(int row =0; row < gridSize; row++) {
	    		for(int col = 0; col < gridSize; col++) {
	    			buttonGrid[row][col].setText("");
	    		}
	    	}
	    }
	    public void setTemporaryButtonGrid() {
	    	temporaryButtonGrid = buttonGrid;
	    }
	    
	    public void showButtonGrid() {
	    	//System.out.println("ffd");
	    	for(int row =0; row < gridSize; row++) {
	    		for(int col = 0; col < gridSize; col++) {
	    			System.out.print(temporaryButtonGrid[row][col].getText());
	    			buttonGrid[row][col].setText(temporaryButtonGrid[row][col].getText());
	    		}
	    	}
	   // 	buttonGrid = temporaryButtonGrid;
	    }
	    
	    public void disableButtons() {
	    	if(designOrPlay == false) {
	    		startButton.setEnabled(false);
	    		showButton.setEnabled(false);
	    		hideButton.setEnabled(false);
	    		randomButton.setEnabled(false);
	    		saveButton.setEnabled(true);
	    		loadButton.setEnabled(true);
	    		applyButton.setEnabled(true);
	    		dimensions.setEnabled(true);
	    		levels.setEnabled(false);
	    	}else {
	    		saveButton.setEnabled(false);
	    		loadButton.setEnabled(true);
	    		applyButton.setEnabled(false);
	    		startButton.setEnabled(true);
	    		showButton.setEnabled(true);
	    		hideButton.setEnabled(true);
	    		randomButton.setEnabled(true);
	    		dimensions.setEnabled(false);
	    		levels.setEnabled(true);
	    	}
	    }
	    
	    public boolean getMode() {
	    	return designOrPlay;
	    }
	    public void setTime(String time){
            String timeString = time;
            timerNumber.setText(time);
        }
	    
	   
}
