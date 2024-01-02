package ServerClient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.*;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

	public static void main(String args[]) {
    	if (args == null) {
            portNumber = PORT;    		
    	} else if (args.length < 1) {
            //System.err.println("Usage: java EchoServer <port number>");
            portNumber = PORT;
        } else {
            portNumber = Integer.parseInt(args[0]);
        }
        System.out.println("Starting Server Thread on port " + portNumber);
		try {
			servsock = new ServerSocket(portNumber);
			Thread servDaemon = new Thread(new ThreadServer());
			servDaemon.start();
			
			System.out.println("Server running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		ThreadServer threadServer = new ThreadServer();
		threadServer.addComponentsToFrame();
	}
	
	public void addComponentsToFrame() {
		 ImageIcon serverImage = new ImageIcon("src/server.png");
	     Image image = serverImage.getImage();
	     Image newimg = image.getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH);
	     ImageIcon newServerImage = new ImageIcon(newimg);
	     JButton img = new JButton(newServerImage);
	     header.add(img);
		
	     JFrame frame = new JFrame();
	     mainFrame.add(header, BorderLayout.NORTH);
	     mainFrame.add(footer,BorderLayout.SOUTH);
	     
	     this.add(mainFrame);	
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     UIManager.put("Label.font", new FontUIResource(new Font("Cambria", Font.PLAIN, 20)));
		 UIManager.put("Button.font", new FontUIResource(new Font("Times New Roman", Font.BOLD, 40)));
		 UIManager.put("TextField.font", new FontUIResource("Cambria", Font.PLAIN, 20));
		        
		        //Set up the content pane.
		 this.pack();
		 this.setResizable(false);
	}

	
	public void run() {
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			Worked w = new Worked(sock, nclient);
			w.start();
		}
	}

	class Worked extends Thread {
		Socket sock;
		int clientid, poscerq;
		String strcliid, dadosCliente;

		public Worked(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}

		public void run() {
			String data;
			PrintStream out = null;
			BufferedReader in;
			String arrayValues;
			try {
				out = new PrintStream(sock.getOutputStream());
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out.println(clientid);
				data = in.readLine();
				poscerq = data.indexOf("#");
				strcliid = data.substring(0, poscerq);
				dadosCliente = data.substring(poscerq + 1, data.length());
				while (!dadosCliente.equals("end")) {
					System.out.println("Cli[" + strcliid + "]: " + data);
					out.println("String '" + data + "’ received.");
					out.flush();
					data = in.readLine();
					poscerq = data.indexOf("#");
					strcliid = data.substring(0, poscerq);
					dadosCliente = data.substring(poscerq + 1, data.length());
					
					String[] dataSplit = data.split(",");
					arrayValues = dataSplit[1];
				}
				System.out.println("Disconecting " + sock.getInetAddress() + "!");
				nclients -= 1;
				System.out.println("Current client number: " + nclients);
				if (nclients == 0) {
					System.out.println("Ending server...");
					sock.close();
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}
}
