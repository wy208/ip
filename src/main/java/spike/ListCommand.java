package spike;

/**
 * Represents a command to display all current tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            System.out.println("   You have no tasks in your list.");
            return; // Stop here so it doesn't try to print an empty list
        }
        System.out.println("   Here are the tasks in your list: ");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("   " + (i + 1) + ". " + tasks.getTask(i));
        }
    }
}
