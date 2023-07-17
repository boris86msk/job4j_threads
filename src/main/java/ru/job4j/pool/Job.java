package ru.job4j.pool;

public class Job implements Runnable {
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        Thread.State threadState = Thread.currentThread().getState();
        System.out.println(threadName + " Job run, state = " + threadState);
    }
}
