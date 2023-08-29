package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String content(Predicate<Character> filter) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream in = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(in)) {
            int simbol;
            while ((simbol = bis.read()) > 0) {
                if (filter.test((char) simbol)) {
                    sb.append(simbol);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}