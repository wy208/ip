package spike.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents the list of tasks managed by the application.
 * Contains operations to add, delete, retrieve, and search for tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index The 0-based index of the task to delete.
     * @return The deleted task.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Allows users to find a task within an ArrayList
     *
     * @param keyword The word that we are finding
     * @return An ArrayList of tasks containing keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.toString().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
