package spike.storage;

import spike.task.Deadline;
import spike.task.Event;
import spike.task.Task;
import spike.task.Todo;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the loading and saving of task data to a file on the hard disk.
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the tasks from the save file into an ArrayList.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws Exception If there is an issue reading the file.
     */
    public ArrayList<Task> loadTaskFromFile() {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            File file = new File("./data/spike.txt");
            if (!file.exists()) {
                return loadedTasks;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                parseTaskFromLine(fileScanner, loadedTasks);
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return loadedTasks;
    }

    private static void parseTaskFromLine(Scanner fileScanner, ArrayList<Task> loadedTasks) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(" \\| ");

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("done");
        String description = parts[2];

        Task task = null;

        switch (type) {
        case "Todo":
            task = new Todo(description);
            break;
        case "Deadline":
            String by = parts[3].replace("by:", "").trim();
            task = new Deadline(description, by);
            break;
        case "Event":
            String from = parts[3].replace("from:", "").trim();
            String to = parts[4].replace("to:", "").trim();
            task = new Event(description, from, to);
            break;
        }

        if (task != null) {
            if(isDone) {
                task.markAsDone();
            }
            loadedTasks.add(task);
        }
    }

    /**
     * Saves the current list of tasks to the hard disk.
     *
     * @param tasks The ArrayList of tasks to be saved.
     */
    public void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File directory = new File("./data");
            if (!directory.exists() && !directory.mkdirs()) {
                System.out.println("Error: Failed to create data directory.");
                return; 
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
}
