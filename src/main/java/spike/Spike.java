package spike;

import java.util.ArrayList;

public class Spike {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    public static void main(String[] args) {
        new Spike("./data/spike.txt").run();
    }

    public Spike(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.loadTaskFromFile());
        } catch (Exception e) {
            ui.showError("Error loading tasks. Starting with an empty list.");
            this.tasks = new TaskList();
        }
    }

    public void run() {
        storage.loadTaskFromFile();

        ui.greetUser();

        boolean isExit = false;

        while (!isExit) {
            String line = ui.readCommand();
            if (line.isEmpty()) {
                continue;
            }
            ui.printLine();

            try {
                if (line.equals("bye")) {
                    isExit = true;
                    ui.printExitMessage();
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
                } else if(line.startsWith("help")) {
                    ui.printHelpMessage();
                } else {
                    throw new SpikeException("i don't understand!?");
                }
            } catch (SpikeException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("   Enter a valid number please!");
            } catch (IndexOutOfBoundsException e) {
                ui.showError("   Task number does not exist.");
            }

            ui.printLine();
        }
    }

    private void deleteTask(String line) throws SpikeException {
        try {
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                throw new SpikeException("Please specify task number to delete.");
            }

            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= tasks.getSize()) {
                throw new SpikeException("Task number does not exist");
            }

            Task removedTask = tasks.deleteTask(index);

            System.out.println("   Noted. I've removed this task.");
            System.out.println("   " + removedTask);
            System.out.println("   Now you have " + tasks.getSize() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new SpikeException("Please enter a valid number to delete.");
        }
    }

    private void addEvent(String line) throws SpikeException {
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
        tasks.addTask(newTask);

        printTaskWithTaskCount(newTask);
        storage.saveTasksToFile(tasks.getTasks());
    }

    private void addDeadline(String line) throws SpikeException {
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
        tasks.addTask(newTask);

        printTaskWithTaskCount(newTask);
        storage.saveTasksToFile(tasks.getTasks());
    }

    private void addTodo(String line) throws SpikeException {
        String description = line.substring(4).trim();
        if (description.isEmpty()) {
            throw new SpikeException("The description of a todo cannot be empty.");
        }

        Task newTask = new Todo(description);
        tasks.addTask(newTask);

        printTaskWithTaskCount(newTask);
        storage.saveTasksToFile(tasks.getTasks());
    }

    private void unmarkTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (index < 0 || index >= tasks.getSize()) {
            throw new SpikeException("That task does not exist.");
        }

        tasks.getTask(index).markAsNotDone();

        System.out.println("   OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks.getTask(index));

        storage.saveTasksToFile(tasks.getTasks());
    }

    private void markTask(String line) throws SpikeException {
        String[] parts = line.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;

        if (index < 0 || index >= tasks.getSize()) {
            throw new SpikeException("That task does not exist.");
        }

        tasks.getTask(index).markAsDone();

        System.out.println("   Nice! I've marked this task as done:");
        System.out.println("   " + tasks.getTask(index));

        storage.saveTasksToFile(tasks.getTasks());
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("   You have no tasks.");
        } else {
            System.out.println("   Here are the tasks in your list: ");
            int count = 1;
            for (Task t: tasks.getTasks()) {
                System.out.println("   " + count + "." + t);
                count++;
            }
        }

        storage.saveTasksToFile(tasks.getTasks());
    }

    private void printTaskWithTaskCount(Task task) {
        System.out.println("   Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println("   Now you have " + tasks.getSize() + " tasks in the list");
    }
}
