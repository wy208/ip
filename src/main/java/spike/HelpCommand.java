package spike;

/**
 * Represents a command to display all available commands to users
 * with explanation of each command function.
 */
public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printHelpMessage();
    }
}
