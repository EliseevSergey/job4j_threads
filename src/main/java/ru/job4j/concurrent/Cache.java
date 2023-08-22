package ru.job4j.concurrent;

public class Cache {
    private static Cache cache;

    private static Cache instOf() {
        synchronized (Cache.class) {
            if (cache == null) {
                cache = new Cache();
            }
            return cache;
        }
    }
}
