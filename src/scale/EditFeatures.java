package scale;

import adapter.ProxyAutomobile;

//extending ProxyAutomobile allows EditFeatures to operate on the LHM without exposing it
public class EditFeatures extends ProxyAutomobile implements Runnable{
	
	int operation; 
	String [] param;
	String modelName; 
	
	public EditFeatures(String modelName, int operation, String [] param){
		super();
		this.modelName = modelName;
		this.operation = operation;
		this.param = param;
	}
	
	
	@SuppressWarnings({ "static-access", "static-access" })
	public void run(){
		System.out.println("~Initiating Thread~");
		
		switch(operation){
		case (1): updateFeature(modelName, param[0], param[1]);break;
		case (2): updateFeatureOptionName(modelName, param[0], param[1], param[2]); break;
		case (3): updateFeatureOptionPrice(modelName, param[0], param[1], Double.parseDouble(param[2])); break;
		default: break;
		}
	}
}
