package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Optional;

public final class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = !accounts.containsKey(account.id());
        if (rsl) {
            accounts.put(account.id(), account);
        } else {
            throw new IllegalArgumentException(String.format("Account with id %s is already in storage", account.id()));
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = accounts.containsKey(account.id());
        if (rsl) {
            accounts.replace(account.id(), account);
        } else {
            throw new IllegalArgumentException(String.format("Account with id %s is not in storage", account.id()));
        }
        return rsl;
    }

    public synchronized void delete(int id) {
        if (accounts.containsKey(id)) {
            accounts.remove(id);
        } else {
            throw new IllegalArgumentException(String.format("Account with id %s is not in storage", id));
        }
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        if (accounts.containsKey(id)) {
            rsl = Optional.of(accounts.get(id));
        } else {
            throw new IllegalArgumentException(String.format("Not found account by id = %s", id));
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = getById(fromId).isPresent() && getById(toId).isPresent();
        if (rsl) {
            Account from = getById(fromId).get();
            Account to = getById(toId).get();
            int amountFromBefore = from.amount();
            int amountToBefore = to.amount();
            int amountFromAfter = amountFromBefore - amount;
            int amountToAfter = amountToBefore + amount;
            Account fromAfter = new Account(fromId, amountFromAfter);
            Account toAfter = new Account(toId, amountToAfter);
            update(fromAfter);
            update(toAfter);
        } else {
            throw new IllegalArgumentException("Receiver or sender is not in storage");
        }
        return rsl;
    }
}
