package spike.command;

import spike.exception.SpikeException;
import spike.storage.Storage;
import spike.task.TaskList;
import spike.ui.Ui;
import spike.task.Task;

import java.util.ArrayList;

/**
 * Represents a command to search for tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private String line;

    /**
     * Constructs a new FindCommand with the specified search keyword.
     *
     * @param line The keyword to search for in the task list.
     */
    public FindCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SpikeException {
        String keyword = line.substring(4).trim();

        if (keyword.isEmpty()) {
            throw new SpikeException("What do you want to find? (e.g., find book)");
        }

        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);

        if(matchingTasks.isEmpty()) {
            System.out.println("   There are no matching tasks.");
            return;
        }

        System.out.println("   Here are the matching tasks:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println("   " + (i + 1) + "." + matchingTasks.get(i));
        }
    }
}
