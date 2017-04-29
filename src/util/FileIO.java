package util;
import model.Automobile; 
import java.io.*;
public class FileIO {
	//constructor
	public FileIO(){}
	
	//build Automobile
	public Automobile buildAutomobile(String filename){
		Automobile am = new Automobile(); 
		try{
			FileReader file = new FileReader(filename);
				int count = 0;
				BufferedReader buff = new BufferedReader(file);
				boolean eof = false;
				String line = buff.readLine();
				String [] tokenM = line.split(",");
				am.setModel(tokenM[0]);
				am.setBasePrice( Double.parseDouble(tokenM[1]));
				line = buff.readLine();
				am.setFeaturesSize(Integer.parseInt(line));
				while(!eof && count < am.getMaxNumFeatures()){
					line = buff.readLine();
					String[] tokenF = line.split(",");
					am.addFeature(tokenF[1], Integer.parseInt(tokenF[0]));
					for(int i = 2; i < tokenF.length; i = i+2){
						am.addFeatureOption(tokenF[1], tokenF[i], Double.parseDouble(tokenF[i+1]));
					}
					if(line == null)
						eof = true;
					count++;
				}
				buff.close();
		} catch(IOException e){
			System.out.println("Error -- "+ e.toString());
		}
		
		return am;
	}
	
	
	public void serializeAutomobile(Automobile a, String file){
		try{
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fo);
		out.writeObject(a);
		out.close();
		fo.close();
		System.out.println("Object saved");
		}catch(IOException e){
			System.out.println("Error --" + e.toString());
		}
	}
	
	public void serializeAutomobiles(Automobile [] a, String file){
		try{
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fo);
		out.writeObject(a);
		out.close();
		fo.close();
		System.out.println(a.length+ " Object(s) saved");
		}catch(IOException e){
			System.out.println("Error --" + e.toString());
		}
	}
	
	public Automobile deserializeAutomobile(String file){
		Automobile  a = null;
		try{
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fi);
			a = (Automobile) in.readObject();
			in.close();
			fi.close();
		}catch(Exception e){
			System.out.println("Error --" + e.toString());
			System.out.println("Note: file not serialized as a single object. "
		            + "Try \"deserializeAutomobiles\" instead.");
			System.exit(1);
		}
		return a;
	}
	
	public Automobile[] deserializeAutomobiles(String file){
		Automobile [] a = null;
		try{
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fi);
			a = (Automobile []) in.readObject();
			in.close();
			fi.close();
		
		}catch(Exception e){
			System.out.println("Error --" + e.toString());
			System.out.println("Note: file not serialized as an array of objects. "
					            + "Try \"deserializeAutomobile\" instead.");
			System.exit(1);
		}
		return a;
	}
	
}





