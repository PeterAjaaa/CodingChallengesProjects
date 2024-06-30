import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.peterajaaa.WordCount;

import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordCountTest {

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

    @Test
    public void testFileNumberOfChars() {
        // Prepare a stream to capture the output from System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Assign the special stream to System.out
        System.setOut(ps);

        // Execute the command with the arguments
        int exitCode = new CommandLine(new WordCount()).execute("-m", "test.txt");

        // Flush the stream
        ps.flush();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals("339292 test.txt", output.trim());
    }

    @Test
    public void testNoArguments() {
        // Prepare a stream to capture the output from System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Assign the special stream to System.out
        System.setOut(ps);

        // Execute the command with the arguments
        int exitCode = new CommandLine(new WordCount()).execute("test.txt");

        // Flush the stream
        ps.flush();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals("7145  58164 342190 test.txt", output.trim());
    }

    @Test
    public void testFileNumberOfLinesStdin() throws Exception {
        // Prepare the content of test.txt as a String
        String fileContent = new String(Files.readAllBytes(Paths.get("test.txt")), StandardCharsets.UTF_8);
        InputStream stdin = System.in; // Save the original System.in
        PrintStream originalOut = System.out; // Save the original System.out

        try {
            // Redirect System.in to read from the file content
            System.setIn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

            // Prepare a stream to capture the output from System.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Assign the special stream to System.out
            System.setOut(ps);

            // Execute the command without the arguments to read from stdin
            int exitCode = new CommandLine(new WordCount()).execute("-l");

            // Flush the stream
            ps.flush();

            // Convert the captured output to a string
            String output = baos.toString();

            // Assertions
            Assertions.assertEquals(0, exitCode);
            // Update the expected output to match your program's output format
            Assertions.assertEquals("7145", output.trim());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(stdin);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testFileSizeStdin() throws Exception {
        // Prepare the content of test.txt as a String
        String fileContent = new String(Files.readAllBytes(Paths.get("test.txt")), StandardCharsets.UTF_8);
        InputStream stdin = System.in; // Save the original System.in
        PrintStream originalOut = System.out; // Save the original System.out

        try {
            // Redirect System.in to read from the file content
            System.setIn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

            // Prepare a stream to capture the output from System.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Assign the special stream to System.out
            System.setOut(ps);

            // Execute the command without the arguments to read from stdin
            int exitCode = new CommandLine(new WordCount()).execute("-l");

            // Flush the stream
            ps.flush();

            // Convert the captured output to a string
            String output = baos.toString();

            // Assertions
            Assertions.assertEquals(0, exitCode);
            // Update the expected output to match your program's output format
            Assertions.assertEquals("7145", output.trim());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(stdin);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testFileNumberOfWordsStdin() throws Exception {
        // Prepare the content of test.txt as a String
        String fileContent = new String(Files.readAllBytes(Paths.get("test.txt")), StandardCharsets.UTF_8);
        InputStream stdin = System.in; // Save the original System.in
        PrintStream originalOut = System.out; // Save the original System.out

        try {
            // Redirect System.in to read from the file content
            System.setIn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

            // Prepare a stream to capture the output from System.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Assign the special stream to System.out
            System.setOut(ps);

            // Execute the command without the arguments to read from stdin
            int exitCode = new CommandLine(new WordCount()).execute("-w");

            // Flush the stream
            ps.flush();

            // Convert the captured output to a string
            String output = baos.toString();

            // Assertions
            Assertions.assertEquals(0, exitCode);
            // Update the expected output to match your program's output format
            Assertions.assertEquals("58164", output.trim());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(stdin);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testFileNumberOfCharsStdin() throws Exception {
        // Prepare the content of test.txt as a String
        String fileContent = new String(Files.readAllBytes(Paths.get("test.txt")), StandardCharsets.UTF_8);
        InputStream stdin = System.in; // Save the original System.in
        PrintStream originalOut = System.out; // Save the original System.out

        try {
            // Redirect System.in to read from the file content
            System.setIn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

            // Prepare a stream to capture the output from System.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Assign the special stream to System.out
            System.setOut(ps);

            // Execute the command without the arguments to read from stdin
            int exitCode = new CommandLine(new WordCount()).execute("-m");

            // Flush the stream
            ps.flush();

            // Convert the captured output to a string
            String output = baos.toString();

            // Assertions
            Assertions.assertEquals(0, exitCode);
            // Update the expected output to match your program's output format
            Assertions.assertEquals("339292", output.trim());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(stdin);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testNoArgumentsStdin() throws Exception {
        // Prepare the content of test.txt as a String
        String fileContent = new String(Files.readAllBytes(Paths.get("test.txt")), StandardCharsets.UTF_8);
        InputStream stdin = System.in; // Save the original System.in
        PrintStream originalOut = System.out; // Save the original System.out

        try {
            // Redirect System.in to read from the file content
            System.setIn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

            // Prepare a stream to capture the output from System.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Assign the special stream to System.out
            System.setOut(ps);

            // Execute the command without the arguments to read from stdin
            int exitCode = new CommandLine(new WordCount()).execute();

            // Flush the stream
            ps.flush();

            // Convert the captured output to a string
            String output = baos.toString();

            // Assertions
            Assertions.assertEquals(0, exitCode);
            // Update the expected output to match your program's output format
            Assertions.assertEquals("7145   58164  342190", output.trim());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(stdin);
            System.setOut(originalOut);
        }
    }
}