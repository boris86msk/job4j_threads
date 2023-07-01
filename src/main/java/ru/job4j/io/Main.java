package ru.job4j.io;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ParseFile parseFile = new ParseFile(new File("C:\\projects\\job4j_threads\\job4j_threads\\job4j_threads.iml"));
        parseFile.getContent();
        parseFile.getContentWithoutUnicode();
        SaveFile.saveContent(parseFile.getContent());
    }
}
