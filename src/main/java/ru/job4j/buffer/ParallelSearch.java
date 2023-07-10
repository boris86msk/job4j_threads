package ru.job4j.buffer;

import ru.job4j.synch.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 6; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
    }
    /**
     * Создаем поток consumer, запускаем.
     * Создаем поток producer, запускаем.
     * В нити main на объекте producer вызываем метод join(), тем самым указывает
     *  JVM не продолжать выполнение в нити main пока не выполнятся метод run()
     *   нити producer.
     * Выставляем в consumer флаг завершения (interrupt)
     *
     * При этом наблюдаем параллельную работу двух потоков, как только producer
     *  добавляет данные в queue, consumer тут же выводит их на консоль и ожидает
     *  500мс (которые выставлены в producer) до появления свежих данных в queue.
     */
}
