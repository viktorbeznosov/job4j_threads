package ru.job4j.threads;

public class RaceConditionExample {
    private int number = 0;

    public synchronized void increment() {
        for (int i = 0; i < 99999; i++) {
            int current = number;
            int next = number + 1;
            if (current + 1 != next) {
                throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionExample raceCondition = new RaceConditionExample();
        Thread one = new Thread(raceCondition::increment);
        one.start();
        Thread two = new Thread(raceCondition::increment);
        two.start();
        one.join();
        two.join();
    }
}