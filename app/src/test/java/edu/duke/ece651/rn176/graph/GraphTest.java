package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class GraphTest {
    @Test
    public void test_getOrCreate(){
        Graph g = new Graph();
        Location a = g.getOrCreate("Gondor", false);
        assertNotNull(a);
        assertEquals("Gondor", a.getName());
        Location b = g.getOrCreate("Gondor", false);
        assertSame(b, a);
        Location c = g.getOrCreate("Gondor", true);
        assertSame(c, a);
        assertThrows(IllegalArgumentException.class, () -> g.getOrCreate("Gondor", true));
        Location d = g.getOrCreate("Gondor", false);
        assertSame(d, a);
        Location e = g.getOrCreate("Mordor", false);
        assertEquals("Mordor", e.getName());
    }

    private void checkStringIterable(Iterable<String> it, String... expected) {
        Set<String> expectedSet = new HashSet<>();
        for(String e: expected) {
            expectedSet.add(e);
        }
        for(String e: it) {
            assertTrue(expectedSet.contains(e));
            expectedSet.remove(e);
        }
        assertTrue(expectedSet.isEmpty());
    }
    @Test
    public void test_getUndefinedNames(){
        Graph g = new Graph();
        g.getOrCreate("Gondor", false);
        g.getOrCreate("Gondor", true);
        g.getOrCreate("Mordor", true);
        g.getOrCreate("Mordor", false);
        g.getOrCreate("Rohan", false);//undefined
        g.getOrCreate("Shire", false);//undefined
        g.getOrCreate("Shire", false);
        g.getOrCreate("Shire", false);
        g.getOrCreate("Shire", false);
        g.getUndefinedNames(); // should be {"Shire", "Rohan"}
    }
}