package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    /**
     * matrix [2, 3, 1]
     *        [1, 4, 1]
     *        [2, 1, 3]
     */

    @Test
    public void wenSum() {
        int[][] ints = new int[3][3];
        ints[0][0] = 2;
        ints[0][1] = 3;
        ints[0][2] = 1;
        ints[1][0] = 1;
        ints[1][1] = 4;
        ints[1][2] = 1;
        ints[2][0] = 2;
        ints[2][1] = 1;
        ints[2][2] = 3;

        Sums[] referenceArray = new Sums[] {new Sums(6, 5), new Sums(6, 8),
                new Sums(6, 5)};
        Sums[] sums = RolColSum.sum(ints);
        assertThat(sums).isEqualTo(referenceArray);
    }

    @Test
    public void wenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] ints = new int[3][3];
        ints[0][0] = 2;
        ints[0][1] = 3;
        ints[0][2] = 1;
        ints[1][0] = 1;
        ints[1][1] = 4;
        ints[1][2] = 1;
        ints[2][0] = 2;
        ints[2][1] = 1;
        ints[2][2] = 3;

        Sums[] referenceArray = new Sums[] {new Sums(6, 5), new Sums(6, 8),
                new Sums(6, 5)};
        Sums[] sums = RolColSum.asyncSum(ints);
        assertThat(sums).isEqualTo(referenceArray);
    }
}