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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        boolean isExit = false;
        while (!isExit) {
            line = in.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            System.out.println("  " + HORIZONTAL_LINE);

            // User exits
            if (line.equals("bye")) {
                System.out.println("  " + "Bye. Hope to see you again soon!");
                isExit = true;
            // User lists tasks
            } else if (line.equals("list")) {
                System.out.println("   Here are the tasks in your list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("   " + (i + 1) + ". " + tasks[i]);
                }
            // User marks task as done
            } else if (line.startsWith("mark")) {
                String[] parts = line.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks[index].markAsDone();
                System.out.println("   Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index]);
            // User marks task as not done
            } else if (line.startsWith("unmark")) {
                String[] parts = line.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks[index].markAsNotDone();
                System.out.println("   OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index]);
            // User adds new task
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println("  Added: " + line);
            }

            System.out.println("  " + HORIZONTAL_LINE);
        }
    }
}
