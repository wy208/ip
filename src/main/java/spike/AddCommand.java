package spike;

public class AddCommand extends Command {
    private String line;
    private String type;

    public AddCommand(String type, String line) {
        this.type = type;
        this.line = line;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SpikeException {
        Task newTask = null;

        if (type.equals("todo")) {
            String description = line.substring(4).trim();
            if (description.isEmpty()) {
                throw new SpikeException("The description of a todo cannot be empty.");
            }
            newTask = new Todo(description);

        } else if (type.equals("deadline")) {
            int byIndex = line.indexOf("/by");

            if (byIndex == -1) {
                throw new SpikeException("Deadline format is invalid! Format: deadline <desc> /by <time>");
            }

            String description = line.substring(8, byIndex).trim();
            String by = line.substring(byIndex + 3).trim();

            if (description.isEmpty()) {
                throw new SpikeException("The description of a deadline cannot be empty.");
            }

            newTask = new Deadline(description, by);

        } else if (type.equals("event")) {
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

            newTask = new Event(description, from, to);
        }

        if (newTask != null) {
            tasks.addTask(newTask);
            System.out.println("   Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println("   Now you have " + tasks.getSize() + " tasks in the list");
            storage.saveTasksToFile(tasks.getTasks());
        }
    }
}
