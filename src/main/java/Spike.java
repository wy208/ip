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

        // Storing of tasks
        String[] tasks = new String[100];
        int taskCount = 0;

        // Echo function
        boolean isExit = false;
        while (!isExit) {
            line = in.nextLine().trim();

            System.out.println("  " + HORIZONTAL_LINE);

            if (line.equals("bye")) {
                System.out.println("  " + "Bye. Hope to see you again soon!");
                isExit = true;
            } else if (line.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("   " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = line;
                taskCount++;
                System.out.println("  added: " + line);
            }

            System.out.println("  " + HORIZONTAL_LINE);
        }
    }
}
