package driver;

import adapter.*;
import scale.*;

public class Driver {

	public static void main(String[] args) {
		BuildAuto a = new BuildAuto();
		a.buildAuto("HondaModel.txt");
		a.printAuto();
		System.out.println("Threads edit price of Feature: Color, FeatureOption:Red\n");
		String[] p1= { "Color", "Red", "45.00" };
		String[] p2 = { "Color", "Red", "400.00"};

		EditFeatures ef1 = new EditFeatures("Civic", 3, p1);
		EditFeatures ef2 = new EditFeatures("Civic", 3, p2);

		Thread t1 = new Thread(ef1);
		Thread t2 = new Thread(ef2);
		t1.start();
		t2.start();
	}

}
