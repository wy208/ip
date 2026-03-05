package spike;

public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printHelpMessage();
    }
}
