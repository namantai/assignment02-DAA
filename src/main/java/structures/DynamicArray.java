package structures;
import metrics.Metrics;

public class DynamicArray {
    private int[] arr;
    private int size;
    private Metrics metrics;
    public DynamicArray(int capacity, Metrics metrics){
        if(capacity <= 0){
            capacity = 1;
        }
        arr = new int[capacity];
        this.metrics = metrics;
    }
    public void printArray(){
        for(int i=0; i<size; i++){
            System.out.print(arr[i] + " ");
        }
    }
    public void add(int x){
        if(arr.length == size){
            int[] newArr = new int[2*size];
            for(int i=0; i<size; i++){
                newArr[i] = arr[i];

                metrics.addStep();
                metrics.addMove();
            }
            arr = newArr;
        }
        arr[size] = x;
        size++;
    }
    public void add(int index, int x){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if(arr.length==size){
            int[] newArr = new int[2*size];
            for(int i=0; i<size; i++){
                newArr[i] = arr[i];

            metrics.addStep();
            metrics.addMove();

            }
            arr = newArr;
        }
        for(int i=size; i > index; i--){
            arr[i] = arr[i-1];
            metrics.addStep();
            metrics.addMove();
        }
        arr[index] = x;
        size++;
    }
    public int get(int index){
        if(index < 0 || index>=size){
            throw new IndexOutOfBoundsException();
        }

        metrics.addStep();

        return arr[index];
    }
    public void remove(int index){
        if(index < 0 || index>=size){
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];

            metrics.addStep();
            metrics.addMove();
        }

        size--;
        arr[size] = 0;
    }
    public boolean contains(int x){
        for(int i=0; i<size; i++){

            metrics.addStep();
            metrics.addComparison();

            if(arr[i]==x){
                return true;
            }
        }
        return false;
    }
}
