package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
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

    public static <T> Integer indexSearch(T[] array, T obj) {
        return new ForkJoinPool().invoke(new ParallelIndexSearch<T>(array, obj, 0, array.length - 1));
    }

    private Integer lineSearch() {
        int res = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(object)) {
                res = i;
                break;
            }
        }
        return res;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return lineSearch();
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
