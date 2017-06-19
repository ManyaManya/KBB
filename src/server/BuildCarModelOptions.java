package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import adapter.BuildAuto;
import model.Automobile;

public class BuildCarModelOptions implements AutoServer {
	private ServerSocket serverSocket = null;
	private BuildAuto buildAuto = null;

	public BuildCarModelOptions() {
		buildAuto = new BuildAuto();
		try {
			serverSocket = new ServerSocket(1234);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 1234.");
			System.exit(1);
		}
		System.out.println("Server is up");
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
			new Thread(new DefaultSocketClient(clientSocket, this)).start();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

	}

	public String getModels(){
		return buildAuto.getModels();
	}
	
	public Automobile sendAutomobile(String modelName){ 
		return buildAuto.sendAutomobile(modelName);
	}
	
	public void buildAutoFromProperties(Properties props) {
		try {
			buildAuto.buildAutoFromProperties(props);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BuildCarModelOptions();
	}
}
