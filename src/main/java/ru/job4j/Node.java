package ru.job4j;

public class Node<T> {
    private volatile Node<T> next;
    private volatile T value;

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        if(this.next == null) {
            this.next = next;
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (this.value == null) {
            this.value = value;
        }
    }
}
