package ServerClient;

import java.net.*;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Sudoku.SudokuController;
import Sudoku.SudokuModel;
import Sudoku.SudokuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ThreadClient extends JFrame{
	static int PORT = 3000;
	static int portNumber = 0;
	static String HOSTNAME = "localhost";
	static String hostName = "";
	
	String protocol;
	String strcliid = null;
	Socket sock;
	BufferedReader dis;
	PrintStream dat;
	
	private static final long serialVersionUID = 1L;
	JFrame clientFrame = new JFrame("Game Client for Sudoku");
	static int clientCount = 0;
	    
	JPanel clientPanel = new JPanel();
	BoxLayout boxlayout = new BoxLayout(clientPanel, BoxLayout.Y_AXIS);
	
	   
	    // child panels
	JPanel splashPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JPanel logPanel = new JPanel();
	    
	    // splashPanel
	 ImageIcon aboutImage = new ImageIcon("src/Sudoku/sudoku_about.png");
	    //Image image = aboutImage.getImage();
	    //Image newimg = image.getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH);
	 JLabel picLabel = new JLabel(aboutImage);
	    
	    // buttonsPanel
	 JLabel userLabel = new JLabel("User:");
	 JTextField userField = new JTextField("        ");
	 JLabel serverLabel = new JLabel("Server:");
	 JTextField serverField = new JTextField("        ");
	 JLabel portLabel = new JLabel("User:");
	 JTextField portField = new JTextField("        ");
	 JButton connectButton = new JButton("Connect");
	 JButton endButton = new JButton("End");
	 JButton newgameButton = new JButton("New Game");
	 JButton sendgameButton = new JButton("Send Game");
	 JButton receivegameButton = new JButton("Receive Game");
	 JButton senddataButton = new JButton("Send Data");
	 JButton playButton = new JButton("Play");
	 
	 ClientGUI gui = new ClientGUI();
	 
	 String sudokuConfig = "2,2314413212433421";
	 boolean end = false;
	      
	    // logPanel
	 private JTextArea log = new JTextArea();

	ThreadClient(){
		clientCount++;
	}

	void addComponents() {
		clientPanel.setLayout(boxlayout);
		
		splashPanel.add(picLabel);
		userField.setEditable(false);
		serverField.setEditable(false);
		portField.setEditable(false);
	        
		buttonsPanel.add(userLabel);
		buttonsPanel.add(userField);
		buttonsPanel.add(serverLabel);
		buttonsPanel.add(serverField);
		buttonsPanel.add(portLabel);
		buttonsPanel.add(portField);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startClient();
				
			}			
		});
		
		buttonsPanel.add(connectButton);
		
		endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				end = true;
				endClient();
			}
			
		});
		buttonsPanel.add(endButton);
		
		newgameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudokuConfig = "2,2314413212433421";	
				appendNewText("New Configuration: " + sudokuConfig + "\n");
		//		log.append("New Configuration: " + sudokuConfig + "\n");
			}
			
		});
		buttonsPanel.add(newgameButton);
		
		sendgameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				///String gameValues = gui.sendConfig();
				String gameValues = sudokuConfig;
				System.out.println(gameValues);
				OutputStream out;
				try {
					out = sock.getOutputStream();
					PrintWriter write = new PrintWriter(out,true);
					protocol = "P1";
					gameValues = strcliid + "#" + protocol+ "#" + gameValues;
					dat.println(gameValues);
					String result = dis.readLine();
					//dis.readLine();
					System.out.println(result);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
			
		});
	    buttonsPanel.add(sendgameButton);
	    
	    receivegameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String output = null;
				OutputStream out;
				try {
					out = sock.getOutputStream();
				//	PrintWriter write = new PrintWriter(out,true);
					protocol = "P2";
					output = strcliid + "#" + protocol;
					
					dat.println(output);
					String result = dis.readLine();
					System.out.println(result);
					sudokuConfig = result;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	    	
	    });
	    buttonsPanel.add(receivegameButton);
	    buttonsPanel.add(senddataButton);
	    
	    playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//gui.startGame(strcliid);
				gui.setSudokuConfig(sudokuConfig,strcliid);
			}
	    });
	    buttonsPanel.add(playButton);
		    
	    log.setSize(500,200);
	    logPanel.add(log);

	    clientPanel.add(splashPanel);
	    clientPanel.add(buttonsPanel);
	    clientPanel.add(logPanel);
		    
		        
	    clientFrame.add(clientPanel);
		    
		    //clientFrame.add(clientPanel);
	    clientFrame.setSize(600,500);
	    clientFrame.setVisible(true);
	}
	
	
	public static void main(String args[]) {
		ThreadClient client = new ThreadClient();
		client.addComponents();
	}
	
	void startClient() {

		hostName = HOSTNAME;
		portNumber = PORT;
			
		System.out.println("Connecting with server on " + hostName + " at port " + portNumber);
		appendNewText("Connecting with server on " + hostName + " at port " + portNumber + "\n");
	//	log.append("Connecting with server on " + hostName + " at port " + portNumber + "\n");
		
		serverField.setText(HOSTNAME);
		portField.setText(Integer.toString(portNumber));
		System.out.println("Starting Server Thread on port " + portNumber);
		appendNewText("Starting Server Thread on port " + portNumber + "\n");
	//	log.append("Starting Server Thread on port " + portNumber + "\n");
		
		try {
			
			sock = new Socket(hostName, portNumber);
			dis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			dat = new PrintStream(sock.getOutputStream());
			strcliid = dis.readLine();
			
			System.out.println("Client no." + strcliid + "...");
			appendNewText("Client no." + strcliid + "..." + "\n");
			//log.append("Client no." + strcliid + "..." + "\n");
			userField.setText(strcliid);
			
			String consoleData;
			String serverData;
			/// DataInputStream inConsole = new DataInputStream(System.in);
			BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Client[" + strcliid + "]: ");
			appendNewText("Client[" + strcliid + "]: \n");
		//	log.append("Client[" + strcliid + "]: \n");
			
			//start
			/*
			
			gui.startGame(strcliid);
			
			String gameValues = gui.sendConfig();
			System.out.println(gameValues);
			OutputStream out = sock.getOutputStream();
			PrintWriter write = new PrintWriter(out,true);
			gameValues = strcliid + "#" + gameValues;
			dat.println(gameValues);
			*/
			//finish
			consoleData = "Hi";
			
			/*
			while (!consoleData.equals("end")) {
				consoleData = strcliid + "#" + consoleData;
				dat.println(consoleData);
				dat.flush();
				serverData = dis.readLine();
				System.out.println("Server: " + serverData);
				log.append("Server: " + serverData + "\n");
				System.out.print("Client[" + strcliid + "]: ");
				log.append("Client[" + strcliid + "]: \n");
				consoleData = inConsole.readLine();
			}
			*/

			//sock.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			//log.append("Error: " + e.getMessage());
			appendNewText("Error: " + e.getMessage());
		}
	}
	
	void endClient() {
		if(end == true) {			
			try {
				String consoleData;
				protocol = "P0";
				consoleData = strcliid + "#" + protocol;
				dat.println(consoleData);
				appendNewText(consoleData + "\n");
				//log.append(consoleData + "\n");
				dat.flush();
				sock.close();
				System.exit(0);
				this.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: " + e.getMessage());
				//log.append("Error: " + e.getMessage());
				appendNewText("Error: " + e.getMessage());
			}
		}
		
	}
	
	public void appendNewText(String txt) {
		  SwingUtilities.invokeLater(new Runnable() {
		     public void run() {
				log.setText(log.getText() + "\n" + txt);
		    	// appendNewText(log.getText() + "\n" + txt);
		       //outputText.setText(outputText.getText + "\n"+ txt); Windows LineSeparator
		     }
		  });
		}

	

}
