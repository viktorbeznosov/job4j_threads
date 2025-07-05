package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;

public class FindIndex<T> {
    private T[] array;

    public FindIndex(T[] array) {
        this.array = array;
    }

    public int find(T item) {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(item)) {
                    return i;
                }
            }
            return -1;
        }

        return find(item, 0, array.length - 1);
    }

    public int find(T item, int from, int to) {
        if (from == to) {
            return array[from].equals(item) ? from : -1;
        }
        int middle = (from + to) / 2;
        int left = find(item, from, middle);
        int right = find(item, middle + 1, to);
        if (left != -1) {
            return left;
        }
        if (right != -1) {
            return right;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"aaa", "bbb", "zzz", "xxx", "qqq", "aaa", "qwerty", "111", "sdf", "ddd", "fff"};
        FindIndex<String> findIndex = new FindIndex<>(arr);
        System.out.println(findIndex.find("qwerty"));
    }
}
