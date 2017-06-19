package server;

import java.net.*;
import java.util.Properties;

import model.Automobile;

import java.io.*;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket sock;
	private BuildCarModelOptions bcmo;

	public DefaultSocketClient(Socket s, BuildCarModelOptions b) {
		sock = s;
		bcmo = b;
	}// constructor

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}// run

	public boolean openConnection() {
		try {
			out = new ObjectOutputStream(sock.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from " + sock.getPort());
			System.exit(1);
			return false;
		}
		System.out.println("OpenConnection done");
		return true;
	}

	public void handleSession() {
		System.out.println("in session");
		boolean finish = false;
		String task = null;		
		while (!finish) {
			try {
				task = (String) in.readObject();
			} catch (IOException e) {
				System.err.println("Could not read from in");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				switch (task) {
				case ("Upload Properties"):
					Properties prop = (Properties) in.readObject();
					bcmo.buildAutoFromProperties(prop);
					System.out.println("Upload Complete");
					out.writeObject("Done");
					out.flush();
					break;
				case ("Upload Automobile"):
					// idk
					break;
				case("Get Models"):
					out.writeObject(bcmo.getModels());
					out.flush();
					break;
				case("Specs"):
					String name = (String) in.readObject();
					out.writeObject(bcmo.sendAutomobile(name));
					out.flush();
					break;
				case ("Exit"):
					finish = true;
					break;

				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendOutput(String strOutput) {

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
				System.err.println("Error closing socket to " + sock.getPort());
		}
	}

}// class DefaultSocketClient
