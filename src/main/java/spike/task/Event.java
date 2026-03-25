package spike.task;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The starting date and/or time of the event.
     * @param to The ending date and/or time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return("[E]" + super.toString() + " (from: " + from + " to: " + to + ")");
    }

    /**
     * Gives the file format in which will appear in txt file.
     *
     * @return String of formatted line.
     */
    @Override
    public String toFileFormat() {
        return "Event    " + super.toFileFormat() + " | from: " + from + " | to: " + to;
    }
}
