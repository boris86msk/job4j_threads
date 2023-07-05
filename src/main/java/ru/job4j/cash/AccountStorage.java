package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this.accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (this.accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (this.accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public boolean delete(int id) {
        synchronized (this.accounts) {
            return accounts.remove(id) != null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (this.accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (this.accounts) {
            boolean res = false;
            Optional<Account> fromAcc = this.getById(fromId);
            Optional<Account> toAcc = this.getById(toId);
            if (fromAcc.isPresent() && toAcc.isPresent()) {
                if (fromAcc.get().amount() >= amount) {
                    this.update(new Account(fromAcc.get().id(), fromAcc.get().amount() - amount));
                    this.update(new Account(toAcc.get().id(), toAcc.get().amount() + amount));
                    res = true;
                } else {
                    throw new IllegalArgumentException("insufficient funds for the transaction");
                }
            }
            return res;
        }
    }
}
