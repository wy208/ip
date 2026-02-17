package spike;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Spike {
    private static final String HORIZONTAL_LINE = "----------------------------------------------------";

    private static final ArrayList<Task> tasks = new ArrayList<>();

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
                } else if (line.startsWith("delete")) {
                    deleteTask(line);
                } else {
                    throw new SpikeException("i don't understand!?");
                }
            } catch (SpikeException e) {
                System.out.println("   OH NO!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("   Enter a valid number please!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("   Task number does not exist.");
            }

            printLine();
        }
    }

    private static void deleteTask(String line) throws SpikeException {
        try {
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                throw new SpikeException("Please specify task number to delete.");
            }

            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new SpikeException("Task number does not exist");
            }

            Task removedTask = tasks.remove(index);

            System.out.println("   Noted. I've removed this task.");
            System.out.println("   " + removedTask);
            System.out.println("   Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new SpikeException("Please enter a valid number to delete.");
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
                    }
                    tasks.add(task);
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
            for (Task t: tasks) {
                writer.write(t.toFileFormat() + System.lineSeparator());
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
            throw new SpikeException("Event format invalid! Format: event <desc> /from <time> /to <time>");
        }

        String description = line.substring(5, fromIndex).trim();
        String from = line.substring(fromIndex + 5, toIndex).trim();
        String to = line.substring(toIndex + 3).trim();

        if (description.isEmpty()) {
            throw new SpikeException("The description of an event cannot be empty.");
        }

        Task newTask = new Event(description, from, to);
        tasks.add(newTask);

        printTaskWithTaskCount(newTask);
        saveTasksToFile();
    }

    private static void addDeadline(String line) throws SpikeException {
        int byIndex = line.indexOf("/by");

        if (byIndex == -1) {
            throw new SpikeException("Deadline format is invalid! Format: deadline <desc> /by <time>");
        }

        String description = line.substring(8, byIndex).trim();
        String by = line.substring(byIndex + 3).trim();

        if (description.isEmpty()) {
            throw new SpikeException("The description of a deadline cannot be empty.");
        }

        Task newTask = new Deadline(description, by);
        tasks.add(newTask);

        printTaskWithTaskCount(newTask);
        saveTasksToFile();
    }

    private static void addTodo(String line) throws SpikeException {
        String description = line.substring(4).trim();
        if (description.isEmpty()) {
            throw new SpikeException("The description of a todo cannot be empty.");
        }

        Task newTask = new Todo(description);
        tasks.add(newTask);

        printTaskWithTaskCount(newTask);
        saveTasksToFile();
    }

    private static void unmarkTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new SpikeException("That task does not exist.");
        }

        tasks.get(index).markAsNotDone();

        System.out.println("   OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks.get(index));

        saveTasksToFile();
    }

    private static void markTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new SpikeException("That task does not exist.");
        }

        tasks.get(index).markAsDone();

        System.out.println("   Nice! I've marked this task as done:");
        System.out.println("   " + tasks.get(index));

        saveTasksToFile();
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("   You have no tasks.");
        } else {
            System.out.println("   Here are the tasks in your list: ");
            int count = 1;
            for (Task t: tasks) {
                System.out.println("   " + count + "." + t);
                count++;
            }
        }

        saveTasksToFile();
    }

    private static void printExitMessage() {
        System.out.println("  " + "Bye. Hope to see you again soon!");
    }

    private static void printLine() {
        System.out.println("  " + HORIZONTAL_LINE);
    }

    private static void printTaskWithTaskCount(Task task) {
        System.out.println("   Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println("   Now you have " + tasks.size() + " tasks in the list");
    }

    private static void greetUser() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Spike!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);
    }
}
