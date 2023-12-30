package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for custom implementation of doubly linked list.
 */
class ListTest {

    @Test
    void emptyListTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        assertTrue(list.isEmpty());
    }

    @Test
    void addObjectTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        assertTrue(list.add(12));
        assertEquals(1, list.size());
    }

    @Test
    void addSeveralObjectsTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.add(1);
        list.add(2);
        list.add("123");
        assertEquals(3, list.size());
        assertTrue(list.addAll(List.of(99, 100)));
    }


    @Test
    void addInTheMiddleTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of(1, 2, 3));
        list.add(1, 5);
        assertEquals(4, list.size());
        assertEquals(5, list.get(1));
        list.addAll(1, List.of(9, 8));
        assertEquals(9, list.get(1));
        assertEquals(8, list.get(2));
        assertEquals(6, list.size());
    }

    @Test
    void getTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.add(1);
        list.add(2);
        Object result = list.get(1);
        assertEquals(2, result);
    }

    @Test
    void getFromEmptyListTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        Object element = list.get(0);
        assertNull(element);
    }

    @Test
    void addAllTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        DoublyLinkedList list2 = new DoublyLinkedList();
        list2.addAll(List.of(1, 2, 3));
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test
    void addOnZeroIndex() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "333"));
        list.add(0, "hello");
        List<String> expected = List.of("hello", "12", "333");
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), list.get(i));
        }
    }

    @Test
    void addOnListSizeIndex() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "333"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(list.size(), "hello"));
    }

    @Test
    void isListEmptyTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.add("123");
        DoublyLinkedList list2 = new DoublyLinkedList();
        assertFalse(list1.isEmpty());
        assertTrue(list2.isEmpty());
    }

    @Test
    void searchTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addAll(new ArrayList<>(List.of(1, 5, 8, 4, 3)));
        int index = list.indexOf(5);
        assertEquals(1, index);
    }

    @Test
    void elementNotFoundTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addAll(new ArrayList<>(List.of(1, 5, 8, 4, 3)));
        int index = list.indexOf(-15);
        assertEquals(-1, index);
    }

    @Test
    void removeElementTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.addAll(new ArrayList<>(List.of(1, 5, 8, 4, 3)));
        Object result = list1.remove(2);

        DoublyLinkedList list2 = new DoublyLinkedList();
        list2.addAll(new ArrayList<>(List.of(1, 5, 4, 3)));
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
        assertEquals(8, result);
    }

    @Test
    void removeElementOnTailTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.addAll(new ArrayList<>(List.of(1, 5, 8, 4, 3)));
        Object result = list1.remove(4);

        DoublyLinkedList list2 = new DoublyLinkedList();
        list2.addAll(new ArrayList<>(List.of(1, 5, 8, 4)));
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
        assertEquals(3, result);
    }

    @Test
    void removeHeadTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.addAll(new ArrayList<>(List.of(1, 5, 8, 4, 3)));
        Object result = list1.remove(0);

        DoublyLinkedList list2 = new DoublyLinkedList();
        list2.addAll(new ArrayList<>(List.of(5, 8, 4, 3)));
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
        assertEquals(1, result);
    }

    @Test
    void removeLastElementTest() {
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.addAll(new ArrayList<>(List.of(1)));
        Object result = list1.remove(0);
        assertEquals(1, result);
        assertEquals(0, list1.size());
    }

    @Test
    void removeFromEmptyListTest() {
        DoublyLinkedList list = new DoublyLinkedList();
        Exception e = assertThrows(NullPointerException.class, () -> list.remove(0));
        assertEquals("Cannot remove element from empty collection.", e.getMessage());
    }

    @Test
    void containsElementTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of(1, 5, 8, 4, 3));
        assertTrue(list.contains(5));
        assertFalse(list.contains(-15));
    }

    @Test
    void containsAllTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of(1, 5, 8, 4, 3));
        assertTrue(list.containsAll(List.of(5,3)));
        assertFalse(list.containsAll(List.of(0, -15, 9)));
    }

    @Test
    void clearTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of(1));
        assertThrows(UnsupportedOperationException.class, list::clear);
    }

    @Test
    void setTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of(1));
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, 5));
    }

    @Test
    void removeObjectTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove("12"));
    }

    @Test
    void removeAllTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "21", "5"));
        assertThrows(UnsupportedOperationException.class, () -> list.removeAll(List.of("12", "5")));
    }

    @Test
    void lastIndexOfTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12"));
        assertThrows(UnsupportedOperationException.class, () -> list.lastIndexOf("12"));
    }

    @Test
    void iteratorTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12"));
        assertThrows(UnsupportedOperationException.class, list::iterator);
    }

    @Test
    void listIteratorTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12"));
        assertThrows(UnsupportedOperationException.class, list::listIterator);
    }

    @Test
    void listIteratorIndexTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12"));
        assertThrows(UnsupportedOperationException.class, () -> list.listIterator(0));
    }

    @Test
    void subListTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "1?", "8"));
        assertThrows(UnsupportedOperationException.class, () -> list.subList(0, 1));
    }

    @Test
    void retainAllTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "1?", "8"));
        assertThrows(UnsupportedOperationException.class, () -> list.retainAll(List.of(0, 1)));
    }

    @Test
    void toArrayTest() {
        DoublyLinkedList list = new DoublyLinkedList(List.of("12", "1?", "8"));
        Object[] expected = new Object[]{"12", "1?", "8"};
        Object[] listAsArray = list.toArray();
        assertEquals(3, listAsArray.length);
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], listAsArray[i]);
        }
    }

}
