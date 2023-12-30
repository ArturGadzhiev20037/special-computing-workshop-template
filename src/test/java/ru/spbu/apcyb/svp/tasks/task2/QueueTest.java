package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for custom implementation of queue.
 */
class QueueTest {

    @Test
    void sizeTest() {
        CustomQueue queue = new CustomQueue(List.of(5, 2, 1));
        assertEquals(3, queue.size());
    }

    @Test
    void emptyQueueTest() {
        CustomQueue queue = new CustomQueue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void nonEmptyQueueTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertFalse(queue.isEmpty());
    }

    @Test
    void containsTest() {
        CustomQueue queue = new CustomQueue(List.of(1, 5, 4));
        assertTrue(queue.contains(5));
        assertFalse(queue.contains(8));
    }

    @Test
    void containsAllTest() {
        CustomQueue queue = new CustomQueue(List.of(1, 5, 4));
        assertTrue(queue.containsAll(List.of(4, 5)));
        assertFalse(queue.containsAll(List.of(8, 1)));
    }

    @Test
    void addTest() {
        CustomQueue queue = new CustomQueue(List.of(1, 5, 4));
        assertTrue(queue.add(5));
    }

    @Test
    void addAllTest() {
        CustomQueue queue = new CustomQueue(List.of(1, 5, 4));
        assertTrue(queue.addAll(List.of(3, 2)));
    }

    @Test
    void iteratorTest() {
        CustomQueue queue = new CustomQueue();
        assertThrows(UnsupportedOperationException.class, queue::iterator);
    }

    @Test
    void toArrayTest() {
        CustomQueue queue = new CustomQueue();
        assertThrows(UnsupportedOperationException.class, queue::toArray);
    }

    @Test
    void retainAllTest() {
        CustomQueue queue = new CustomQueue();
        assertThrows(UnsupportedOperationException.class, () -> queue.retainAll(List.of(1, 2)));
    }

    @Test
    void removeTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertEquals(1, queue.remove());
        Exception e = assertThrows(NullPointerException.class, queue::remove);
        assertEquals("Cannot remove element from empty collection.", e.getMessage());
    }

    @Test
    void removeObjectTest() {
        CustomQueue queue = new CustomQueue();
        assertThrows(UnsupportedOperationException.class, () -> queue.remove(1));
    }

    @Test
    void removeAllTest() {
        CustomQueue queue = new CustomQueue();
        assertThrows(UnsupportedOperationException.class, () -> queue.removeAll(List.of(1, 2)));
    }

    @Test
    void pollTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertEquals(1, queue.poll());
        assertNull(queue.poll());
    }

    @Test
    void offerTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertTrue(queue.offer(5));
    }

    @Test
    void clearTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertThrows(UnsupportedOperationException.class, queue::clear);
    }

    @Test
    void elementTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertEquals(1, queue.element());
    }

    @Test
    void elementFromEmptyQueueTest() {
        CustomQueue queue = new CustomQueue();
        Exception e = assertThrows(NullPointerException.class, queue::element);
        assertEquals("The queue is empty", e.getMessage());
    }


    @Test
    void peekTest() {
        CustomQueue queue = new CustomQueue(List.of(1));
        assertEquals(1, queue.element());
    }

    @Test
    void peekFromEmptyQueueTest() {
        CustomQueue queue = new CustomQueue();
        assertNull(queue.peek());
    }

}