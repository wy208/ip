import java.util.Scanner;

public class Spike {
    public static void main(String[] args) {
        // Greeting the user
        String HORIZONTAL_LINE = "---------------------------------------------------";
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Spike!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        Scanner in = new Scanner(System.in);
        String line;

        // Echo function
        boolean isExit = false;
        while (!isExit) {
            line = in.nextLine();
            System.out.println("  " + HORIZONTAL_LINE);

            if (line.equals("bye")) {
              System.out.println("  " + "Bye. Hope to see you again soon!");
              isExit = true;
            } else {
                System.out.println("  " + line);
            }
            
            System.out.println("  " + HORIZONTAL_LINE);
        }
    }
}
