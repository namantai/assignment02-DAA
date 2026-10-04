package structures;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    public void testInsertAndPeekMin() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        assertEquals(5, heap.peekMin());
    }

    @Test
    public void testHeapPropertyAfterInsert() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        int[] values = {30, 10, 50, 5, 20, 1, 40};

        for (int value : values) {
            heap.insert(value);

            assertTrue(heap.isValidHeap());
        }
    }

    @Test
    public void testExtractMin() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.peekMin());
    }

    @Test
    public void testSortedOutput() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        heap.insert(40);
        heap.insert(10);
        heap.insert(30);
        heap.insert(5);
        heap.insert(20);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
    }

    @Test
    public void testHeapPropertyAfterExtractMin() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        int[] values = {30, 10, 50, 5, 20, 1, 40};

        for (int value : values) {
            heap.insert(value);
        }

        for (int i = 0; i < values.length; i++) {
            heap.extractMin();

            assertTrue(heap.isValidHeap());
        }
    }

    @Test
    public void testResize() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(2, metrics);

        heap.insert(40);
        heap.insert(30);

        // здесь capacity уже заполнена
        // следующая вставка должна сделать resize
        heap.insert(20);
        heap.insert(10);
        heap.insert(5);

        assertEquals(5, heap.peekMin());
        assertTrue(heap.isValidHeap());
    }

    @Test
    public void testDuplicates() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        heap.insert(10);
        heap.insert(5);
        heap.insert(5);
        heap.insert(20);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
    }

    @Test
    public void testOneElement() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(1, metrics);

        heap.insert(10);

        assertEquals(10, heap.peekMin());
        assertEquals(10, heap.extractMin());
    }

    @Test
    public void testPeekMinEmpty() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        assertThrows(IllegalStateException.class, () -> {
            heap.peekMin();
        });
    }

    @Test
    public void testExtractMinEmpty() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(4, metrics);

        assertThrows(IllegalStateException.class, () -> {
            heap.extractMin();
        });
    }

    @Test
    public void testRandomDataAgainstPriorityQueue() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(1, metrics);

        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(10000);

            heap.insert(value);
            expected.add(value);
        }

        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), heap.extractMin());
        }
    }
}