package ru.job4j.concurrent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        var file = new File(path);
        var startAt = System.currentTimeMillis();
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[speed];
            int bytesTotal = 0;
            int byteReadQty;
            var downloadAt = System.nanoTime();
            while ((byteReadQty = in.read(dataBuffer)) != -1) {
                bytesTotal += byteReadQty;
                out.write(dataBuffer);
                if (bytesTotal >= speed) {
                    long actualTime = (System.nanoTime() - downloadAt) / 1000000;
                    System.out.printf("Total bytes read %s. Read %s bytes for session. Time to download session %s mls %n", bytesTotal, byteReadQty, actualTime);
                    if (actualTime < 1000) {
                        long sleepTime = 1000 - actualTime;
                        try {
                            Thread.sleep(sleepTime);
                            System.out.printf("Thread.sleep was applied: %s mls %n", sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    bytesTotal = 0;
                    downloadAt = System.nanoTime();
                }
            }
            System.out.printf("Total file download size: %s byte", Files.size(file.toPath()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validation(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.validateUrl(args[0]);
        int speed = Integer.parseInt(args[1]);
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException(String.format("Speed is wrong %s ", speed));
        }
        Path path = Path.of(args[2]);
        if (!path.toFile().exists()) {
            throw new IllegalArgumentException(String.format("File [%s] not exist ", path));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String path = args[2];
        Thread wget = new Thread(new Wget(url, speed, path));
        wget.start();
        wget.join();
    }
}
