package ru.job4j.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SaveFile {
    public static void saveContent(String content) throws IOException {
        Properties prop = new Properties();
        try (OutputStream o = new FileOutputStream(prop.getProperty("loadFile"))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
