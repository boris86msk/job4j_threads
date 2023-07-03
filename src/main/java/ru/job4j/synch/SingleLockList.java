package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        synchronized (this) {
            this.list = copy(list);
        }
    }

    public void add(T value) {
        synchronized (this) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (this) {
            return list.get(index);
        }
    }

    private List<T> copy(List<T> origin) {
        synchronized (this) {
            return origin.stream().toList();
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (this) {
            return list.iterator();
        }
    }
}
