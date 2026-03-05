package spike;

/**
 * Represents an executable command from the user.
 */
public abstract class Command {
    /**
     * Executes the command and performs the corresponding action.
     *
     * @param tasks The list of tasks.
     * @param ui The user interface.
     * @param storage The storage component.
     * @throws SpikeException If an error occurs during execution.
     */
    public abstract void execute (TaskList tasks, Ui ui, Storage storage) throws SpikeException;

    public boolean isExit() {
        return false;
    }
}
