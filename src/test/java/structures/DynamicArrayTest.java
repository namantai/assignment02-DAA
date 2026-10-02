package structures;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    @Test
    public void testAddAndGet(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
    }
    @Test
    public void testAddAtIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(1, 30);

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
        assertEquals(20, array.get(2));
    }
    @Test
    public void testAddAtEndIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(2, 30);
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }
    @Test
    public void testContains(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(2, metrics);

        array.add(10);
        array.add(20);

        assertTrue(array.contains(10));
        assertFalse(array.contains(30));
    }
    @Test
    public void testRemove(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(30);

        array.remove(1);

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.get(2);
        });
    }
    @Test
    public void testResize(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(30);
        array.add(40);
        array.add(50);

        assertEquals(40, array.get(3));
        assertEquals(50, array.get(4));
    }
    @Test
    public void testAddAtIndexWithResize(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(30);
        array.add(1,40);

        assertEquals(10, array.get(0));
        assertEquals(40, array.get(1));
        assertEquals(20, array.get(2));
        assertEquals(30, array.get(3));

    }
    @Test
    public void testDuplicates(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(10);
        array.add(20);

        assertTrue(array.contains(10));
        assertEquals(10, array.get(0));
        assertEquals(10, array.get(1));
    }
    @Test
    public void testEmptyArrayInvalidGet(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        assertThrows(IndexOutOfBoundsException.class, () ->{
            array.get(0);
        });
    }
    @Test
    public void testNegativeIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.get(-1);
        });
    }
    @Test
    public void testRemoveInvalidIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.remove(1);
        });
    }
    @Test
    public void testAddInvalidIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.add(2,20);
        });
    }
    @Test
    public void testFirstAndLastIndex(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(2));
    }
    @Test
    public void testRandomDataAgainstArrayList(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(1, metrics);

        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);

        for(int i=0; i<1000; i++){
            int value = random.nextInt(10000);

            array.add(value);
            expected.add(value);
        }

        for(int i=0; i<1000; i++){
            assertEquals(expected.get(i),array.get(i));
        }
    }
    @Test
    public void testContainsOnEmptyArray(){
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(3, metrics);

        assertFalse(array.contains(10));
    }
}
