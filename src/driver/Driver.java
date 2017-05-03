package driver;
import adapter.*;

public class Driver {

	public static void main(String[] args) {
		
		CreateAuto a = new BuildAuto();
		UpdateAuto b = new BuildAuto();
		
		a.buildAuto("FordModel.txt");
		a.printAuto("Focus Wagon ZTW");
		
		b.updateFeatureOptionName("Focus Wagon ZTW", "Color", "Liquid Grey Clearcoat Metallic", "Pretty Pink");
		b.updateFeatureOptionPrice("Focus Wagon ZTW", "Color", "Pretty Pink", 335.20);
		a.printAuto("Focus Wagon ZTW");
			
	}

}
