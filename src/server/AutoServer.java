package server;

import java.util.Properties;

import model.Automobile;

public interface AutoServer {
	 public void buildAutoFromProperties(Properties props);
	 
	 public String getModels();
	 
	 public Automobile sendAutomobile(String modelName);
}
