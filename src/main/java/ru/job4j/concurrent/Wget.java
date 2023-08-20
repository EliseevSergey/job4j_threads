package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("./data/tmp2.xml");
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                long actualTime = System.nanoTime() - downloadAt;
                long sleepTime = (1000000 * 512 / actualTime) / speed;
                    if (sleepTime > 1) {
                        try {
                            System.out.printf("Sleep was applied : %s mls %n", sleepTime);
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                System.out.printf("Read 512 bytes : %s  . nano: SleepTime: %s mls %n", actualTime, sleepTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.printf("bytes: %s", Files.size(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validation(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        String url = args[0];
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.validateUrl(url);
        int speed = Integer.parseInt(args[1]);
        if (speed <= 0) {
            throw new IllegalArgumentException(String.format("Speed is wrong %s ", speed));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
