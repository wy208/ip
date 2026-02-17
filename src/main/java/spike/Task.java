package spike;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

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
