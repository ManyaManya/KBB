package adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import exception.*;
import model.*;
import util.*;
import scale.*;
import server.*;

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

	public void buildAuto(String fileType, String filename) {
		boolean done = false;
		String model = null;
		Automobile auto = null;
		Properties p = null;
		String backup = filename;
		FileIO io = new FileIO();
		do {
			try {
				done = io.openFile(backup);
				switch (fileType) {
				case "csv":
					auto = io.buildAutoCSV(backup);
					break;
				case "properties":
					p = io.buildAutoProperties(backup);
					buildAutoFromProperties(p);
					break;
				}

				model = io.getModelKey();
				autos.put(model, auto);
			} catch (AutoException a) {
				UtilExceptions ue = new UtilExceptions();
				backup = ue.fixInput(20);
			}
		} while (done == false);
	}

	// UpdateAuto
	/*
	 * these methods are directly used in EditFeatures, they encapsulate the
	 * Automobile methods, so synchronizing here should keep everything
	 * thread-safe
	 */
	public synchronized void updateFeature(String modelName, String featureName, String newName) {
		/*
		 * auto is accessed in other updating methods, so locking to prevent
		 * access at the same time
		 */
		synchronized (autos) {
			try {
				autos.get(modelName).updateFeature(featureName, newName);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}

	public synchronized void updateFeatureOptionName(String modelName, String featureName, String foName,
			String newName) {
		synchronized (autos) {
			try {
				autos.get(modelName).updateFeatureOption(featureName, foName, newName);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}

	public synchronized void updateFeatureOptionPrice(String modelName, String featureName, String foName,
			double newPrice) {
		synchronized (autos) {
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

	// AutoServer
	public void buildAutoFromProperties(Properties props) {
		Automobile am = new Automobile();
		String modelKey;
		am.setMake(props.getProperty("Make"));
		modelKey = props.getProperty("Model");
		am.setModel(modelKey);
		am.setBasePrice(Double.parseDouble(props.getProperty("BasePrice")));

		int fnum = Integer.parseInt(props.getProperty("NumberOfFeatures"));
		int fonum;
		String index = null;
		String fname = null;
		String foname = null;
		String price = null;
		for (int i = 1; i <= fnum; i++) {
			fname = props.getProperty("Feature" + i);
			fonum = Integer.parseInt(props.getProperty("NumberOfFO" + i));
			am.addFeature(fname);
			for (int j = 1; j <= fonum; j++) {
				index = i + String.valueOf((char) (j + 96));
				foname = props.getProperty("FOption" + index);
				price = props.getProperty("Price" + index);
				try {
					am.addFeatureOption(fname, foname, Double.parseDouble(price));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (AutoException e) {
					e.printStackTrace();
				}
			}
		}
		autos.put(modelKey, am);
	}

	public String getModels() {
		StringBuilder modelNames = new StringBuilder();
		Set<String> a = autos.keySet();
		for (String key : a) {
			modelNames.append(key + "\n");
		}
		return modelNames.toString();
	}

	public Automobile sendAutomobile(String modelName){
		Set<String> a = autos.keySet();
		for (String key : a) {
			if (modelName.contains(key.toString())) {
				return autos.get(key);
			}
		}
		return null;
	}

}
