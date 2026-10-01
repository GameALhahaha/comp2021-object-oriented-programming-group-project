package hk.edu.polyu.comp.comp2021.clevis.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles logging of commands to both HTML and plain TXT files.
 */
public class Logger {
    private final String htmlPath;
    private final String txtPath;
    private int operationIndex = 1;

    /**
     * Constructs a Logger and initializes the log files.
     * @param htmlPath The path to the HTML log file.
     * @param txtPath The path to the TXT log file.
     * @throws IOException if an I/O error occurs during file initialization.
     */
    public Logger(String htmlPath, String txtPath) throws IOException {
        this.htmlPath = htmlPath;
        this.txtPath = txtPath;
        clearTxtLog();
        initializeHtmlLog();
    }

    /**
     * Initializes the HTML log file with the table header.
     * @throws IOException if an I/O error occurs.
     */
    private void clearTxtLog() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath, false));
    }

    private void initializeHtmlLog() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlPath, false))) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head><title>Clevis Command Log</title></head>\n");
            writer.write("<body>\n");
            writer.write("<h1>Clevis Command Log</h1>\n");
            writer.write("<table border=\"1\">\n");
            writer.write("<tr><th>Index</th><th>Command</th></tr>\n");
        }
    }

    /**
     * Logs the output of a query command to both files.
     * @param output The output string to log.
     */
    public void logOutput(String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath, true))) {
            writer.write("Output: " + output + "\n");
        } catch (IOException e) {
            System.err.println("Error writing output to TXT log file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlPath, true))) {
            writer.write(String.format("<tr><td colspan=\"2\">Output: %s</td></tr>\n", output.replace("\n", "<br>")));
        } catch (IOException e) {
            System.err.println("Error writing output to HTML log file: " + e.getMessage());
        }
    }

    /**
     * Logs a command to both files.
     * @param command The command string to log.
     */
    public void logCommand(String command) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath, true))) {
            writer.write(command + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to TXT log file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlPath, true))) {
            writer.write(String.format("<tr><td>%d</td><td>%s</td></tr>\n", operationIndex, command));
            operationIndex++;
        } catch (IOException e) {
            System.err.println("Error writing to HTML log file: " + e.getMessage());
        }
    }

    /**
     * Closes the HTML log file by adding the closing tags.
     */
    public void closeHtmlLog() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlPath, true))) {
            writer.write("</table>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
        } catch (IOException e) {
            System.err.println("Error closing HTML log file: " + e.getMessage());
        }
    }
}
