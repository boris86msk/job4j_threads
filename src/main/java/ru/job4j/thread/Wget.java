package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            long start = System.currentTimeMillis();
            int bytesRead;
            int bytesAmount = 0;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesAmount += bytesRead;
                if (bytesAmount >= 1024) {
                    long finish = System.currentTimeMillis();
                    int currentSpeed = bytesAmount / (int) (finish - start) * 1000;
                    start = finish;
                    bytesAmount = 0;
                    if (currentSpeed > speed) {
                        Thread.sleep(currentSpeed - speed);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void validator(String url, int speed) {
        UrlValidator validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new IllegalArgumentException("Ошибка url-адреса");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Недопустимое значение ограничения скорости");
        }
    }

    public static void main(String[] args) throws Exception {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        validator(url, speed);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
