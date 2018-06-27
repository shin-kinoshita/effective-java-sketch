package Item32;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author subaru
 */
public class Methods {
    // Mixing generics and varargs can violate type safety!
    @SafeVarargs
    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = com.sun.tools.javac.util.List.of(42);
        Object[] objects = stringLists;
        objects[0] = intList;               // Heap pollution
        String s = stringLists[0].get(0);   // ClassCastException
    }

    // UNSAFE - Exposes a reference to its generic parameter array!
    static <T> T[] toArray(T... args) {
        return args;
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();     // can't get here
    }

    public static void main(String[] args) {
        String[] attributes = pickTwo("Good", "Fast", "Cheap");
    }

    // Safe method with a generic varargs parameter
    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<T>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    // List as a type safe alternative to a generic varargs parameter
    static <T> List<T> flatten(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    static <T> List<T> pickTwo2(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return com.sun.tools.javac.util.List.of(a, b);
            case 1: return com.sun.tools.javac.util.List.of(a, c);
            case 2: return com.sun.tools.javac.util.List.of(b, c);
        }
        throw new AssertionError();
    }
}
