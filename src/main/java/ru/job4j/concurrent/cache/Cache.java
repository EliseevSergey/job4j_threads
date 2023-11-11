package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base in) {
        return memory.computeIfPresent(in.id(), (k, b) -> {
                    if (memory.get(in.id()).version() != in.version()) {
                        throw new OptimisticException("Inputted model ver is not the same with cache version");
                    }
                    delete(findById(in.id()).get());
                    add(in);
                    return memory.get(k);
                }
        ) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.id()) != null;
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
