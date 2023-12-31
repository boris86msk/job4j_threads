package ru.job4j.concurrent;

public class VisualLoad {
    public static void main(String[] args) {
        Thread load = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 1; i < 101; i++) {
                            Thread.sleep(500);
                            System.out.print("\r[");
                            for (int j = 0; j < i; j++) {
                                System.out.print(new String(Character.toChars(0x2588)));
                            }
                            for (int j = 0; j < 100 - i; j++) {
                                System.out.print(" ");
                            }
                            System.out.print("]");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        load.start();
    }
}
