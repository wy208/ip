package spike.task;

/**
 * Represents a task without any specific date or time constraints.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return("[T]" + super.toString());
    }

    @Override
    public String toFileFormat() {
        return "Todo     " + super.toFileFormat();
    }
}
