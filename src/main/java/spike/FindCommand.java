package spike;

import java.util.ArrayList;

public class FindCommand extends Command {
    private String line;

    public FindCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SpikeException{
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
