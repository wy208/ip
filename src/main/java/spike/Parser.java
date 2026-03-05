package spike;

/**
 * Represents the component responsible for making sense of user input.
 */
public class Parser {
    /**
     * Parses the raw user input string and returns the corresponding Command object.
     *
     * @param line The full command string typed by the user.
     * @return A Command object representing the user's intent.
     * @throws SpikeException If the user input is invalid or not recognized.
     */
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
        } else if (line.startsWith("find")) {
            return new FindCommand(line);
        } else if(line.startsWith("help")) {
            return new HelpCommand();
        } else {
            throw new SpikeException("i don't understand!?");
        }
    }
}
