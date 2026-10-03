package structures;

import metrics.Metrics;

public class MyLinkedList {
    private static class Node{
        int value;
        Node next;

        Node(int value){
            this.value = value;
        }
    }
    private Node head;
    private int size;
    private Metrics metrics;

    public MyLinkedList(Metrics metrics){
        this.metrics = metrics;
    }

    public void add(int x){
        if(head == null){
            head = new Node(x);
            metrics.addMove();
            size++;
            return;
        }
        Node currentNode = head;
        while(currentNode.next != null){
            currentNode = currentNode.next;

            metrics.addStep();
        }
        currentNode.next = new Node(x);

        metrics.addMove();
        size++;

    }

    public int get(int index){
        if(index<0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node currentNode = head;

        for(int i=0; i<index; i++){
            currentNode = currentNode.next;
            metrics.addStep();
        }
        return currentNode.value;
    }

    public boolean contains(int x){
        Node currentNode = head;
        while(currentNode != null){
            metrics.addComparison();
            if(currentNode.value == x){
                return true;
            }
            currentNode = currentNode.next;
            metrics.addStep();
        }
        return false;
    }

    public void add(int index, int x){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(x);

        if(index == 0){
            newNode.next = head;
            head = newNode;

            metrics.addMove();
            metrics.addMove();

            size++;
            return;
        }

        Node currentNode = head;

        for(int i=0; i < index - 1; i++){
            currentNode = currentNode.next;
            metrics.addStep();
        }
        newNode.next = currentNode.next;
        currentNode.next = newNode;
        metrics.addMove();
        metrics.addMove();

        size++;
    }

    public void remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            head = head.next;
            metrics.addMove();
            size--;
            return;
        }

        Node currentNode = head;

        for(int i=0; i<index-1; i++){
            currentNode = currentNode.next;
            metrics.addStep();
        }

        currentNode.next = currentNode.next.next;
        metrics.addMove();

        size--;
    }
}
