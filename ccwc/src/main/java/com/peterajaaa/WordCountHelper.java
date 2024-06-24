package com.peterajaaa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WordCountHelper {
    final Path pathToFile;
    List<String> allLinesFromFile;
    String inputLine;
    BufferedReader bufferedStdInputStreamReader;

    public WordCountHelper(String fileToCount) {
        this.pathToFile = Paths.get(fileToCount);

        if (!Files.exists(pathToFile)) {
            throw new IllegalArgumentException("File does not exist");
        } else if (!Files.isRegularFile(pathToFile)) {
            throw new IllegalArgumentException("Path is not a file!");
        } else if (!Files.isReadable(pathToFile)) {
            throw new IllegalArgumentException("File cannot be read");
        }

        try {
            this.allLinesFromFile = Files.readAllLines(pathToFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public WordCountHelper() {
        this.pathToFile = null;
        this.allLinesFromFile = null;
        this.bufferedStdInputStreamReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public long getSize(String fileToCount) throws Exception {
        return Files.size(pathToFile);
    }

    public long getLineCount(String fileToCount) throws Exception {
        return allLinesFromFile.size();
    }

    public long getWordsCount(String fileToCount) throws Exception {
        return allLinesFromFile.parallelStream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .filter(item -> !item.isBlank())
                .count();
    }

    public long getCharsCount(String fileToCount) throws Exception {
        return Files.readString(pathToFile).chars().count();
    }

    public long getLineCountStdin() {
        return bufferedStdInputStreamReader.lines()
                .count();
    }

    public long getWordCountStdin() {
        return bufferedStdInputStreamReader.lines()
                .parallel()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .filter(item -> !item.isBlank())
                .count();
    }

    public long getSizeStdin() throws Exception {
        long fileSize = 0;

        while ((inputLine = bufferedStdInputStreamReader.readLine()) != null) {
            byte[] bytes = inputLine.getBytes(StandardCharsets.UTF_8);
            fileSize += bytes.length;
            fileSize += System.lineSeparator().getBytes(StandardCharsets.UTF_8).length;
        }

        return fileSize;

    }

    public Path getPathToFile() {
        return pathToFile;
    }
}
