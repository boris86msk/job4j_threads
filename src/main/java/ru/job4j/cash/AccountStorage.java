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
        return putIfAbsent(account);
    }

    public boolean update(Account account) {
        return replace(account);
    }

    public boolean delete(int id) {
        return remove(id);
    }

    private boolean putIfAbsent(Account account) {
        synchronized (this.accounts) {
            boolean result = false;
            if (!accounts.containsKey(account.id())) {
                accounts.put(account.id(), account);
                result = true;
            }
            return result;
        }
    }

    private boolean replace(Account account) {
        synchronized (this.accounts) {
            if (!accounts.containsKey(account.id())) {
                throw new IllegalStateException("Not found account by id = " + account.id());
            } else {
                accounts.put(account.id(), account);
            }
            return true;
        }
    }

    private boolean remove(int id) {
        synchronized (this.accounts) {
            if (!accounts.containsKey(id)) {
                throw new IllegalStateException("Not found account by id = " + id);
            } else {
                accounts.remove(id);
            }
            return true;
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
