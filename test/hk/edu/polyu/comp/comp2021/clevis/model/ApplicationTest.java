package hk.edu.polyu.comp.comp2021.clevis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class ApplicationTest {

    private PrintStream originalOut;
    private PrintStream originalErr;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    /**
     *
     */
    @BeforeEach
    public void setUpStreams() {
        originalOut = System.out;
        originalErr = System.err;
        originalIn = System.in;

        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void testMissingBothLogs() {
        String[] args = {};
        Application.main(args);

        String err = errContent.toString();
        assertTrue(err.contains("Missing required log file paths"));
        assertTrue(err.contains("Usage: java hk.edu.polyu.comp.comp2021.clevis.Application"));
    }

    @Test
    public void testMissingHtmlLog() {
        String[] args = { "-txt", "log.txt" };
        Application.main(args);

        String err = errContent.toString();
        assertTrue(err.contains("Missing required log file paths"));
    }

    @Test
    public void testMissingTxtLog() {
        String[] args = { "-html", "log.html" };
        Application.main(args);

        String err = errContent.toString();
        assertTrue(err.contains("Missing required log file paths"));
    }

    @Test
    public void testLoggerInitializationFailure() {
        // Attempt to create log in a directory path - on most systems, this should fail
        String[] args = { "-html", ".", "-txt", "." };
        Application.main(args);
        String err = errContent.toString();
        assertTrue(err.contains("Could not initialize log files"));
    }

    @Test
    public void testSuccessfulStartupNoInput() {
        String[] args = { "-html", "appTest.html", "-txt", "appTest.txt" };
        // No input lines -> scanner.hasNextLine() false
        System.setIn(new ByteArrayInputStream(new byte[0]));

        Application.main(args);

        String out = outContent.toString();
        assertTrue(out.contains("Clevis started. Enter commands or 'quit' to exit."));
        assertTrue(out.contains("GUI window opened for visual feedback."));
        assertTrue(out.contains("Clevis terminated."));
    }

    @Test
    public void testOneQuitCommand() {
        String[] args = { "-html", "appTest2.html", "-txt", "appTest2.txt" };
        System.setIn(new ByteArrayInputStream("quit\n".getBytes()));

        Application.main(args);
        String out = outContent.toString();
        assertTrue(out.contains("Clevis started. Enter commands or 'quit' to exit."));
        assertTrue(out.contains("Clevis terminated."));
    }
}
