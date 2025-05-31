package ru.job4j.cash;

public final class Account {
    private final int id;
    private final int amount;

    Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int id() {
        return id;
    }

    public int amount() {
        return amount;
    }
}
