package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenPutt() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            for (int i = 1; i < 4; i++) {
                queue.offer(String.format("задание №%d", i));
            }
        });
        producer.start();
        producer.join();
        assertThat(queue.getSize()).isEqualTo(3);

    }

    @Test
    public void whenPull() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            for (int i = 1; i < 4; i++) {
                queue.offer(String.format("задание №%d", i));
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 1; i < 4; i++) {
                queue.poll();
            }
        });
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
        assertThat(queue.getSize()).isEqualTo(0);
    }

    @Test
    public void whenProducerWait() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            for (int i = 1; i < 4; i++) {
                queue.offer(String.format("задание №%d", i));
            }
        });
        Thread consumer = new Thread(queue::poll);
        producer.start();
        producer.join();
        consumer.start();
    }
}