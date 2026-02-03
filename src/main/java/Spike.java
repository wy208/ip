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

            // User adds new Todo
            } else if (line.startsWith("todo")) {
                String description = line.substring(4).trim();
                tasks[taskCount] = new Todo(description);
                taskCount++;

                System.out.println("   Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount-1]);
                System.out.println("   Now you have " + taskCount + " tasks in the list");
            }

            //User adds new Deadline
            else if (line.startsWith("deadline")) {
                int byIndex = line.indexOf("/by");
                String description = line.substring(8, byIndex).trim();
                String by = line.substring(byIndex + 3).trim();

                tasks[taskCount] = new Deadline(description, by);
                taskCount++;

                System.out.println("   Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount-1]);
                System.out.println("   Now you have " + taskCount + " tasks in the list");

            //User adds new Event
            } else if (line.startsWith("event")) {
                int fromIndex = line.indexOf("/from");
                int toIndex = line.indexOf("/to");
                String description = line.substring(5, fromIndex).trim();
                String from = line.substring(fromIndex + 5, toIndex).trim();
                String to = line.substring(toIndex + 3).trim();

                tasks[taskCount] = new Event(description, from, to);
                taskCount++;

                System.out.println("   Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount-1]);
                System.out.println("   Now you have " + taskCount + " tasks in the list");
            } else {
                System.out.println("   Sorry, I don't recognise that command!!");
            }

            System.out.println("  " + HORIZONTAL_LINE);
        }
    }
}
