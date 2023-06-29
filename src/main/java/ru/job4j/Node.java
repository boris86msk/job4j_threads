package ru.job4j;

public final class Node<T> {
    private Node<T> next;
    private T value;

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}
