package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenMatrixIsNotSquare() {
        int[][] matrix = new int[][]{{1, 1}, {2, 2}, {3, 3}};

        assertThatThrownBy(() -> RolColSum.sum(matrix)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> RolColSum.asyncSum(matrix)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenMatrixLengthIsTheSameAsSumsLength() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        RolColSum.Sums[] asyncSums = RolColSum.asyncSum(matrix);
        assertThat(sums.length).isEqualTo(matrix.length).isEqualTo(asyncSums.length);
    }

    @Test
    public void whenSyncSum() {
        int[][] matrix = new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum()).isEqualTo(6);
        assertThat(sums[0].getColSum()).isEqualTo(3);
        assertThat(sums[2].getRowSum()).isEqualTo(6);
        assertThat(sums[2].getColSum()).isEqualTo(9);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum()).isEqualTo(3);
        assertThat(sums[0].getColSum()).isEqualTo(6);
        assertThat(sums[2].getRowSum()).isEqualTo(9);
        assertThat(sums[2].getColSum()).isEqualTo(6);
    }

    @Test
    public void whenSyncIsTheSameAsAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        RolColSum.Sums[] asyncSums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum()).isEqualTo(asyncSums[0].getRowSum());
        assertThat(sums[0].getColSum()).isEqualTo(asyncSums[0].getColSum());
        assertThat(sums[1].getRowSum()).isEqualTo(asyncSums[1].getRowSum());
        assertThat(sums[1].getColSum()).isEqualTo(asyncSums[1].getColSum());
        assertThat(sums[2].getRowSum()).isEqualTo(asyncSums[2].getRowSum());
        assertThat(sums[2].getColSum()).isEqualTo(asyncSums[2].getColSum());
    }
}