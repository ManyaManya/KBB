package exception;
import java.util.Scanner;

public class UtilExceptions {
	
	public String fixInput(int num){
		String input;
		Scanner scan = new Scanner(System.in);
		switch(num){
			case 20: System.out.print("Enter valid file name: ");
		}
		input = scan.nextLine();
		
		return input; 
	}
}
