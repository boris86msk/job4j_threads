package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + " из конструктора another")
        );
        another.start();

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + " из конструктора second")
        );
        second.start();

        System.out.println(Thread.currentThread().getName() + " из main");
    }
}
