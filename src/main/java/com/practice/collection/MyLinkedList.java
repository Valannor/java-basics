package com.practice.collection;

public class MyLinkedList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E value;
        Node<E> previous;
        Node<E> next;

        public Node(E value, Node<E> previous, Node<E> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    public void add(E element) {
        if (size == 0 && first == null) {
            first = last = new Node<>(element, null, null);
            size++;
            return;
        }

        addLast(element);
    }

    public void add(int index, E element) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index!");
        }

        if (index == size) {
            add(element);
            return;
        }

        if (index == 0) {
            addFirst(element);
            return;
        }

        Node<E> nodePrev = getNode(index).previous;
        Node<E> node = new Node<>(element, nodePrev, nodePrev.next);
        Node<E> nodeNext = node.next;

        if (nodeNext != null)
            nodeNext.previous = node;
        nodePrev.next = node;
        size++;
    }

    private Node<E> getNode(int index) {

        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Incorrect index!");

        Node<E> nodePrev = null;
        for (int i = 0; i <= index; i++) {
            if (i == 0)
                nodePrev = first;
            else
                nodePrev = nodePrev.next;
        }

        return nodePrev;
    }

    public E get(int index) {
        return getNode(index).value;
    }

    public void set(int index, E element) {
        Node<E> node = getNode(index);
        node.value = element;
    }

    public E remove(int index) {
        Node<E> node = getNode(index);

        if (index == 0 && first != null)
            first = first.next;
        if (index == size - 1 && last != null)
            last = last.previous;
        if (index > 0 && index < size - 1) {
            Node<E> prev = node.previous;
            Node<E> next = node.next;
            prev.next = next;
            next.previous = prev;
        }

        size--;

        return node.value;
    }

    public void addFirst(E element) {
        Node<E> node = new Node<>(element, null, first);

        if (first != null)
            first.previous = node;

        first = node;

        size++;
    }

    public void addLast(E element) {
        Node<E> node = new Node<>(element, last, null);

        if (last != null)
            last.next = node;

        last = node;

        size++;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        Node<E> temp = null;
        for (int i = 0; i < size; i++) {

            if (temp == null)
                temp = first;
            else temp = temp.next;

            builder.append(temp.value);

            if (i < size - 1) {
                builder.append(", ");
            }
        }

        builder.append("]");

        return builder.toString();
    }
}
