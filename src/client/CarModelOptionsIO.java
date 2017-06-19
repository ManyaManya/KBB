package client;

import java.util.Properties;
import adapter.CreateAuto;
import util.FileIO;

public class CarModelOptionsIO {

	public CarModelOptionsIO() {
			new Thread(new DefaultSocketClient("192.168.56.1", 1234)).start();
	}

	public Properties SendProperties(String filename) {
		Properties props = null;
		FileIO io = new FileIO();

		props = io.buildAutoProperties(filename);
		return props;
	}
	
	public void Verify(boolean result){ 
		if(result == true){
			System.out.println("Properties sent");
		}else{
			System.out.println("Properties were NOT sent");
		}
	}
	
	public static void main(String[] args){
		new CarModelOptionsIO();
	}

}
