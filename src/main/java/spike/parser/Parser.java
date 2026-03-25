package spike.parser;

import spike.command.*;
import spike.exception.SpikeException;

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

        String[] parts = line.split(" ", 2);
        String commandWord = parts[0];

        if (commandWord.equals("bye")) {
            return new ExitCommand();
        } else if (commandWord.equals("list")) {
            return new ListCommand();
        } else if (commandWord.equals("mark")) {
            return new MarkCommand(line, true);
        } else if (commandWord.equals("unmark")) {
            return new MarkCommand(line, false);
        } else if (commandWord.equals("todo")) {
            return new AddCommand("todo", line);
        } else if (commandWord.equals("deadline")) {
            return new AddCommand("deadline", line);
        } else if (commandWord.equals("event")) {
            return new AddCommand("event", line);
        } else if (commandWord.equals("delete")) {
            return new DeleteCommand(line);
        } else if (commandWord.equals("find")) {
            return new FindCommand(line);
        } else if(commandWord.equals("help")) {
            return new HelpCommand();
        } else {
            throw new SpikeException("i don't understand!?");
        }
    }
}
