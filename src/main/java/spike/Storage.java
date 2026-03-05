package spike;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTaskFromFile() {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            File file = new File("./data/spike.txt");
            if (!file.exists()) {
                return loadedTasks;
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
                    loadedTasks.add(task);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return loadedTasks;
    }

    public void saveTasksToFile(ArrayList<Task> tasks) {
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
}
