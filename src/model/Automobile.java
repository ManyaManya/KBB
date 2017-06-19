package model;

import java.lang.StringBuilder;
import java.util.ArrayList;
import exception.AutoException;
import model.Automobile.Feature.FeatureOption;

public class Automobile implements java.io.Serializable {
	String model;
	String make;
	double basePrice;
	ArrayList<Feature> features = new ArrayList<Feature>();
	ArrayList<FeatureOption> choices = new ArrayList<FeatureOption>();

	// constructors
	public Automobile() {
	}

	public Automobile(String m, String md, double bp) {
		make = m;
		model = md;
		basePrice = bp;
	}

	// getters
	public String getModel() throws AutoException {
		try {
			return model;
		} catch (NullPointerException e) {
			throw new AutoException(AutoException.AutoError.MISSING_MODEL_NAME);
		}
	}

	public String getMake() {
		return make;
	}

	public double getBasePrice() throws AutoException {
		try {
			return basePrice;
		} catch (NullPointerException e) {
			throw new AutoException(AutoException.AutoError.MISSING_MODEL_PRICE);
		}
	}

	public String getFeature(String fname) throws AutoException {
		int x = findFeaturePos(fname);
		return features.get(x).toString();
	}

	public int getNumOfFeatures() {
		return features.size();
	}

	public double getFeatureOptionPrice(String fname, String foname) throws AutoException {
		int x = findFeaturePos(fname);
		return features.get(x).getFeatureOptionPrice(foname);
	}

	public String getFeatureOptionChoices() throws AutoException {
		StringBuilder fc = new StringBuilder();
		int size = choices.size();
		System.out.println(size);
		for (int i = 0; i < size; i++) {
			if (choices.get(i) != null) {
				fc.append(features.get(i).getName() + " : " + choices.get(i).getName() + "\n");
				System.out.println(fc.toString());
			}
		}
		return fc.toString();
	}

