package com.peterajaaa;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WordCountHelperStdin {
    private List<String> allLinesFromFile;
    private String contentString;

    public WordCountHelperStdin() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\\A");
        this.contentString = scanner.hasNext() ? scanner.next() : "";
        this.allLinesFromFile = Arrays.asList(this.contentString.split("\\R", -1));
        scanner.close();
    }

    public long getSizeStdin() {
        return contentString.getBytes(StandardCharsets.UTF_8).length;
    }

    public long getLineCount() {
        if (allLinesFromFile.size() > 0 && allLinesFromFile.get(allLinesFromFile.size() - 1).isEmpty()) {
            return allLinesFromFile.size() - 1;
        }
        return allLinesFromFile.size();
    }

    public long getWordCountStdin() {
        return allLinesFromFile.parallelStream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .filter(item -> !item.isBlank())
                .count();
    }

}
