package spike;

/**
 * Represents a task that needs to be done before a specific date or time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The date and/or time by which the task needs to be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return("[D]" + super.toString() + " (by: " + by + ")");
    }

    @Override
    public String toFileFormat() {
        return "Deadline " + super.toFileFormat() + " | by: " + by;
    }
}
