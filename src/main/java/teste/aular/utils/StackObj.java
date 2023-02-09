package teste.aular.utils;

public class StackObj {

    private int top;
    private int[] stack;

    public StackObj(int capacity) {
        this.top = -1;
        this.stack = new int [capacity];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == stack.length -1;
    }

    public void push(int info) {
        if (!isFull()){
            stack[++top] = info;
        }
        else {
            System.out.println("Full stack!");
        }
    }

    public int pop(){
        if (!isEmpty()){
            return stack[top--];
        }
        return -1;
    }

    public int peek(){
        if (!isEmpty()){
            return stack[top];
        }
        return -1;
    }

    public void show() {
        if (isEmpty()) {
            System.out.println("Empty stack");
        } else {
            for (int i = top; i >= 0; i--) {
                System.out.println(stack[i]);
            }
        }
    }

    public void displayOnline() {
        if (isEmpty()) {
            System.out.println("Empty stack");
        } else {
            for (int i = top; i >= 0; i--) {
                System.out.print(stack[i]);
            }
        }
    }

    public int getTop() {
        return top;
    }

}
