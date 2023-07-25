package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelIndexSearchTest {

    @Test
    void wenSearchIntegerLessThanTen() {
        Integer[] array = new Integer[6];
        array[0] = 13;
        array[1] = 9;
        array[2] = 1;
        array[3] = 7;
        array[4] = 2;
        array[5] = 10;
        Integer obj = 7;
        ParallelIndexSearch search = new ParallelIndexSearch(array, obj, 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(forkJoinPool.invoke(search)).isEqualTo(3);
    }

    @Test
    void wenSearchIntegerMoreThanTen() {
        Integer[] array = new Integer[500];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Integer obj = 225;
        ParallelIndexSearch search = new ParallelIndexSearch(array, obj, 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(forkJoinPool.invoke(search)).isEqualTo(225);
    }

    @Test
    void whenSearchString() {
        String[] array = new String[5];
        array[0] = "black";
        array[1] = " ";
        array[2] = "&&&";
        array[3] = "156";
        array[4] = "object";
        ParallelIndexSearch search = new ParallelIndexSearch(array, "object", 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(forkJoinPool.invoke(search)).isEqualTo(4);
    }

    @Test
    void wenElementNotFound() {
        String[] array = new String[6];
        array[0] = "black";
        array[1] = " ";
        array[2] = "&&&";
        array[3] = "156";
        array[4] = "object";
        array[5] = "new";
        ParallelIndexSearch search = new ParallelIndexSearch(array, "OK!", 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(forkJoinPool.invoke(search)).isEqualTo(-1);
    }

    @Test
    void wenSearchCustomType() {
        SomeObject[] objects = new SomeObject[12];
        objects[0] = new SomeObject(1);
        objects[1] = new SomeObject(2);
        objects[2] = new SomeObject(4);
        objects[3] = new SomeObject(7);
        objects[4] = new SomeObject(13);
        objects[5] = new SomeObject(25);
        objects[6] = new SomeObject(17);
        objects[7] = new SomeObject(21);
        objects[8] = new SomeObject(14);
        objects[9] = new SomeObject(0);
        objects[10] = new SomeObject(12);
        objects[11] = new SomeObject(14);
        SomeObject obj = new SomeObject(12);
        ParallelIndexSearch search = new ParallelIndexSearch(objects, obj, 0, objects.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(forkJoinPool.invoke(search)).isEqualTo(10);
    }
}