package ru.job4j.concurrent.cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    private Cache cache = new Cache();


    @Test
    public void whenAddFind() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        cache.add(base);
        var find = cache.findById(base.id());
        assertEquals("Base", find.get().name());
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertEquals("Base updated", find.get().name());
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        var base = new Base(1,   "Base", 1);
        cache.add(base);
        cache.delete(base);
        var find = cache.findById(base.id());
        assertEquals(true, find.isEmpty());
    }
}