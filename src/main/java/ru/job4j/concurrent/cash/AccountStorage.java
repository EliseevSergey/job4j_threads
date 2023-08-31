package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Optional;

public final class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return account.equals(accounts.putIfAbsent(account.id(), account));
    }

    public synchronized boolean update(Account account) {
        return account.equals(accounts.replace(account.id(), account));
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    private synchronized void validationForTransfert(int fromId, int toId, int amount) {
        if (!getById(fromId).isPresent() && getById(toId).isPresent()) {
            throw new IllegalArgumentException("Receiver or sender is not in storage");
        }
        if (getById(fromId).get().amount() < amount) {
            throw new IllegalArgumentException("Sender's balance is less than amount");
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        validationForTransfert(fromId, toId, amount);
        boolean t1 = update(new Account(toId, getById(toId).get().amount() + amount));
        boolean t2 = update(new Account(fromId, getById(fromId).get().amount() - amount));
        return t1 && t2;
    }
}
