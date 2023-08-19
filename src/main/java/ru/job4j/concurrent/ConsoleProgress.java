package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    char[] process = new char[] {'-', '\\', '|', '/'};
    @Override
    public void run() {
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
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(15000);
        progress.interrupt();
    }
}
