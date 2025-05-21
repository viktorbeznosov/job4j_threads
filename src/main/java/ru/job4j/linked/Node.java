package ru.job4j.linked;

public final class Node<T> {
    private Node<T> next;
    private T value;

    public Node(Node next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}