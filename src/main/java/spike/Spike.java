package spike;

/**
 * The main entry point for Spike chatbot.
 * Initialises user interface, storage and task list components.
 * Runs the main interaction loop
*/
public class Spike {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    public static void main(String[] args) {
        new Spike("./data/spike.txt").run();
    }

    public Spike(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.loadTaskFromFile());
        } catch (Exception e) {
            ui.showError("Error loading tasks. Starting with an empty list.");
            this.tasks = new TaskList();
        }
    }

    public void run() {
        storage.loadTaskFromFile();

        ui.greetUser();

        boolean isExit = false;

        while (!isExit) {
            String line = ui.readCommand();
            if (line.isEmpty()) {
                continue;
            }
            ui.printLine();

            try {
                Command c = Parser.parse(line);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();

            } catch (SpikeException e) {
                ui.showError(e.getMessage());
            }

            ui.printLine();
        }
    }
}