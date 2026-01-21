package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    @SafeVarargs
    public static <T> void checkIterableWithoutOrder(Iterable<T> it, T... expected) {
        Set<T> expectedSet = new HashSet<>();
        for(T e: expected) {
            expectedSet.add(e);
        }
        for(T e: it) {
            assertTrue(expectedSet.contains(e));
            expectedSet.remove(e);
        }
        assertTrue(expectedSet.isEmpty());
    }
    public static <T, R>
        Iterable<R> transformIterable(Iterable<T> input, Function<T,R> transform) {
        ArrayList<R> ans = new ArrayList<>();
        for (T data: input) {
            ans.add(transform.apply(data));
        }
        return ans;
    }
    @SafeVarargs
    public static <T, R>
        void transformAndCheckIterableNoOrder(Iterable<T> input, Function<T,R> transform, R... expected) {
        Iterable<R> transformedInput = transformIterable(input, transform);
        checkIterableWithoutOrder(transformedInput, expected);
    }
}
