package client;

import java.net.*;

import model.Automobile;

import java.io.*;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket sock;
	private String strHost;
	private int iPort;
	CarModelOptionsIO cmoIO;
	SelectCarOption sco;

	public DefaultSocketClient(String strHost, int iPort) {
		this.strHost = strHost;
		this.iPort = iPort;
	}// constructor

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}// run

	public boolean openConnection() {
		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			System.exit(1);
			return false;
		}
		try {
			out = new ObjectOutputStream(sock.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			System.err.println("Unable to obtain serrverstream to " + strHost);
			return false;
		}
		cmoIO = new CarModelOptionsIO();
		sco = new SelectCarOption();
		return true;
	}

	// TODO
	public void handleSession() {
		String input = null;
		String task = null;
		Automobile auto = null;
		boolean finish = false;
		boolean cont = false;
		while (!finish) {
			try {
				sco.displayOptions();
				task = sco.userInput();

				switch (task) {
				case ("Upload Properties Model"):
					sendOutput("File name? ");
					input = sco.userInput();
					out.writeObject("Upload Properties");
					out.flush();
					out.writeObject(cmoIO.SendProperties(input));
					out.flush();
					input = (String) in.readObject();
					cmoIO.Verify(input.contains("Done"));
					break;
				case ("See Available Models"):
					out.writeObject("Get Models");
					out.flush();
					sendOutput("Current models: \n" + (String) in.readObject());
					break;
				case ("Create Auto"):
					cont = true;
				case ("See Model Specs"):
					sendOutput("Name of Model: ");
					input = sco.userInput();
					out.writeObject("Specs");
					out.flush();
					out.writeObject(input);
					out.flush();
					auto = (Automobile) in.readObject();
					sendOutput(auto.toString());
					if(!cont)
						break;
					sco.createAnAuto(auto);
					cont = false;
					break;
				case ("Exit"):
					finish = true;
					out.writeObject("Exit");
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void sendOutput(String strOutput) {
		System.out.println(strOutput);
	}

	public void handleInput(String strInput) {
		System.out.println(strInput);
	}

	public void closeSession() {
		System.out.println("closing");
		try {
			out = null;
			in = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}

}// class DefaultSocketClient
