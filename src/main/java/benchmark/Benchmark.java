package benchmark;

import metrics.Metrics;
import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100,
            1_000,
            10_000,
            100_000
    };

    private static final int RUNS = 5;

    private static final int W1_GETS = 10_000;
    private static final int W2_QUERIES = 1_000;
    private static final int W3_OPERATIONS = 1_000;

    public static void main(String[] args) throws FileNotFoundException {

        new File("results/plots").mkdirs();

        try (PrintWriter writer =
                     new PrintWriter("results/results.csv")) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            for (int n : SIZES) {

                System.out.println("Running n = " + n);

                int[] data = generateData(n);
                int[] accessIndices = generateAccessIndices(n);
                int[] searchQueries = generateSearchQueries(data);
                int[] insertValues = generateInsertValues();

                RunResult w1Array = measureMedian(
                        () -> runW1DynamicArray(data, accessIndices)
                );

                writeResult(
                        writer,
                        "W1",
                        "-",
                        "DynamicArray",
                        n,
                        w1Array
                );

                RunResult w1List = measureMedian(
                        () -> runW1LinkedList(data, accessIndices)
                );

                writeResult(
                        writer,
                        "W1",
                        "-",
                        "MyLinkedList",
                        n,
                        w1List
                );

                System.out.println("  W1 finished");

                RunResult w2Array = measureMedian(
                        () -> runW2DynamicArray(data, searchQueries)
                );

                writeResult(
                        writer,
                        "W2",
                        "-",
                        "DynamicArray",
                        n,
                        w2Array
                );

                RunResult w2List = measureMedian(
                        () -> runW2LinkedList(data, searchQueries)
                );

                writeResult(
                        writer,
                        "W2",
                        "-",
                        "MyLinkedList",
                        n,
                        w2List
                );

                System.out.println("  W2 finished");

                RunResult w3ArrayHead = measureMedian(
                        () -> runW3DynamicArrayHead(data, insertValues)
                );

                writeResult(
                        writer,
                        "W3",
                        "head",
                        "DynamicArray",
                        n,
                        w3ArrayHead
                );

                RunResult w3ListHead = measureMedian(
                        () -> runW3LinkedListHead(data, insertValues)
                );

                writeResult(
                        writer,
                        "W3",
                        "head",
                        "MyLinkedList",
                        n,
                        w3ListHead
                );

                RunResult w3ArrayMiddle = measureMedian(
                        () -> runW3DynamicArrayMiddle(data, insertValues)
                );

                writeResult(
                        writer,
                        "W3",
                        "middle",
                        "DynamicArray",
                        n,
                        w3ArrayMiddle
                );

                RunResult w3ListMiddle = measureMedian(
                        () -> runW3LinkedListMiddle(data, insertValues)
                );

                writeResult(
                        writer,
                        "W3",
                        "middle",
                        "MyLinkedList",
                        n,
                        w3ListMiddle
                );

                System.out.println("  W3 finished");

                RunResult w4Heap = measureMedian(
                        () -> runW4MinHeap(data)
                );

                writeResult(
                        writer,
                        "W4",
                        "-",
                        "MinHeap",
                        n,
                        w4Heap
                );

                System.out.println("  W4 finished");
            }
        }

        System.out.println();
        System.out.println("Benchmark completed.");
        System.out.println("Results: results/results.csv");
    }

    private static RunResult runW1DynamicArray(
            int[] data,
            int[] indices) {

        Metrics metrics = new Metrics();

        DynamicArray array =
                createDynamicArray(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int index : indices) {
            array.get(index);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW1LinkedList(
            int[] data,
            int[] indices) {

        Metrics metrics = new Metrics();

        MyLinkedList list =
                createLinkedList(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int index : indices) {
            list.get(index);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW2DynamicArray(
            int[] data,
            int[] queries) {

        Metrics metrics = new Metrics();

        DynamicArray array =
                createDynamicArray(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int value : queries) {
            array.contains(value);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW2LinkedList(
            int[] data,
            int[] queries) {

        Metrics metrics = new Metrics();

        MyLinkedList list =
                createLinkedList(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int value : queries) {
            list.contains(value);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW3DynamicArrayHead(
            int[] data,
            int[] insertValues) {

        Metrics metrics = new Metrics();

        DynamicArray array =
                createDynamicArray(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int value : insertValues) {
            array.add(0, value);
        }

        for (int i = 0; i < W3_OPERATIONS; i++) {
            array.remove(0);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW3LinkedListHead(
            int[] data,
            int[] insertValues) {

        Metrics metrics = new Metrics();

        MyLinkedList list =
                createLinkedList(data, metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int value : insertValues) {
            list.add(0, value);
        }

        for (int i = 0; i < W3_OPERATIONS; i++) {
            list.remove(0);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW3DynamicArrayMiddle(
            int[] data,
            int[] insertValues) {

        Metrics metrics = new Metrics();

        DynamicArray array =
                createDynamicArray(data, metrics);

        metrics.reset();

        int middle = data.length / 2;

        long start = System.nanoTime();

        for (int value : insertValues) {
            array.add(middle, value);
        }

        for (int i = 0; i < W3_OPERATIONS; i++) {
            array.remove(middle);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW3LinkedListMiddle(
            int[] data,
            int[] insertValues) {

        Metrics metrics = new Metrics();

        MyLinkedList list =
                createLinkedList(data, metrics);

        metrics.reset();

        int middle = data.length / 2;

        long start = System.nanoTime();

        for (int value : insertValues) {
            list.add(middle, value);
        }

        for (int i = 0; i < W3_OPERATIONS; i++) {
            list.remove(middle);
        }

        long end = System.nanoTime();

        return result(end - start, metrics);
    }

    private static RunResult runW4MinHeap(int[] data) {

        Metrics metrics = new Metrics();

        MinHeap heap =
                new MinHeap(data.length, metrics);

        int[] output = new int[data.length];

        long start = System.nanoTime();

        for (int value : data) {
            heap.insert(value);
        }

        for (int i = 0; i < data.length; i++) {
            output[i] = heap.extractMin();
        }

        long end = System.nanoTime();

        for (int i = 1; i < output.length; i++) {
            if (output[i] < output[i - 1]) {
                throw new IllegalStateException(
                        "MinHeap output is not sorted"
                );
            }
        }

        return result(end - start, metrics);
    }

    private static DynamicArray createDynamicArray(
            int[] data,
            Metrics metrics) {

        DynamicArray array =
                new DynamicArray(data.length, metrics);

        for (int value : data) {
            array.add(value);
        }

        return array;
    }

    private static MyLinkedList createLinkedList(
            int[] data,
            Metrics metrics) {

        MyLinkedList list =
                new MyLinkedList(metrics);

        for (int i = data.length - 1; i >= 0; i--) {
            list.add(0, data[i]);
        }

        return list;
    }

    private static int[] generateData(int n) {

        Random random = new Random(42);

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static int[] generateAccessIndices(int n) {

        Random random = new Random(42);

        int[] indices = new int[W1_GETS];

        for (int i = 0; i < W1_GETS; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private static int[] generateSearchQueries(
            int[] data) {

        Random random = new Random(42);

        int[] queries =
                new int[W2_QUERIES];

        for (int i = 0; i < W2_QUERIES; i++) {

            if (i % 2 == 0) {
                queries[i] =
                        data[random.nextInt(data.length)];
            } else {
                queries[i] = -1 - i;
            }
        }

        return queries;
    }

    private static int[] generateInsertValues() {

        Random random = new Random(42);

        int[] values =
                new int[W3_OPERATIONS];

        for (int i = 0; i < W3_OPERATIONS; i++) {
            values[i] =
                    random.nextInt(1_000_000);
        }

        return values;
    }

    private static RunResult measureMedian(
            BenchmarkTask task) {

        task.run();

        RunResult[] results =
                new RunResult[RUNS];

        for (int i = 0; i < RUNS; i++) {
            results[i] = task.run();
        }

        Arrays.sort(
                results,
                Comparator.comparingLong(
                        result -> result.timeNs
                )
        );

        return results[RUNS / 2];
    }


    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            RunResult result) {

        double timeMs =
                result.timeNs / 1_000_000.0;

        writer.printf(
                Locale.US,
                "%s,%s,%s,%d,%.6f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                timeMs,
                result.steps,
                result.moves,
                result.comparisons
        );

        writer.flush();
    }

    private static RunResult result(
            long timeNs,
            Metrics metrics) {

        return new RunResult(
                timeNs,
                metrics.getSteps(),
                metrics.getMoves(),
                metrics.getComparisons()
        );
    }

    private interface BenchmarkTask {
        RunResult run();
    }

    private static class RunResult {

        final long timeNs;
        final long steps;
        final long moves;
        final long comparisons;

        RunResult(
                long timeNs,
                long steps,
                long moves,
                long comparisons) {

            this.timeNs = timeNs;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}