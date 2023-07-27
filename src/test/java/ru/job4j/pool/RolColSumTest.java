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

        RolColSum.Sums[] sum = RolColSum.sum(ints);

        assertThat(sum[0].getRowSum()).isEqualTo(6);
        assertThat(sum[0].getColSum()).isEqualTo(5);
        assertThat(sum[1].getRowSum()).isEqualTo(6);
        assertThat(sum[1].getColSum()).isEqualTo(8);
        assertThat(sum[2].getRowSum()).isEqualTo(6);
        assertThat(sum[2].getColSum()).isEqualTo(5);
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

        RolColSum.Sums[] sum = RolColSum.asyncSum(ints);

        assertThat(sum[0].getRowSum()).isEqualTo(6);
        assertThat(sum[0].getColSum()).isEqualTo(5);
        assertThat(sum[1].getRowSum()).isEqualTo(6);
        assertThat(sum[1].getColSum()).isEqualTo(8);
        assertThat(sum[2].getRowSum()).isEqualTo(6);
        assertThat(sum[2].getColSum()).isEqualTo(5);
    }

}