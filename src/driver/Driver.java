package driver;
import model.Automobile;
import util.FileIO;

public class Driver {

	public static void main(String[] args) {
		FileIO io = new FileIO();
		Automobile FordZTW = io.buildAutomobile("FordModel.txt");
		
		System.out.println(FordZTW.toString());
		
		io.serializeAutomobile(FordZTW, "file.dat");
		
		Automobile FordZTW2 = io.deserializeAutomobile("file.dat"); 
		
		System.out.println("\ndeserialized FordZTW: \n" + FordZTW2.toString());
		
		
	}

}
