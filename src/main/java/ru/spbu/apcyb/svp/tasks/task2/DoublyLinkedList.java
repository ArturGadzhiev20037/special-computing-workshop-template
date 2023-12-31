package ru.spbu.apcyb.svp.tasks.task2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementation of doubly linked list.
 */
public class DoublyLinkedList implements List {

    private int size = 0;
    private Node head;
    private Node tail;

    private static class Node {
        Object value;
        Node prev;
        Node next;

        Node(Object value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void isIndexInInterval(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public DoublyLinkedList() {}

    public DoublyLinkedList(Collection c) {
        this();
        this.addAll(c);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (head == null);
    }

    @Override
    public boolean contains(Object o) {
        int index = this.indexOf(o);
        return index != -1;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, Object element) {
        isIndexInInterval(index);

        if (index == 0) {
            Node newNode = new Node(element, null, head);
            head.prev = newNode;
            head = newNode;

        } else {
            Node worker = head;
            for (int i = 0; i < index; i++) {
                worker = worker.next;
            }

            Node newNode = new Node(element, worker.prev, worker);
            worker.prev.next = newNode;
            worker.prev = newNode;
        }

        size++;
    }

    @Override
    public boolean add(Object o) {

        if (head == null) {
            head = new Node(o, null, null);
            tail = head;

        } else {

            Node newNode = new Node(o, tail, null);
            tail.next = newNode;
            tail = newNode;
        }

        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection c) {

        for (Object o : c) {
            this.add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {

        Object[] objects = c.toArray();
        for (int i = 0; i < objects.length; i++) {
            this.add(index + i, objects[i]);
        }
        return true;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(int index) {

        if (this.isEmpty()) {
            return null;
        }
        isIndexInInterval(index);

        Node worker = head;
        for (int i = 0; i < index; i++) {
            worker = worker.next;
        }
        return worker.value;
    }

    @Override
    public Object set(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(int index) {

        if (this.isEmpty()) {
            throw new NullPointerException("Cannot remove element from empty collection.");
        }
        isIndexInInterval(index);

        size--;
        Object element;

        if (size == 0) {
            element = head.value;
            head = null;
            tail = null;

        } else if (index == size) {
            element = tail.value;
            tail.prev.next = null;
            tail = tail.prev;

        } else if (index == 0) {
            element = head.value;
            head.next.prev = null;
            head = head.next;

        } else {
            Node worker = head;
            for (int i = 0; i < index; i++) {
                worker = worker.next;
            }
            element = worker.value;
            worker.prev.next = worker.next;
            worker.next.prev = worker.prev;
        }

        return element;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {

        Node worker = head;
        for (int i = 0; i < size; i++) {
            if (worker.value.equals(o)) {
                return i;
            }
            worker = worker.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {

        return toArray();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node worker = head;
        for (int i = 0; i < size; i++) {
            result[i] = worker.value;
            worker = worker.next;
        }

        return result;
    }
}

