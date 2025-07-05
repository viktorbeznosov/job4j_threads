package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T item;

    public ParallelFindIndex(T[] array, T item, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.item = item;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(item)) {
                    return i;
                }
            }
            return -1;
        }

        if (from == to) {
            return array[from].equals(item) ? from : -1;
        }
        int middle = (from + to) / 2;

        ParallelFindIndex left = new ParallelFindIndex(array, item, from, middle);
        ParallelFindIndex right = new ParallelFindIndex(array, item, middle + 1, to);

        left.fork();
        right.fork();

        int leftIndex = (int) left.join();
        int rightIndex = (int) right.join();

        if (leftIndex != -1) {
            return leftIndex;
        }

        if (rightIndex != -1) {
            return rightIndex;
        }

        return -1;
    }

    public static <T> int find(T[] array, T item) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelFindIndex<T>(array, item, 0, array.length - 1));
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"aaa", "bbb", "zzz", "xxx", "qqq", "aaa", "qwerty", "111", "sdf", "ddd", "fff"};
        System.out.println(ParallelFindIndex.find(arr, "zzz"));
    }
}
