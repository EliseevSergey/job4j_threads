package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = new char[] {'-', '\\', '|', '/'};
        try {
            int i = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\r load: " + process[i]);
                    Thread.sleep(500);
                    i++;
                        if (i == process.length) {
                            i = 0;
                    }
                }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(14000);
        progress.interrupt();
    }
}
