package spike.exception;

/**
 * Represents exceptions specific to the Spike application.
 * Thrown when the user inputs an invalid command or format.
 */
public class SpikeException extends Exception {
    public SpikeException(String message) {
        super(message);
    }
}
