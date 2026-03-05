package spike;

public class Parser {
    public static Command parse(String line) throws SpikeException {
        if (line.equals("bye")) {
            return new ExitCommand();
        } else if (line.equals("list")) {
            return new ListCommand();
        } else if (line.startsWith("mark")) {
            return new MarkCommand(line, true);
        } else if (line.startsWith("unmark")) {
            return new MarkCommand(line, false);
        } else if (line.startsWith("todo")) {
            return new AddCommand("todo", line);
        } else if (line.startsWith("deadline")) {
            return new AddCommand("deadline", line);
        } else if (line.startsWith("event")) {
            return new AddCommand("event", line);
        } else if (line.startsWith("delete")) {
            return new DeleteCommand(line);
        } else if(line.startsWith("help")) {
            return new HelpCommand();
        } else {
            throw new SpikeException("i don't understand!?");
        }
    }
}
