package teste.aular.utils;

public class ListObj<T> {
    private T[] vector;

    private int numberOfElements;

    public ListObj(int capacity) {
        vector = (T[]) new Object[capacity];
        numberOfElements = 0;
    }

    public void add(T element) throws ArrayStoreException {
        if (numberOfElements >= vector.length) {
            throw new ArrayStoreException("full list");
        } else {
            vector[numberOfElements++] = element;
        }
    }

    public int search(T searchedElement) {
        for (int i = 0; i < numberOfElements; i++) {
            if (vector[i].equals(searchedElement)) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeByIndex(int index) {
        if (index < 0 || index >= numberOfElements) {
            System.out.println("\nInvalid index!");
            return false;
        }

        // Loop to "shift left" vector elements overwriting the removed element
        for (int i = index; i < numberOfElements - 1; i++) {
            vector[i] = vector[i + 1];
        }

        numberOfElements--;
        return true;
    }

    public boolean removeElement(T elementToRemove) {

        return removeByIndex(search(elementToRemove));
    }

    public int getSize() {

        return numberOfElements;
    }

    public T getElement(int index) {
        if (index < 0 || index >= numberOfElements) {
            return null;
        } else {
            return vector[index];
        }
    }

    public void clean() {

        numberOfElements = 0;
    }

    public void show() {
        if (numberOfElements == 0) {
            System.out.println("\nEmpty list.");
        } else {
            System.out.println("\nList Elements:");
            for (int i = 0; i < numberOfElements; i++) {
                System.out.println(vector[i]);
            }
        }
    }

    public void setElement(Integer index, T element) {
        vector[index] = element;
    }

    public T[] getVector() {
        return vector;
    }
}
