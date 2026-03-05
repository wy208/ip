package spike;

/**
 * Represents a command to exit the chatbot application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printExitMessage();
    }

    /**
     * Indicates whether this command should terminate the application's main loop.
     *
     * @return true, since this is the exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
