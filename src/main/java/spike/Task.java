package spike;

/**
 * Task with a description and a completion status.
 * This is the base class for more task types like Todo, Deadline and Event.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is marked as not done by default.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon indicating whether the task is done.
     *
     * @return "X" if the task is done, or a blank space " " if it is not.
     */
    public String getStatusIcon() {
        if (!isDone) {
            return (" ");
        }
        return ("X");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }

    public String toFileFormat() {
        String status = "not done";
        if(isDone) {
            status = "  done  ";
        }

        return String.format("| " + status + " | " + description);
    }

}
