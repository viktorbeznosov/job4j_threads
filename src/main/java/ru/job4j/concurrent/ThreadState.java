package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
            () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        first.join();

        second.start();
        second.join();

        if (first.getState() == Thread.State.TERMINATED) {
            System.out.printf("Work of %s is completed", first.getName());
            System.out.println(System.lineSeparator());
        }
        if (second.getState() == Thread.State.TERMINATED) {
            System.out.printf("Work of %s is completed", second.getName());
            System.out.println(System.lineSeparator());
        }
    }
}
