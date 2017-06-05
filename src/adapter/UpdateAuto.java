package adapter;

public interface UpdateAuto {

	void updateFeatureOptionName(String modelName, String featureName, String foName, String newName);

	void updateFeatureOptionPrice(String modelName, String featureName, String foName, double newPrice);
	
	void updateFeature(String modelName, String featureName, String newName);
}
