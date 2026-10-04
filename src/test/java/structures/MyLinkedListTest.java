package structures;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Random;

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
    @Test
    public void testAddAtEnd() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(2, 30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void testRemoveLast() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        list.remove(2);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(2);
        });
    }

    @Test
    public void testOneElement() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);

        assertEquals(10, list.get(0));

        list.remove(0);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });
    }

    @Test
    public void testDuplicates() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(10);
        list.add(20);

        assertEquals(10, list.get(0));
        assertEquals(10, list.get(1));
        assertTrue(list.contains(10));
    }

    @Test
    public void testNegativeIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
    }

    @Test
    public void testAddInvalidIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add(2, 20);
        });
    }

    @Test
    public void testRemoveInvalidIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(1);
        });
    }

    @Test
    public void testRandomDataAgainstLinkedList() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        LinkedList<Integer> expected = new LinkedList<>();
        Random random = new Random(42);

        for(int i = 0; i < 1000; i++) {
            int value = random.nextInt(10000);

            list.add(0, value);
            expected.add(0, value);
        }

        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), list.get(i));
        }
    }
}