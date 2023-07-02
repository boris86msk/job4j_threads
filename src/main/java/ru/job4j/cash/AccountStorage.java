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
            boolean result = false;
            if (!accounts.containsKey(account.id())) {
                accounts.put(account.id(), account);
                result = true;
            }
            return result;
        }
    }

    public boolean update(Account account) {
        synchronized (this.accounts) {
            boolean result = false;
            if (!accounts.containsKey(account.id())) {
                throw new IllegalStateException("Not found account by id = " + account.id());
            } else {
                accounts.put(account.id(), account);
                result = true;
            }
            return result;
        }
    }

    public boolean delete(int id) {
        synchronized (this.accounts) {
            boolean result = false;
            if (accounts.containsKey(id)) {
                accounts.remove(id);
                result = true;
            }
            return result;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (this.accounts) {
            return accounts.containsKey(id) ? Optional.of(accounts.get(id)) : Optional.empty();
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (this.accounts) {
            boolean res = false;
            if (this.getById(fromId).isPresent() && this.getById(toId).isPresent()
                    && amount > 0) {
                Account fromAcc = this.getById(fromId).get();
                Account toAcc = this.getById(toId).get();
                if (fromAcc.amount() >= amount) {
                    this.update(new Account(fromAcc.id(), fromAcc.amount() - amount));
                    this.update(new Account(toAcc.id(), toAcc.amount() + amount));
                    res = true;
                } else {
                    throw new IllegalArgumentException("insufficient funds for the transaction");
                }
            }
            return res;
        }
    }
}
