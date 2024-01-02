package ServerClient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class ThreadServer extends JFrame implements Runnable{
	Socket sock;
	static int nclient = 0, nclients = 0;
	static ServerSocket servsock;
	static int PORT = 3000;
	static int portNumber = 0;
	private JPanel mainFrame  = new JPanel();
	private JPanel header= new JPanel();
	private JPanel footer = new JPanel();
	
	static JFrame serverFrame = new JFrame("Game Server for Sudoku");
    
    JPanel serverPanel = new JPanel();
    BoxLayout boxlayout = new BoxLayout(serverPanel, BoxLayout.Y_AXIS);
   
   
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
    JLabel portLabel = new JLabel("Port:");
    JTextField portField = new JTextField("            ");
   	
   // portField.setSize(200, 100);
///    portField.add(new JLabel(Integer.toString(portNumber)));
    JButton startButton = new JButton("Start");
    JButton resultsButton = new JButton("Results");
    JCheckBox finalizeButton = new JCheckBox("Finalize");
    JButton endButton = new JButton("End");
    
    String arrayValues = "2,2314413212433421";
    boolean endServer = false;
    
    // logPanel
   private  JTextArea log = new JTextArea();

	public static void main(String[] args) {
		ThreadServer server = new ThreadServer();
		server.addComponents();
	}

	void addComponents() {
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startServer();
			}
			
		});
		
		portField.setEditable(false);
		serverPanel.setLayout(boxlayout);
		
	    splashPanel.add(picLabel);
	        
	    buttonsPanel.add(portLabel);
	    buttonsPanel.add(portField);
	    buttonsPanel.add(startButton);
	    buttonsPanel.add(resultsButton);
	    buttonsPanel.add(finalizeButton);
	    endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				endServer = true;
				disposeServer();
			}
	    	
	    });
	    buttonsPanel.add(endButton);
	    
	  //  log.setSize(500,200);
	    logPanel.add(log);

	    serverPanel.add(splashPanel);
	    serverPanel.add(buttonsPanel);
	    serverPanel.add(logPanel);
	    
	        
	    serverFrame.add(serverPanel);
	    
	    //serverFrame.add(serverPanel);
	    serverFrame.setSize(600,500);
	    serverFrame.setVisible(true);
	}
	
	void startServer(){
		portNumber = PORT;   
	
		portField.setText(Integer.toString(portNumber));
        System.out.println("Starting Server Thread on port " + portNumber);
        log.append("Starting Server Thread on port " + portNumber);
        log.append("\n");
        
		try {
			servsock = new ServerSocket(portNumber);
			Thread servDaemon = new Thread(new ThreadServer());
			servDaemon.start();		
			System.out.println("Server running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!");
			log.append("Server running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!\n");
			
			startButton.setEnabled(false);
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	public void run() {
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
				log.append("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".\n");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			Worked w = new Worked(sock, nclient);
			w.start();
		}
	}
	
	void disposeServer() {
		this.dispose();
	}

	class Worked extends Thread {
		Socket sock;
		int clientid, poscerq;
		String strcliid, protocol;

		public Worked(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}
		
		void end() {
			try {
				System.out.println("Ending server...");
				log.append("Ending server...\n");
				sock.close();
				disposeServer();
				System.exit(0);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void run() {
			String data;
			PrintStream out = null;
			BufferedReader in;
			
			try {
				out = new PrintStream(sock.getOutputStream());
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out.println(clientid);
//				log.append(clientid+"\n");
//				data = in.readLine();
//				poscerq = data.indexOf("#");
//				strcliid = data.substring(0, poscerq);
//				protocol = data.substring(poscerq + 1, data.length());
				protocol = " ";
				while (!protocol.equals("P0")) {
				//	if(protocol != " ") {
						data = in.readLine();
						poscerq = data.indexOf("#");
						strcliid = data.substring(0, poscerq);
						protocol = data.substring(poscerq + 1, data.length());
						
						
//					/	data = in.readLine();
						
						
						strcliid = data.substring(0, poscerq);
						protocol = data.substring(poscerq + 1, data.length());
						if(protocol.length() > 2) {
							int stopIndex = protocol.indexOf("#");
							arrayValues = protocol.substring(stopIndex+1, protocol.length());
							protocol = protocol.substring(poscerq-1, stopIndex);
							//String[] dataSplit = data.split(",");
							
						}
						
						if(protocol.equals("P1")) {
							System.out.println("Cli[" + strcliid + "]: " + data);
							log.append("Cli[" + strcliid + "]: " + data + "\n");
							out.println("String '" + data + "’ received.");
							out.flush();
						}
						//String protocol = 
						
						
						//System.out.println(protocol.length());
						//System.out.println(poscerq+" "+protocol+" "+arrayValues);
						if(protocol.equals("P2")) {
							System.out.println(protocol);
							System.out.println(arrayValues);
							
							out.println(arrayValues);
							out.flush();
						}
					//}
				}
				System.out.println("Disconecting " + sock.getInetAddress() + "!");
				log.append("Disconecting " + sock.getInetAddress() + "!\n");
				nclients -= 1;
				System.out.println("Current client number: " + nclients);
				log.append("Current client number: " + nclients + "\n");
				if (nclients == 0) {
					System.out.println("Ending server...");
					log.append("Ending server...\n");
					sock.close();
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
				log.append(ioe + "\n");
			}
		}
		
	}
}
