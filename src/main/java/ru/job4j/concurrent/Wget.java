package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        int i = 0;
                        while (i <= 100) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(500);
                            i++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
