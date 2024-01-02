package Sudoku;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;




public class G extends JFrame {

		private static final long serialVersionUID = 1L;
		
		private JPanel mainFrame  = new JPanel();
		private JPanel header= new JPanel();
		private JPanel footer = new JPanel();
	    

	     
	    public G() {
	    }

	    
	  
	    public void addComponentsToPane() {
	    	ImageIcon serverImage = new ImageIcon("src/Sudoku/server.jpg");
		     Image image = serverImage.getImage();
		     Image newimg = image.getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH);
		     ImageIcon newServerImage = new ImageIcon(newimg);
		     JButton img = new JButton(newServerImage);
		     header.add(img);
		     
		     JFrame frame = new JFrame();
		     mainFrame.add(header, BorderLayout.NORTH);
		     mainFrame.add(footer,BorderLayout.SOUTH);
		      
		     this.add(mainFrame);	
		    
	    	
	    }
	    
	    public void createAndShowGUI() {
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        UIManager.put("Label.font", new FontUIResource(new Font("Cambria", Font.PLAIN, 20)));
	        UIManager.put("Button.font", new FontUIResource(new Font("Times New Roman", Font.BOLD, 40)));
	        UIManager.put("TextField.font", new FontUIResource("Cambria", Font.PLAIN, 20));
	        
	        //Set up the content pane.
	        this.addComponentsToPane();
	        
	        //Display the window.
	        this.pack();
	        this.setVisible(true);
	    }
	     
		
		public static void main(String[] args) {
			G gc = new G();
			gc.createAndShowGUI();
			
		}
	    
}
	   