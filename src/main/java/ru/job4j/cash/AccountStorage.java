package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);

        return true;
    }

    public synchronized boolean update(Account account) {
        delete(account.id());
        add(account);
        return true;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (this.getById(fromId).isEmpty() || this.getById(toId).isEmpty()) {
            return false;
        }

        Account accountFrom = this.getById(fromId).get();
        if (accountFrom.amount() < amount) {
            return false;
        }

        Account accountTo = this.getById(toId).get();
        this.update(new Account(fromId, accountFrom.amount() - amount));
        this.update(new Account(toId, accountTo.amount() + amount));

        return true;
    }
}
