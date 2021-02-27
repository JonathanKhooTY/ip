package ui;

import java.util.Locale;
import java.util.Scanner;
import static common.Messages.WELCOME_MESSAGE;

public class TextUI {

    private final Scanner in = new Scanner(System.in);

    public TextUI(){};

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public String getUserCommand() {
        System.out.print("What would you like to do? Enter command: ");
        String fullInputLine = in.nextLine();

        return fullInputLine.toLowerCase();
    }

    public void showToUser(String message) {
        System.out.println(message);
    }


}
