package spike;

public abstract class Command {
    public abstract void execute (TaskList tasks, Ui ui, Storage storage) throws SpikeException;

    public boolean isExit() {
        return false;
    }
}
