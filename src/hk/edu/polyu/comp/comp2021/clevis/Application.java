package hk.edu.polyu.comp.comp2021.clevis;

import hk.edu.polyu.comp.comp2021.clevis.controller.CommandParser;
import hk.edu.polyu.comp.comp2021.clevis.controller.ClevisController;
import hk.edu.polyu.comp.comp2021.clevis.model.ClevisModel;
import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;
import hk.edu.polyu.comp.comp2021.clevis.view.Logger;
import hk.edu.polyu.comp.comp2021.clevis.view.ClevisView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the Command Line Vector Graphics Software (Clevis).
 * Handles application initialization, command loop, and logging setup.
 */
public class Application {

    /**
     * Main method to start the Clevis application.
     * @param args Command line arguments for log file paths.
     */
    public static void main(String[] args) {
        String htmlLogPath = null;
        String txtLogPath = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-html") && i + 1 < args.length) {
                htmlLogPath = args[i + 1];
            } else if (args[i].equals("-txt") && i + 1 < args.length) {
                txtLogPath = args[i + 1];
            }
        }

        if (htmlLogPath == null || txtLogPath == null) {
            System.err.println("Error: Missing required log file paths.");
            System.err.println("Usage: java hk.edu.polyu.comp.comp2021.clevis.Application -html <html_path> -txt <txt_path>");
            return;
        }

        Logger logger = null;
        final ClevisView view;
        try {
            logger = new Logger(htmlLogPath, txtLogPath);
        } catch (IOException e) {
            System.err.println("Error: Could not initialize log files: " + e.getMessage());
            return;
        }

        ClevisModel model = new ClevisModel();
        ClevisController controller = new ClevisController(model, logger);

        final ClevisView finalView = new ClevisView(model);
        view = finalView;
        controller.setView(finalView);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Clevis Vector Graphics");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(finalView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        System.out.println("Clevis started. Enter commands or 'quit' to exit.");
        System.out.println("GUI window opened for visual feedback.");

        while (!quit && scanner.hasNextLine()) {
            String commandLine = scanner.nextLine().trim();
            if (commandLine.isEmpty()) {
                continue;
            }

            logger.logCommand(commandLine);

            try {
                List<String> tokens = CommandParser.parse(commandLine);
                quit = CommandParser.executeCommand(tokens, controller);
            } catch (ClevisException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected internal error occurred: " + e.getMessage());
            }
        }

        if (logger != null) {
            logger.closeHtmlLog();
        }

        if (view != null) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(finalView);
                if (frame != null) {
                    frame.dispose();
                }
            });
        }

        System.out.println("Clevis terminated.");
    }
}
