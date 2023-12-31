package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread load = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 1; i < 101; i++) {
                            Thread.sleep(500);
                            System.out.print("\rLoading : " + i + "%");
                        }
                        System.out.println();
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        load.start();
    }
}
