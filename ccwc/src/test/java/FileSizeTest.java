import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.peterajaaa.WordCount;

import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FileSizeTest {

    @Test
    public void testFileSize() {
        // Prepare a stream to capture the output from System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Assign the special stream to System.out
        System.setOut(ps);

        // Execute the command with the arguments
        int exitCode = new CommandLine(new WordCount()).execute("-c", "test.txt");

        // Flush the stream
        ps.flush();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals("342190 test.txt", output.trim());
    }

    @Test
    public void testFileNumberOfLines() {
        // Prepare a stream to capture the output from System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Assign the special stream to System.out
        System.setOut(ps);

        // Execute the command with the arguments
        int exitCode = new CommandLine(new WordCount()).execute("-l", "test.txt");

        // Flush the stream
        ps.flush();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals("7145 test.txt", output.trim());
    }

    @Test
    public void testFileNumberOfWords() {
        // Prepare a stream to capture the output from System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Assign the special stream to System.out
        System.setOut(ps);

        // Execute the command with the arguments
        int exitCode = new CommandLine(new WordCount()).execute("-w", "test.txt");

        // Flush the stream
        ps.flush();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals("58164 test.txt", output.trim());
    }
}