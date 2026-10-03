package structures;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {
    @Test
    public void testAddAndGet() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void testAddAtIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 99);

        assertEquals(10, list.get(0));
        assertEquals(99, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
    }

    @Test
    public void testAddAtBeginning() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);

        list.add(0, 5);

        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(20, list.get(2));
    }

    @Test
    public void testRemoveFirst() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(0);

        assertEquals(20, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    public void testRemoveMiddle() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(1);

        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    public void testContains() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);

        assertTrue(list.contains(20));
        assertFalse(list.contains(99));
    }

    @Test
    public void testInvalidIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });
    }
}