package ServerClient;

import java.net.*;

import Sudoku.SudokuController;
import Sudoku.SudokuModel;
import Sudoku.SudokuView;

import java.io.*;

public class ThreadClient {
	static int PORT = 3000;
	static int portNumber = 0;
	static String HOSTNAME = "localhost";
	static String hostName = "";

	

	public static void main(String args[]) {
		if (args == null) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
		} else if (args.length != 2) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
		} else {
			hostName = args[0];
			portNumber = Integer.parseInt(args[1]);
		}
		System.out.println("Connecting with server on " + hostName + " at port " + portNumber);
		System.out.println("Starting Server Thread on port " + portNumber);
		try {
			Socket sock;
			BufferedReader dis;
			sock = new Socket(hostName, portNumber);
			dis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintStream dat = new PrintStream(sock.getOutputStream());
			String strcliid = dis.readLine();
			System.out.println("Client no." + strcliid + "...");
			String consoleData;
			String serverData;
			/// DataInputStream inConsole = new DataInputStream(System.in);
			BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Client[" + strcliid + "]: ");
			
			//start
			ClientGUI gui = new ClientGUI();
			gui.startGame(strcliid);
			
			String gameValues = gui.sendConfig();
			System.out.println(gameValues);
			OutputStream out = sock.getOutputStream();
			PrintWriter write = new PrintWriter(out,true);
			gameValues = strcliid + "#" + gameValues;
			dat.println(gameValues);
			//finish
			consoleData = inConsole.readLine();
			
			while (!consoleData.equals("end")) {
				consoleData = strcliid + "#" + consoleData;
				dat.println(consoleData);
				dat.flush();
				serverData = dis.readLine();
				System.out.println("Server: " + serverData);
				System.out.print("Client[" + strcliid + "]: ");
				consoleData = inConsole.readLine();
			}
			consoleData = strcliid + "#" + consoleData;
			dat.println(consoleData);
			dat.flush();
			sock.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	

}
