package ru.job4j;

public class CountBarrier {
    /**
     * У монитора есть методы wait, notifyAll.
     * Метод notifyAll будит все нити, которые ждали изменения состояния.
     * Метод wait переводит нить в состояние ожидания,
     * если программа не может дальше выполняться.
     */
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод count изменяет состояние программы. Это значит,
     * что внутри метода count нужно вызывать метод notifyAll.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}



