package structures;
import metrics.Metrics;

public class MinHeap {
    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap(int capacity, Metrics metrics){
        if(capacity <= 0){
            capacity = 1;
        }
        heap = new int[capacity];
        this.metrics = metrics;
    }

    public int peekMin(){
        if(size==0){
            throw new IllegalStateException();
        }
        metrics.addStep();
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        metrics.addStep();
        int min = heap[0];
        if (size > 1) {
            metrics.addStep();
            metrics.addMove();

            heap[0] = heap[size - 1];
        }
        size--;
        if (size > 0) {
            bubbleDown(0);
        }
        return min;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

        metrics.addStep();
        metrics.addStep();

        metrics.addMove();
        metrics.addMove();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.addStep();
            metrics.addStep();
            metrics.addComparison();

            if (heap[index] >= heap[parent]) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.addStep();
                metrics.addStep();
                metrics.addComparison();

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.addStep();
                metrics.addStep();
                metrics.addComparison();

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index){
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    public void insert(int x){
        if(size == heap.length){
            int[] newHeap = new int[heap.length*2];

            for(int i=0; i<size; i++){
                newHeap[i] = heap[i];

                metrics.addStep();
                metrics.addMove();
            }
            heap = newHeap;
        }
        heap[size] = x;
        size++;
        bubbleUp(size-1);
    }

    boolean isValidHeap() {
        for (int i = 1; i < size; i++) {
            int parent = (i - 1) / 2;

            if (heap[parent] > heap[i]) {
                return false;
            }
        }

        return true;
    }
}