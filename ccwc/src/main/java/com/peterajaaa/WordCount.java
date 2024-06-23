package com.peterajaaa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Option(names = "-w", description = "Print number of words for the given file")
    boolean countWords;

    @Parameters(paramLabel = "<FILE>", description = "The target file to operate on", arity = "1")
    String fileToCount;

    @Override
    public Integer call() throws Exception {
        Path pathToFile = Paths.get(fileToCount);
        String filename = pathToFile.getFileName().toString();
        List<String> allLinesFromFile = Files.readAllLines(pathToFile);
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
            System.out.printf(templateOutput, allLinesFromFile.size(), filename);
        } else if (countWords) {
            long wordCount = allLinesFromFile.parallelStream()
                    .map(str -> str.split("\\s+"))
                    .flatMap(Arrays::stream)
                    .filter(item -> !item.isBlank())
                    .count();
            System.out.printf(templateOutput, wordCount, filename);
        }
        return 0;
    }

    public static void main(String[] args) {
        args = new String[] { "-w", "test.txt" };
        int exitCode = new CommandLine(new WordCount()).execute(args);
        System.exit(exitCode);
    }

}