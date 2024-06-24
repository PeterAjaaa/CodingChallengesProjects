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

        if (fileToCount.isBlank()) {
            if (noFlagUsed) {
                final WordCountHelper wordCountHelperStdin = new WordCountHelper();

                long lineCount = wordCountHelperStdin.getLineCountStdin();
                long wordCount = wordCountHelperStdin.getWordCountStdin();
                long fileSize = wordCountHelperStdin.getSizeStdin();
                System.out.printf(noFlagTemplateOutputPiped, lineCount, wordCount, fileSize);
            }
            return 0;
        } else {
            final WordCountHelper wordCountHelper = new WordCountHelper(fileToCount);
            final String filename = wordCountHelper.getPathToFile().getFileName().toString();

            if (noFlagUsed) {
                long lineCount = wordCountHelper.getLineCount(fileToCount);
                long wordCount = wordCountHelper.getWordsCount(fileToCount);
                long fileSize = wordCountHelper.getSize(fileToCount);
                System.out.printf(noFlagTemplateOutput, lineCount, wordCount, fileSize, filename);
            } else {
                if (countBytes) {
                    long fileSize = wordCountHelper.getSize(fileToCount);
                    System.out.printf(templateOutput, fileSize, filename);
                }

                if (countLines) {
                    long lineCount = wordCountHelper.getLineCount(fileToCount);
                    System.out.printf(templateOutput, lineCount, filename);
                }

                if (countWords) {
                    long wordCount = wordCountHelper.getWordsCount(fileToCount);
                    System.out.printf(templateOutput, wordCount, filename);
                }

                if (countChars) {
                    long charCount = wordCountHelper.getCharsCount(fileToCount);
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
