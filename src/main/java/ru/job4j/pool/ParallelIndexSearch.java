package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T obj, int from, int to) {
        this.array = array;
        this.object = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int res = -1;
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(object)) {
                    res = i;
                    break;
                }
            }
            return res;
        }

        int mid = (from + to) / 2;
        ParallelIndexSearch<T> leftIndexSearch = new ParallelIndexSearch<>(array, object, from, mid);
        ParallelIndexSearch<T> rightIndexSearch = new ParallelIndexSearch<>(array, object, mid + 1, to);

        leftIndexSearch.fork();
        rightIndexSearch.fork();

        int leftRes = leftIndexSearch.join();
        int rightRes = rightIndexSearch.join();

        return Math.max(leftRes, rightRes);
    }
}
