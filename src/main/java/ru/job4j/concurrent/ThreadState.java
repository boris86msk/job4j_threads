package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("Thread name: " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Thread name: " + Thread.currentThread().getName())
        );

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED) {
            first.getState();
        }
        while (second.getState() != Thread.State.TERMINATED) {
            second.getState();
        }
        System.out.println("Работа программы завершина");
    }
}
