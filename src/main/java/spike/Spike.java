package spike;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Spike {
    private static final String HORIZONTAL_LINE = "---------------------------------------------------";
    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        loadTaskFromFile();

        greetUser();

        Scanner in = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            printLine();

            try {
                if (line.equals("bye")) {
                    isExit = true;
                    printExitMessage();
                } else if (line.equals("list")) {
                    listTasks();
                } else if (line.startsWith("mark")) {
                    markTask(line);
                } else if (line.startsWith("unmark")) {
                    unmarkTask(line);
                } else if (line.startsWith("todo")) {
                    addTodo(line);
                } else if (line.startsWith("deadline")) {
                    addDeadline(line);
                } else if (line.startsWith("event")) {
                    addEvent(line);
                } else {
                    throw new SpikeException("i don't understandd!?");
                }
            } catch (SpikeException e) {
                System.out.println("   OH NO!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("   Enter a valid number pleaasssse!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("   spike.Task number does not exist");
            }

            printLine();
        }
    }

    private static void loadTaskFromFile() {
        try {
            File file = new File("./data/spike.txt");
            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");

                String type = parts[0];
                boolean isDone = parts[1].equals("done");
                String description = parts[2];

                Task task = null;

                switch (type) {
                case "Todo":
                    task = new Todo(description);
                    break;
                case "Deadline":
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "Event":
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                }

                if (task != null) {
                    if(isDone) {
                        task.markAsDone();
                        tasks[taskCount] = task;
                        taskCount++;
                    }
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private static void saveTasksToFile() {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    System.out.println("Error: Failed to create data directory.");
                }
            }

            FileWriter writer = new FileWriter("./data/spike.txt");
            for (int i = 0; i < taskCount; i++) {
                writer.write(tasks[i].toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file " + e.getMessage());
        }
    }

    private static void addEvent(String line) throws SpikeException {
        int fromIndex = line.indexOf("/from");
        int toIndex = line.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            throw new SpikeException("spike.Event format is invalid. Use: event <desc> /from <time> /to <time>");
        }

        String description = line.substring(5, fromIndex).trim();
        String from = line.substring(fromIndex + 5, toIndex).trim();
        String to = line.substring(toIndex + 3).trim();

        if (description.isEmpty()) {
            throw new SpikeException("The description of an event cannot be empty.");
        }

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;

        printTaskWithTaskCount();
        saveTasksToFile();
    }

    private static void addDeadline(String line) throws SpikeException {
        int byIndex = line.indexOf("/by");

        if (byIndex == -1) {
            throw new SpikeException("spike.Deadline format is invalid. Use: deadline <desc> /by <time>");
        }

        String description = line.substring(8, byIndex).trim();
        String by = line.substring(byIndex + 3).trim();

        if (description.isEmpty()) {
            throw new SpikeException("The description of a deadline cannot be empty.");
        }

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;

        printTaskWithTaskCount();
        saveTasksToFile();
    }

    private static void addTodo(String line) throws SpikeException {
        if (line.length() <= 4) {
            throw new SpikeException("The description of a todo cannot be empty.");
        }
        String description = line.substring(4).trim();
        if (description.isEmpty()) {
            throw new SpikeException("The description of a todo cannot be empty.");
        }

        tasks[taskCount] = new Todo(description);
        taskCount++;

        printTaskWithTaskCount();
        saveTasksToFile();
    }

    private static void unmarkTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (tasks[index] == null) {
            throw new SpikeException("That task does not exist.");
        }

        tasks[index].markAsNotDone();

        System.out.println("   OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[index]);

        saveTasksToFile();
    }

    private static void markTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (tasks[index] == null) {
            throw new SpikeException("That task does not exist.");
        }

        tasks[index].markAsDone();

        System.out.println("   Nice! I've marked this task as done:");
        System.out.println("   " + tasks[index]);

        saveTasksToFile();
    }

    private static void listTasks() {
        System.out.println("   Here are the tasks in your list: ");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("   " + (i + 1) + ". " + tasks[i]);
        }

        saveTasksToFile();
    }

    private static void printExitMessage() {
        System.out.println("  " + "Bye. Hope to see you again soon!");
    }

    private static void printLine() {
        System.out.println("  " + HORIZONTAL_LINE);
    }

    private static void printTaskWithTaskCount() {
        System.out.println("   Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println("   Now you have " + taskCount + " tasks in the list");
    }

    private static void greetUser() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm spike.Spike!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);
    }
}
