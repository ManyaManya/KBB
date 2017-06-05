package adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import exception.*;
import model.*;
import util.*;
import scale.*;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String, Automobile> autos = new LinkedHashMap<String, Automobile>();
	

	public void printAuto(String autoName) {
		Set<String> a = autos.keySet();
		for (String key : a) {
			if (autoName.contains(key.toString())) {
				System.out.println(autos.get(key));
			}
		}
	}

	public void printAuto() {
		
		Collection<Automobile> c = autos.values();
		Iterator<Automobile> aobj = c.iterator();
		System.out.println("Avaiable Models:");
		while (aobj.hasNext()) {
			System.out.println(aobj.next());
		}

	}

	public void buildAuto(String filename) {
		boolean done = false;
		String model;
		Automobile auto;
		String backup = filename;
		FileIO io = new FileIO();
		do {
			try {
				done = io.openFile(backup);
				auto = io.buildAutomobile(backup);
				model = io.getModelKey();
				autos.put(model, auto);
			} catch (AutoException a) {
				UtilExceptions ue = new UtilExceptions();
				backup = ue.fixInput(20);
			}
		} while (done == false);
	}

	// UpdateAuto
	/*these methods are directly used in EditFeatures, they encapsulate the Automobile methods, so synchronizing here should
	 * keep everything thread-safe */
	public synchronized void updateFeature(String modelName, String featureName, String newName) {
		/*auto is accessed in other updating methods, so locking to prevent access at the same time */
		synchronized (autos) {
			try {
				autos.get(modelName).updateFeature(featureName, newName);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	public synchronized void updateFeatureOptionName(String modelName, String featureName, String foName, String newName) {
		synchronized (autos) {
			try {
				autos.get(modelName).updateFeatureOption(featureName, foName, newName);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}

	
	/*FOR TESTING PURPOSES*/
	//********************************
	static String[] msg = { "Example", "of", "how", "messy", "Java", "is", "without", "synchronization" }; 
	@SuppressWarnings("static-access")
	//for testing 
		void randomWait() {
			try {
				Thread.currentThread().sleep((long) (1000 * Math.random()));
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
			}
		}
	//*********************************
	
	public synchronized void updateFeatureOptionPrice(String modelName, String featureName, String foName,
			double newPrice) {
		synchronized(autos){
			//*******
			for (int i = 0; i < msg.length; i++) {
				randomWait();
				System.out.println( msg[i]);
			}
			//*******
			
			try {
				autos.get(modelName).updateFeatureOption(featureName, foName, newPrice);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
	}

	// fixAuto
	public void fix(int num) {
		AutoException er = new AutoException();
		er.fix(num);
	}

	// OptionEdit, for link between BuildAuto and EditFeatures
	public void Edit(String modelName, int operation, String[] param) {
		EditFeatures ef = new EditFeatures(modelName, operation, param);
	}

}
