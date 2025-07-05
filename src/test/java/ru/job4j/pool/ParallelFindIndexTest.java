package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelFindIndexTest {
    @Test
    public void whenStringItem() {
        String[] array = new String[]{"aaa", "bbb", "zzz", "xxx", "qqq", "aaa", "qwerty", "111", "sdf", "ddd", "fff"};
        int index = ParallelFindIndex.find(array, "qwerty");
        assertThat(index).isEqualTo(6);
    }

    @Test
    public void whenIntegerItem() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5};
        int index = ParallelFindIndex.find(array, 3);
        assertThat(index).isEqualTo(2);
    }

    @Test
    public void whenIndexNotFound() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5};
        int index = ParallelFindIndex.find(array, 6);
        assertThat(index).isEqualTo(-1);
    }
}