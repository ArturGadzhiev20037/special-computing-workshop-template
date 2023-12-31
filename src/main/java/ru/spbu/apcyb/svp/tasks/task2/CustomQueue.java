package ru.spbu.apcyb.svp.tasks.task2;

import java.util.Collection;
import java.util.Iterator;

/**
 * Custom implementation for queue.
 */
public class CustomQueue implements java.util.Queue {

    private final DoublyLinkedList queue;

    public CustomQueue() {
        queue = new DoublyLinkedList();
    }

    public CustomQueue(Collection c) {
        queue = new DoublyLinkedList(c);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return queue.containsAll(c);
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Object o) {
        return queue.add(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return queue.addAll(c);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(Object o) {

        queue.add(o);
        return queue.contains(o);
    }

    @Override
    public Object remove() {
        return queue.remove(0);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object poll() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.remove(0);
    }

    @Override
    public Object element() {

        Object first = queue.get(0);
        if (first == null) {
            throw new NullPointerException("The queue is empty");
        }
        return first;
    }


    @Override
    public Object peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.get(0);
    }
}
