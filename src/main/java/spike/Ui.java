package spike;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE = "----------------------------------------------------";
    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public String readCommand() {
        return in.nextLine().trim();
    }

    public void printLine() {
        System.out.println("   " + HORIZONTAL_LINE);
    }

    public void greetUser() {
        printLine();
        System.out.println("Hello! I'm Spike!");
        System.out.println("What can I do for you?");
        printLine();
    }

    public void printExitMessage() {
        System.out.println("  " + "Bye. Hope to see you again soon!");
    }

    public void showError(String message) {
        System.out.println("   OH NO!! " + message);
        System.out.println("   Type 'help' for instructions.");
    }

    public void printHelpMessage() {
        System.out.println("   Here are the commands you can use:");
        System.out.println("   - todo <description> : Adds a todo task.");
        System.out.println("   - deadline <description> /by <time> : Adds a deadline task.");
        System.out.println("   - event <description> /from <time> /to <time> : Adds a event task.");
        System.out.println("   - list : Shows all your tasks.");
        System.out.println("   - mark <task number> : Marks the task as done.");
        System.out.println("   - unmark <task number> : Marks the task as not done.");
        System.out.println("   - delete <task number> : Deletes the task.");
        System.out.println("   - bye : Exits the program.");


    }
}
