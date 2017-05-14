package driver;

import adapter.*;
import exception.AutoException;
import model.Automobile;
import util.FileIO;

public class Driver {

	public static void main(String[] args) {
		
		//using Automobile class
		FileIO io = new FileIO();
		Automobile a = null;
		try {
			a = io.buildAutomobile("FordModel.txt");
		} catch (AutoException e) {
			e.fix();
		}
		System.out.println("Automobile Object: ");
		System.out.print(a);
		System.out.println("\n******************************************************* \n Multiple Auto instances using adapter package: \n");
		//using the interface and multiple-automibile instance capabilities 
		CreateAuto FordZTW = new BuildAuto();
		CreateAuto HondaCivic = new BuildAuto();
		
		FordZTW.buildAuto("FordModel.txt");
		HondaCivic.buildAuto("HondaModel.txt");
		
		FordZTW.printAuto("Focus Wagon ZTW");
		HondaCivic.printAuto("Civic");
	
	}
	

}
