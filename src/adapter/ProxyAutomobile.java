package adapter;

import exception.*;
import model.*;
import util.*;

public abstract class ProxyAutomobile {

	private static Automobile a1;

	// CreateAuto
	public void printAuto(String autoName) {
		// subject to change once multiple instances of auto are a thin
		System.out.println(a1.toString());
	}

	public void buildAuto(String filename) {
		boolean done = false;
		String backup = filename;
		FileIO io = new FileIO();
		do {
			try {
				done = io.openFile(backup);
				a1 = io.buildAutomobile(backup);
			} catch (AutoException a) {
				UtilExceptions ue = new UtilExceptions();
				backup = ue.fixInput(20);
			}
		} while (done == false);
	}

	// UpdateAuto
	public void updateFeatureOptionName(String modelName, String featureName, String foName, String newName) {
		try {
			a1.UpdateFeatureOption(featureName, foName, newName);
		} catch (AutoException e) {
			e.printStackTrace();
		}

	}

	public void updateFeatureOptionPrice(String modelName, String featureName, String foName, double newPrice) {
		try {
			a1.UpdateFeatureOption(featureName, foName, newPrice);
		} catch (AutoException e) {
			e.printStackTrace();
		}
	}

	// fixAuto
	public void fix(int num) {
		AutoException er = new AutoException();
		er.fix(num);
	}

}
