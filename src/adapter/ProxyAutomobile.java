package adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import exception.*;
import model.*;
import util.*;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String,Automobile> autos = new LinkedHashMap<String,Automobile>();

	public void printAuto(String autoName) {
		Set<String> a = autos.keySet();
		for(String key : a){
			if(autoName.contains(key.toString())){
				System.out.println(autos.get(key));
			}
		}
	}
	
	public void printAuto(){
		Collection<Automobile> c = autos.values();
		Iterator<Automobile> aobj = c.iterator();
		
		System.out.println("Avaiable Models:");
		while(aobj.hasNext()){
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
	public void updateFeatureOptionName(String modelName, String featureName, String foName, String newName) {
		try {
			autos.get(modelName).UpdateFeatureOption(featureName, foName, newName);
		} catch (AutoException e) {
			e.printStackTrace();
		}

	}

	public void updateFeatureOptionPrice(String modelName, String featureName, String foName, double newPrice) {
		try {
			autos.get(modelName).UpdateFeatureOption(featureName, foName, newPrice);
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
