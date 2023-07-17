package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int taskMaxSize) {
        this.tasks = new SimpleBlockingQueue<>(taskMaxSize);
        for (int i = 0; i < SIZE; i++) {
            Thread t = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
            t.setName("myThread_" + i);
            threads.add(t);
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(4);
        threadInfo();

        for (int i = 0; i < 8; i++) {
            threadPool.work(new Job());
        }

        threadPool.shutdown();
        threadInfo();
    }

    public static void threadInfo() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().startsWith("myThread_")) {
                System.out.println(t.getName() + " " + t.getState());
            }
        }
    }
}
