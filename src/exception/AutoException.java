package exception;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class AutoException extends Exception {
	AutoError err;

	public AutoException() {

	}

	public AutoException(AutoError e) {
		super();
		this.err = e;
		log();
	}

	public enum AutoError {
		// model errors start with 1
		NULL_FEATURES_SIZE(10, "features size undeclared"), NULL_FEATUREOPTION_SIZE(11,
				"featureOptions size undeclared"),
		// file errors start with 2
		FILENAME_ERROR(20, "file name invalid"), MISSING_MODEL_NAME(21,
				"model price not found"), MISSING_MODEL_PRICE(22, "model price not found");

		private int errorNum;
		private String errorMsg;

		private AutoError(int errorNum, String errorMsg) {
			this.errorNum = errorNum;
			this.errorMsg = errorMsg;
		}

		public int getErrorNum() {
			return errorNum;
		}

		public String getErrorMsg() {
			return errorMsg;
		}
	}

	// possible use in proxy
	public void fix(int num) {
		ModelExceptions me = new ModelExceptions();
		UtilExceptions ioe = new UtilExceptions();

		switch (num) {
		case 20:
			ioe.fixInput(num);
			break;
		}
	}

	public void fix() {
		ModelExceptions me = new ModelExceptions();
		UtilExceptions ioe = new UtilExceptions();

		switch (err) {
		case FILENAME_ERROR:
			ioe.fixInput(err.errorNum);
			break;
		}
	}

	public void log() {
		FileWriter fo;
		BufferedWriter out;
		String ts = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(Calendar.getInstance().getTime());
		try {
			fo = new FileWriter("ErrorLog.txt");
			out = new BufferedWriter(fo);
			out.write(ts.toString() + " Error Number: " + err.getErrorNum() + " Error Mssg: " + err.getErrorMsg());
			out.close();
			fo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error in log" + e.toString());
		} catch (IOException e) {
			System.out.println("Error in log" + e.toString());

		}
		System.out.println(ts + " Error Number: " + err.getErrorNum() + " Error Mssg: " + err.getErrorMsg());

	}
}