	public String getFeatureOptionChoice(String fname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1 && choices.get(x) != null)
			return choices.get(x).getName();
		return "no feature option chosen for this feature";
	}

	public double getFeatureOptionChoicePrice(String fname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1 && choices.get(x) != null)
			return choices.get(x).getPrice();
		return 0;
	}

	public double getTotalPrice() {
		double tp = basePrice;
		for (int i = 0; i < choices.size(); i++) {
			if (choices.get(i) != null)
				tp += choices.get(i).getPrice();
		}
		return tp;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Make: " + make + " | Model: " + model + " | Base Price: $" + basePrice + "\n");
		if (features != null) {
			sb.append("[Features: Feature Options] \n");
			for (int i = 0; i < features.size(); i++) {
				sb.append(features.get(i).toString());
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	// find
	public boolean findFeature(String fname) throws AutoException {
		if (findFeaturePos(fname) != -1)
			return true;
		else
			return false;
	}

	private int findFeaturePos(String fname) throws AutoException {
		try {
			for (int i = 0; i < features.size(); i++) {
				if (features.get(i).getName().contains(fname)) {
					return i;
				}
			}
		} catch (NullPointerException e) {
			throw new AutoException(AutoException.AutoError.NULL_FEATURES_SIZE);
		}
		return -1;
	}

	public boolean findFeatureOption(String fname, String foname) throws AutoException {
		int x = -1;
		try {
			x = findFeaturePos(fname);
		} catch (AutoException e) {
			e.printStackTrace();
		}
		if (x != -1) {
			if (features.get(x).findFeatureOptionPos(foname) != -1)
				return true;
		}
		return false;
	}

	// Setters
	public void setModel(String name) {
		model = name;
	}

	public void setMake(String name) {
		make = name;
	}

	public void setBasePrice(double d) {
		basePrice = d;
	}

	public void setFeatureOptionChoice(String fname, String foname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1) {
			features.get(x).setFeatureOptionChoice(foname);
			choices.add(x, features.get(x).getFeatureOptionChoice());
		}
	}

	// Delete
	public void deleteFeature(String fname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features.remove(x);
		refactorChoices();
	}

	private void refactorChoices() {
		choices.clear();
		for (int i = 0; i < features.size(); i++) {
			choices.add(features.get(i).getFeatureOptionChoice());
		}
	}

	public void deleteFeatureOption(String fname, String foname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features.get(x).deleteFeatureOption(foname);
	}

	// Update
	public void updateFeature(String fname, String newName) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features.get(x).name = newName;
	}

	public void updateFeatureOption(String fname, String foname, String newName) throws AutoException {

		int x = findFeaturePos(fname);
		if (x != -1)
			features.get(x).updateFeatureOption(foname, newName);

	}

	public void updateFeatureOption(String fname, String foname, double newPrice) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features.get(x).updateFeatureOption(foname, newPrice);
	}

	// Add

	public void addFeature(String fname) {
		features.add(new Feature(fname));
		choices.add(null);

	}

	public void addFeatureOption(String fname, String foname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1) {
			features.get(x).addFeatureOption(foname);
		}
	}

	public void addFeatureOption(String fname, String foname, double price) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features.get(x).addFeatureOption(foname, price);

	}

	// Inner Class
	protected class Feature implements java.io.Serializable {
		private String name = "";
		private FeatureOption choice = null;
		private ArrayList<FeatureOption> featureOptions = new ArrayList<FeatureOption>();

		// constructors
		protected Feature() {
		}

		protected Feature(String n) {
			name = n;
		}

		// Getters
		protected String getName() {
			return name;
		}

		protected double getFeatureOptionPrice(String name) throws AutoException {
			int i = findFeatureOptionPos(name);
			return featureOptions.get(i).getPrice();
		}

		protected FeatureOption getFeatureOptionChoice() {
			return choice;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name + ": ");
			for (int i = 0; i < featureOptions.size(); i++) {
				sb.append(featureOptions.get(i).toString());
				sb.append(", ");
			}
			return sb.toString();
		}

		// Setters
		protected void setFeatureOptionChoice(String name) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				choice = featureOptions.get(x);
		}

		// Find
		protected int findFeatureOptionPos(String name) throws AutoException {
			try {
				for (int i = 0; i < featureOptions.size(); i++) {
					if (featureOptions.get(i).getName().contains(name)) {
						return i;
					}
				}
			} catch (NullPointerException e) {
				throw new AutoException(AutoException.AutoError.NULL_FEATUREOPTION_SIZE);
			}
			return -1;
		}

		// Delete
		public void deleteFeatureOption(String name) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1) {
				if (featureOptions.get(x) == choice)
					choice = null;
				featureOptions.remove(x);
			}
		}

		// Update
		public void updateFeatureOption(String name, String newName) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				featureOptions.get(x).update(newName);
		}

		public void updateFeatureOption(String name, double price) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				featureOptions.get(x).update(price);
		}

		// Add
		protected void addFeatureOption(String name) {
			featureOptions.add(new FeatureOption(name));
		}

		protected void addFeatureOption(String name, double price) {
			featureOptions.add(new FeatureOption(name, price));

		}

		// Inner Inner Class
		@SuppressWarnings("serial")
		protected class FeatureOption implements java.io.Serializable {
			private String name = "";
			private double price = 0;

			// Constructors
			protected FeatureOption() {

			}

			protected FeatureOption(String n) {
				name = n;
			}

			protected FeatureOption(String n, double p) {
				name = n;
				price = p;
			}

			// Getters
			protected String getName() {
				return name;
			}

			protected double getPrice() {
				return price;
			}

			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append(name + " ($" + price + ")");
				return sb.toString();
			}

			// Update
			protected void update(String newName) {
				name = newName;
			}

			protected void update(double newPrice) {
				price = newPrice;
			}

		}
	}
}
