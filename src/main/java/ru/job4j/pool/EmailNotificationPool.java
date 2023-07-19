package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotificationPool {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(UserPool user) {
        String subject = String.format("Notification %s to email %s", user.getUserName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUserName());
        String email = user.getEmail();
        send(subject, body, email);
    }

    private void send(String subject, String body, String email) {

    }

    public void close() {
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

    public static void main(String[] args) {
        EmailNotificationPool emailNotificationPool = new EmailNotificationPool();
        emailNotificationPool.pool.submit(new Runnable() {
            @Override
            public void run() {
                UserPool user = new UserPool("Boris", "boris86@yandex.ru");
                emailNotificationPool.emailTo(user);
            }
        });
        emailNotificationPool.close();
    }
}
