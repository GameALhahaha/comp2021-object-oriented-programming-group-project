package hk.edu.polyu.comp.comp2021.clevis.view;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    @Test
    public void testLogCommandAndOutputAndCloseHtml() throws Exception {
        File html = File.createTempFile("clevis_log", ".html");
        File txt = File.createTempFile("clevis_log", ".txt");

        html.deleteOnExit();
        txt.deleteOnExit();

        Logger logger = new Logger(html.getAbsolutePath(), txt.getAbsolutePath());

        logger.logCommand("rectangle r1 0 0 1 1");
        logger.logOutput("some output");
        logger.closeHtmlLog();

        // Check TXT content basic
        BufferedReader txtReader = new BufferedReader(new FileReader(txt));
        String line1 = txtReader.readLine(); // may be null or empty due to clearTxtLog impl
        String line2 = txtReader.readLine();
        String line3 = txtReader.readLine();
        txtReader.close();

        // We only check that the command and output appear in some lines
        String allText = (line1 == null ? "" : line1) + "\n"
                + (line2 == null ? "" : line2) + "\n"
                + (line3 == null ? "" : line3);

        assertTrue(allText.contains("rectangle r1 0 0 1 1"));
        assertTrue(allText.contains("Output: some output"));
    }
}