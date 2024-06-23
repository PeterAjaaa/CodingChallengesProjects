package com.peterajaaa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ccwc", version = "1.0", description = "Count words in a file", mixinStandardHelpOptions = true)
public class WordCount implements Callable<Integer> {
    @Option(names = "-c", description = "Print number of bytes for the given file")
    boolean countBytes;

    @Option(names = "-l", description = "Print number of lines for the given file")
    boolean countLines;

    @Parameters(paramLabel = "<FILE>", description = "The target file to operate on", arity = "1")
    String fileToCount;

    @Override
    public Integer call() throws Exception {
        Path pathToFile = Paths.get(fileToCount);
        String filename = pathToFile.getFileName().toString();
        String templateOutput = "%d %s\n";

        if (!Files.exists(pathToFile)) {
            System.err.println("File " + fileToCount + " does not exist");
            return 1;
        }

        if (!Files.isReadable(pathToFile)) {
            System.err.println("File " + fileToCount + " cannot be read");
            return 1;
        }

        if (!Files.isRegularFile(pathToFile)) {
            System.err.println("Path " + fileToCount + " is not a file");
            return 1;
        }

        if (countBytes) {
            System.out.printf(templateOutput, Files.size(pathToFile), filename);
        } else if (countLines) {
            System.out.printf(templateOutput, Files.readAllLines(pathToFile).size(), filename);
        }
        return 0;
    }

    public static void main(String[] args) {
        args = new String[] { "-l", "test.txt" };
        int exitCode = new CommandLine(new WordCount()).execute(args);
        System.exit(exitCode);
    }

}