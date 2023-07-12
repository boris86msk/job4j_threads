package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CountCas {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int expectedValue;
        int newValue;
        do {
            expectedValue = count.get();
            newValue = expectedValue++;
        } while (!count.compareAndSet(expectedValue, newValue));
    }

    public int get() {
        return count.get();
    }
}

/**
 * Работа метода compareAndSet() происходит в несколько этапов:
 * 1. Сначала он сравнивает текущее значение ссылки с ожидаемым значением.
 * Если они совпадают, то метод устанавливает новое значение и возвращает true.
 * 2. Если текущее значение не совпадает с ожидаемым, то метод ничего не делает
 * и возвращает false.
 */
