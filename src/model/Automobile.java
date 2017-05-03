package model;

import java.lang.StringBuilder;

import exception.AutoException;

public class Automobile implements java.io.Serializable {
	String model;
	double basePrice;
	Feature[] features = null;

	// constructors
	public Automobile() {
	}

	public Automobile(String m, double bp) {
		model = m;
		basePrice = bp;
	}

	public Automobile(String m, double bp, int size) {
		model = m;
		basePrice = bp;
		features = new Feature[size];
		for (int i = 0; i < size; i++) {
			features[i] = new Feature();
		}
	}

	// getters
	public String getModel() throws AutoException {
		try{
		return model;
		}catch(NullPointerException e){
			throw new AutoException(AutoException.AutoError.MISSING_MODEL_NAME);
		}
	}
	
	public double getBasePrice() throws AutoException{
		try{
		return basePrice;
		}catch(NullPointerException e){
			throw new AutoException(AutoException.AutoError.MISSING_MODEL_PRICE);
		}
	}

	public String getFeature(String fname) throws AutoException {
		int i = findFeaturePos(fname);
		return features[i].toString();
	}

	public int getMaxNumFeatures() {
		return features.length;
	}

	public double getFeatureOptionPrice(String fname, String foname) throws AutoException {
		int i = findFeaturePos(fname);
		return features[i].getFeatureOptionPrice(foname);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Model: " + model + " Base Price: $" + basePrice + "\n");

		if (features != null) {
			sb.append("[Features: Feature Options] \n");
			for (int i = 0; i < features.length; i++) {
				if (features[i] != null) {
					sb.append(features[i].toString());
				}
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
			for (int i = 0; i < features.length; i++) {
				if (features[i] != null) {
					if (fname.equals(features[i].getName()))
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
			if (features[x].findFeatureOptionPos(foname) != -1)
				return true;
		}
		return false;
	}

	// Setters
	public void setModel(String name) {
		model = name;
	}

	public void setBasePrice(double d) {
		basePrice = d;
	}

	public void setFeaturesSize(int size) {
		features = new Feature[size];
	}

	public void setFeatureOptionSize(String fname, int size) throws AutoException {
		int x = findFeaturePos(fname);
		features[x].setFeatureOptionSize(size);
	}

	// Delete
	public void deleteFeature(String fname) throws AutoException {
		int x = findFeaturePos(fname);
		features[x] = null;
	}

	public void deleteFeatureOption(String fname, String foname) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features[x].deleteFeatureOption(foname);
	}

	// Update
	public void UpdateFeature(String fname, String newName) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features[x].name = newName;
	}

	public void UpdateFeatureOption(String fname, String foname, String newName) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features[x].updateFeatureOption(foname, newName);
	}

	public void UpdateFeatureOption(String fname, String foname, double newPrice) throws AutoException {
		int x = findFeaturePos(fname);
		if (x != -1)
			features[x].updateFeatureOption(foname, newPrice);
	}

	// Add
	private int isSpace() {
		for (int i = 0; i < features.length; i++) {
			if (features[i] == null)
				return i;
		}
		return -1;
	}

	public void addFeature(String fname) {
		int i = isSpace();
		if (i != -1) {
			features[i] = new Feature(fname);
		} else {
			System.out.println("Sorry, no space for new feature " + fname);
		}
	}

	public void addFeature(String fname, int foSize) {
		int i = isSpace();
		if (i != -1) {
			features[i] = new Feature(fname, foSize);
		} else {
			System.out.println("Sorry, no space for new feature " + fname);
		}
	}

	public void addFeatureOption(String fname, String foname) throws AutoException {
		int i = findFeaturePos(fname);
		if (i != -1) {
			boolean added = features[i].addFeatureOption(foname);
			if (added == false) {
				System.out.println("Sorry, no space for new feature option " + foname);
			}
		}

	}

	public void addFeatureOption(String fname, String foname, double price) throws AutoException {
		int i = findFeaturePos(fname);
		if (i != -1 && features[i].addFeatureOption(foname, price) == false) {
			System.out.println("Sorry, no space for new feature option " + foname);
		}

	}

	// Inner Class
	protected class Feature implements java.io.Serializable {
		private String name = "";
		private FeatureOption[] featureOptions = null;

		// constructors
		protected Feature() {
		}

		protected Feature(String n) {
			name = n;
		}

		protected Feature(String n, int size) {
			name = n;
			featureOptions = new FeatureOption[size];
			for (int i = 0; i < featureOptions.length; i++) {
				featureOptions[i] = null;
			}
		}

		// Getters
		protected String getName() {
			return name;
		}

		protected double getFeatureOptionPrice(String name) throws AutoException {
			int i = findFeatureOptionPos(name);
			return featureOptions[i].getPrice();
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name + ": ");
			if (featureOptions != null) {
				for (int i = 0; i < featureOptions.length; i++) {
					if (featureOptions[i] != null) {
						sb.append(featureOptions[i].toString());
						sb.append(", ");
					}
				}
			}
			return sb.toString();
		}

		// Setters
		protected void setFeatureOptionSize(int size) {
			featureOptions = new FeatureOption[size];
		}

		// Find
		protected int findFeatureOptionPos(String name) throws AutoException{
			try{
			for (int i = 0; i < featureOptions.length; i++) {
				if (featureOptions[i] != null) {
					if (name.equals(featureOptions[i].getName()))
						return i;
				}
			}
			}catch(NullPointerException e){
				throw new AutoException(AutoException.AutoError.NULL_FEATUREOPTION_SIZE);
			}
			return -1;
		}

		// Delete
		public void deleteFeatureOption(String name) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				featureOptions[x] = null;
		}

		// Update
		public void updateFeatureOption(String name, String newName) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				featureOptions[x].update(newName);
		}

		public void updateFeatureOption(String name, double price) throws AutoException {
			int x = findFeatureOptionPos(name);
			if (x != -1)
				featureOptions[x].update(price);
		}

		// Add
		protected boolean addFeatureOption(String name) {
			int i = isSpace();
			if (i != -1) {
				featureOptions[i] = new FeatureOption(name);
				return true;
			}
			return false;
		}

		protected boolean addFeatureOption(String name, double price) {
			int i = isSpace();
			if (i != -1) {
				featureOptions[i] = new FeatureOption(name, price);
				return true;
			}
			return false;
		}

		private int isSpace() {
			for (int i = 0; i < featureOptions.length; i++) {
				if (featureOptions[i] == null)
					return i;
			}
			return -1;
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
