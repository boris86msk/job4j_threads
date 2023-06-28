package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        int count = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + process[count]);
                count++;
                if (count == process.length) {
                    count = 0;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
