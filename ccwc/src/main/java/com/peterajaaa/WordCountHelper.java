package com.peterajaaa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WordCountHelper {
    private final Path pathToFile;
    private List<String> allLinesFromFile;

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

    public long getSize(String fileToCount) throws Exception {
        return Files.size(pathToFile);
    }

    public long getLineCount(String fileToCount) {
        return allLinesFromFile.size();
    }

    public long getWordsCount(String fileToCount) {
        return allLinesFromFile.parallelStream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .filter(item -> !item.isBlank())
                .count();
    }

    public long getCharsCount(String fileToCount) throws Exception {
        return Files.readString(pathToFile).chars().count();
    }

    public Path getPathToFile() {
        return pathToFile;
    }
}
