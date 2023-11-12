package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base in) {
        return memory.computeIfPresent(in.id(), (key, currentCacheBase) -> {
                    if (currentCacheBase.version() != in.version()) {
                        throw new OptimisticException("Inputted model ver is not the same with cache version");
                    }
                    delete(currentCacheBase);
                    add(new Base(key, in.name(), currentCacheBase.version() + 1));
                    return memory.get(key);
                }
        ) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.id()) != null;
    }

    public Optional<Base> findById(int id) {
        return Optional.ofNullable(memory.remove(id));
    }
}
