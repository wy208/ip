package spike;

/**
 * Represents a command to mark a specific task as done or not done.
 */
public class MarkCommand extends Command {
    private String line;
    private boolean isMark;

    public MarkCommand(String line, boolean isMark) {
        this.line = line;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SpikeException {
        try {
            String[] parts = line.split(" ");
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= tasks.getSize()) {
                throw new SpikeException("That task does not exist.");
            }

            if (isMark) {
                tasks.getTask(index).markAsDone();
                System.out.println("   OK, I've marked this task as not done yet:");
            } else {
                tasks.getTask(index).markAsNotDone();
                System.out.println("   OK, I've marked this task as not done yet:");
            }

            System.out.println("   " + tasks.getTask(index));
            storage.saveTasksToFile(tasks.getTasks());
        } catch (NumberFormatException e) {
            throw new SpikeException("   Enter a valid number please!");
        } catch (IndexOutOfBoundsException e) {
            throw new SpikeException("   Task number does not exist.");
        }
    }
}
