package util;

import java.io.*;
import java.util.Properties;

import exception.AutoException;
import model.Automobile;

public class FileIO {
	private String modelKey;
	// constructor
	public FileIO() {
	}

	public boolean openFile (String name){ 
		boolean works = true;
		try{
		FileReader fr = new FileReader(name);
		}catch(FileNotFoundException e){
			works = false;
		}
		return works;
	}
	
	public String getModelKey(){
		return modelKey;
	}
	
	
	// build Automobile
	public Automobile buildAutoCSV(String filename) throws AutoException {
		FileReader file;
		BufferedReader buff;
		
		Automobile am = new Automobile();
		try {
			file = new FileReader(filename);
		}catch(FileNotFoundException e){
			throw new AutoException(AutoException.AutoError.FILENAME_ERROR);
		}
			
		try{
			buff = new BufferedReader(file);
			boolean eof = false;
			String line = buff.readLine();
			String[] tokenM = line.split(",");
			am.setMake(tokenM[0]);
			modelKey = tokenM[1];
			am.setModel(modelKey);
			am.setBasePrice(Double.parseDouble(tokenM[2]));
			while (!eof) {
				line = buff.readLine();
				if(line != null){
					String[] tokenF = line.split(",");
					am.addFeature(tokenF[0]);
					for (int i = 1; i < tokenF.length; i = i + 2) {
						am.addFeatureOption(tokenF[0], tokenF[i], Double.parseDouble(tokenF[i + 1]));
					}
				}else{
					eof = true;	
				}
			}
			buff.close();
		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}

		return am;
	}
	
	public Properties buildAutoProperties(String filename){
		Properties props = new Properties();
		FileInputStream in =  null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read Properties file: " + filename);
		}
		try {
			props.load(in);
		} catch (IOException e) {
			System.out.println("Could not lead file: " + filename);
		}
		return props;
	}

	public void serializeAutomobile(Automobile a, String file) {
		try {
			FileOutputStream fo = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(a);
			out.close();
			fo.close();
			System.out.println("Object saved");
		} catch (IOException e) {
			System.out.println("Error --" + e.toString());
		}
	}

	public void serializeAutomobiles(Automobile[] a, String file) {
		try {
			FileOutputStream fo = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(a);
			out.close();
			fo.close();
			System.out.println(a.length + " Object(s) saved");
		} catch (IOException e) {
			System.out.println("Error --" + e.toString());
		}
	}

	public Automobile deserializeAutomobile(String file) {
		Automobile a = null;
		try {
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fi);
			a = (Automobile) in.readObject();
			in.close();
			fi.close();
		} catch (Exception e) {
			System.out.println("Error --" + e.toString());
			System.out.println(
					"Note: file not serialized as a single object. " + "Try \"deserializeAutomobiles\" instead.");
			System.exit(1);
		}
		return a;
	}

	public Automobile[] deserializeAutomobiles(String file) {
		Automobile[] a = null;
		try {
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fi);
			a = (Automobile[]) in.readObject();
			in.close();
			fi.close();

		} catch (Exception e) {
			System.out.println("Error --" + e.toString());
			System.out.println(
					"Note: file not serialized as an array of objects. " + "Try \"deserializeAutomobile\" instead.");
			System.exit(1);
		}
		return a;
	}

}
