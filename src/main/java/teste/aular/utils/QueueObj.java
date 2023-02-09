package teste.aular.utils;

public class QueueObj<T> {

    // Atributos
    private int size;
    private T[] queue;

    // Construtor
    public QueueObj(int capacity) {
        this.queue = (T[]) new Object[capacity];
        this.size = 0;
    }

    // Métodos
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return queue.length == size;
    }

    public void insert(T info) {
        if (isFull()){
            throw new IllegalStateException("Full queue!");
        }
        queue[size++]  = info;
    }

    public T peek() {
        return queue[0];
    }

    public T poll() {
        T firstObject;
        if (!isEmpty()){
            firstObject = peek();
            for (int i = 0; i < size -1 ; i++) {
                queue[i] = queue[i+1];
            }
            queue[size -1] = null;
            size--;
            return firstObject;
        }
        return null;
    }

    public void show() {
        if (isEmpty()){
            System.out.println("Empty queue");
        }
        for (int i = 0; i < size; i++) {
            System.out.println(queue[i]);
        }
    }

    public T[] getQueue() {
        return queue;
    }

    public int getSize() {
        return size;
    }
}