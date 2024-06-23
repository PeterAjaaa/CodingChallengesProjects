package com.peterajaaa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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

    @Option(names = "-w", description = "Print number of words for the given file")
    boolean countWords;

    @Option(names = "-m", description = "Print number of characters for the given file")
    boolean countChars;

    @Parameters(paramLabel = "<FILE>", description = "The target file to operate on", arity = "0..1")
    String fileToCount;

    @Override
    public Integer call() throws Exception {
        final Path pathToFile = Paths.get(fileToCount);
        final String filename = pathToFile.getFileName().toString();
        final List<String> allLinesFromFile = Files.readAllLines(pathToFile);
        final String templateOutput = "%d %s\n";
        final String noFlagTemplateOutput = "%d  %d %d %s\n";
        final boolean noFlagUsed = !countBytes && !countLines && !countWords && !countChars;

        if (!Files.exists(pathToFile)) {
            System.err.println("File " + fileToCount + " does not exist");
            return 1;
        } else if (!Files.isRegularFile(pathToFile)) {
            System.err.println("Path " + fileToCount + " is not a file");
            return 1;
        } else if (!Files.isReadable(pathToFile)) {
            System.err.println("File " + fileToCount + " cannot be read");
            return 1;
        }

        if (noFlagUsed) {
            long lineCount = allLinesFromFile.size();
            long wordCount = allLinesFromFile.parallelStream()
                    .map(str -> str.split("\\s+"))
                    .flatMap(Arrays::stream)
                    .filter(item -> !item.isBlank())
                    .count();
            long bytesCount = Files.size(pathToFile);
            System.out.printf(noFlagTemplateOutput, lineCount, wordCount, bytesCount, filename);
        } else {
            if (countBytes) {
                long bytesCount = Files.size(pathToFile);
                System.out.printf(templateOutput, bytesCount, filename);
            }

            if (countLines) {
                long lineCount = allLinesFromFile.size();
                System.out.printf(templateOutput, lineCount, filename);
            }

            if (countWords) {
                long wordCount = allLinesFromFile.parallelStream()
                        .flatMap(str -> Arrays.stream(str.split("\\s+")))
                        .filter(item -> !item.isBlank())
                        .count();
                System.out.printf(templateOutput, wordCount, filename);
            }

            if (countChars) {
                long charCount = Files.readString(pathToFile).chars().count();
                System.out.printf(templateOutput, charCount, filename);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        args = new String[] { "" };
        int exitCode = new CommandLine(new WordCount()).execute(args);
        System.exit(exitCode);
    }

}