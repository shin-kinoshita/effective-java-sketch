package Item29;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author subaru
 */
public class Stack<E> {
    // Introducing generics into Stack
    // There are two ways to do it.

    // One way is to cast Object array into type E array in constructor.

    //private E[] elements;
    //private int size = 0;
    //private static final int DEFAULT_INITIAL_CAPACITY =  16;

    // The elements array will contain only E instances from push(E).
    // This is sufficient to ensure type safety, but the runtime
    // type of the array won't be E[]; it will always be Object[]!
    //@SuppressWarnings("unchecked")
    //public Stack() {
    //    elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
    //}

    //public void push(E e) {
    //    ensureCapacity();
    //    elements[size++] = e;
    //}

    //public E pop() {
    //    if (size == 0) {
    //        throw new EmptyStackException();
    //    }

    //    E result = elements[--size];
    //    elements[size] = null;  // Eliminate obsolete reference
    //    return result;
    //}

    //public boolean isEmpty() {
    //    return size == 0;
    //}

    //private void ensureCapacity() {
    //    if (elements.length == size) {
    //        elements = Arrays.copyOf(elements, 2 * size + 1);
    //    }
    //}

    // The Other way is to change type of elements to Object[].

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // Appropriate suppression of unchecked warning
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        // push requires elements to be of type E, so cast is correct
        @SuppressWarnings("unchecked")
        E result = (E)elements[--size];
        elements[size] = null;  // Eliminate obsolete reference
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // The First way is more readable because array is declared to be of type E[],
    // clearly indicating that it contains only E instances.
    // Also, the first way requires only a single cast (where the array is created),
    // while the second requires a separate cast each time an array element is read.
    // Thus, the first way is preferable and more common.
    // A disadvantage of the first way is heap pollution!!
    // The runtime type of the array does not match its compile-time type.


    // Little program to exercise our generic Stack
    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();

        for (String arg : args) {
            stack.push(arg);
            while (!stack.isEmpty()) {
                System.out.println(stack.pop().toUpperCase());
            }
        }
    }

    // There are some generic types that restrict the permissible values of their parameters.
    // For example,
    //class DelayQueue<E extends Delayed> implements BlockingQueue<E>
}
