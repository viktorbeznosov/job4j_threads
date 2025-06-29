package ru.job4j.cache;

public record Base(int id, String name, int version) {
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getVersion() {
        return this.version;
    }
}
