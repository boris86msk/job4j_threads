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
        return queue.size();
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() == maxSize) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.add(value);
            if (queue.size() == 0) {
                this.notify();
            }
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (queue.size() == maxSize) {
                this.notify();
            }
            return queue.poll();
        }
    }
}
