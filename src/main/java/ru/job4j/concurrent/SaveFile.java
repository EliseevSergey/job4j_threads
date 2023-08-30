package ru.job4j.concurrent;

import java.io.*;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
