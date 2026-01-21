package edu.duke.ece651.rn176.graph;

import java.util.HashSet;
import java.util.Set;

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
}
