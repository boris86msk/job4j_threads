package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content(n -> n < 0x80);
    }

    public String getContentWithoutUnicode() {
        return content(n -> true);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder res = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    res.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

}