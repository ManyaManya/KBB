package client;

import java.util.Scanner;

import exception.AutoException;
import model.Automobile;

public class SelectCarOption {
	public SelectCarOption() {
	}

	public void displayOptions() {
		System.out.println("What would you like to do?\n" + "~Upload Properties Model\n" + "~See Available Models\n"
				+ "~See Model Specs\n"  + "~Create Auto\n" + "~Exit");

	}

	public String userInput() {
		String input = null;
		System.out.print("> ");
		Scanner scanner = new Scanner(System.in);
		input = scanner.nextLine();
		return input;
	}

	public void createAnAuto(Automobile a) {
		String feature = null;
		String choice = null;
		for (int i = 0; i < a.getNumOfFeatures(); i++) {
			System.out.println("Feature: \n");
			feature = userInput();
			System.out.println("Selection: \n");
			choice = userInput();
			try {
				a.setFeatureOptionChoice(feature, choice);
			} catch (AutoException e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("Your Automobile: \n" + a.getFeatureOptionChoices());
		} catch (AutoException e) {
			e.printStackTrace();
		}
	}

}
