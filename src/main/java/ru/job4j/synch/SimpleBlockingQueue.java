package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getSize() {
        synchronized (this) {
            return queue.size();
        }
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == maxSize) {
                this.wait();
            }
            queue.add(value);
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                this.wait();
            }
            T result = queue.poll();
            this.notify();
            return result;
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.size() == 0;
        }
    }
}
