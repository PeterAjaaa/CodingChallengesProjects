package com.peterajaaa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
        final boolean noFlagUsed = !countBytes && !countLines && !countWords && !countChars;
        final String templateOutput = "%d %s\n";
        final String noFlagTemplateOutput = "%d  %d %d %s\n";
        final String noFlagTemplateOutputPiped = "%d   %d  %d\n";

        if (fileToCount == null) {
            String inputLine;
            BufferedReader bufferedStdInputStreamReader = new BufferedReader(new InputStreamReader(System.in));

            if (noFlagUsed) {
                long lineCount = bufferedStdInputStreamReader.lines()
                        .count();
                long wordCount = bufferedStdInputStreamReader.lines()
                        .parallel()
                        .flatMap(str -> Arrays.stream(str.split("\\s+")))
                        .filter(item -> !item.isBlank())
                        .count();
                long bytesCount = 0;

                while ((inputLine = bufferedStdInputStreamReader.readLine()) != null) {
                    byte[] bytes = inputLine.getBytes(StandardCharsets.UTF_8);
                    bytesCount += bytes.length;
                    bytesCount += System.lineSeparator().getBytes(StandardCharsets.UTF_8).length;
                }
                System.out.printf(noFlagTemplateOutputPiped, lineCount, wordCount, bytesCount);

            }
            return 0;
        } else {
            final Path pathToFile = Paths.get(fileToCount);
            final String filename = pathToFile.getFileName().toString();
            final List<String> allLinesFromFile = Files.readAllLines(pathToFile);

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
                        .flatMap(str -> Arrays.stream(str.split("\\s+")))
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
    }

    public static void main(String[] args) {
        args = new String[] { "" };
        int exitCode = new CommandLine(new WordCount()).execute(args);
        System.exit(exitCode);
    }
}
