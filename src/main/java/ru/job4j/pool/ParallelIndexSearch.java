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

    @Override
    protected Integer compute() {
        if (from == to) {
            if (array[from].equals(object)) {
                return from;
            }
            return -1;
        }

        int mid = (from + to) / 2;
        ParallelIndexSearch leftIndexSearch = new ParallelIndexSearch(array, object, from, mid);
        ParallelIndexSearch rightIndexSearch = new ParallelIndexSearch(array, object, mid + 1, to);

        leftIndexSearch.fork();
        rightIndexSearch.fork();
        /**
        int left = leftIndexSearch.join();
        int right = rightIndexSearch.join();
         */
        return 1;
    }

    public static void main(String[] args) {
        SomeObject[] array = new SomeObject[] {new SomeObject(1), new SomeObject(2)};

        ParallelIndexSearch parallelIndexSearch = new ParallelIndexSearch(array, new SomeObject(2), 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(parallelIndexSearch));

    }
}
