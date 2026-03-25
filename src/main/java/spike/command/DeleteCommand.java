package spike.command;

import spike.exception.SpikeException;
import spike.storage.Storage;
import spike.task.TaskList;
import spike.ui.Ui;
import spike.task.Task;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private String line;

    public DeleteCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SpikeException {
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
}
