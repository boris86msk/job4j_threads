package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {
    public static void main(String[] args) {
        /**
         * Создаем пул нитей по количеству доступных процессоров.
         */
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        /**
         * submit() Добавляет задачу в пул и сразу ее выполняет.
         */
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });

        /**
         * Метод shutdown() завершает работу пула, давая всем задачам в очереди отработать до конца,
         * после чего закроется сам пул. После вызова shutdown() пул не будет принимать новые задачи.
         * Существует второй метод закрытия пула - shutdownNow(). Он пытается немедленно закрыть пул.
         */
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
