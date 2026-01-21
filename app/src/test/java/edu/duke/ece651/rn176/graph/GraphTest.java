package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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

    @Test
    public void test_getUndefinedNames(){
        Graph g = new Graph();
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames());
        g.getOrCreate("Gondor", false);
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames(), "Gondor");
        g.getOrCreate("Gondor", true);
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames());
        g.getOrCreate("Mordor", true);
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames());
        g.getOrCreate("Mordor", false);
        g.getOrCreate("Rohan", false);//undefined
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames(), "Rohan");
        g.getOrCreate("Shire", false);//undefined
        g.getOrCreate("Shire", false);
        g.getOrCreate("Shire", false);
        g.getOrCreate("Shire", false);
        TestUtils.checkIterableWithoutOrder(g.getUndefinedNames(), "Rohan", "Shire");
        g.getUndefinedNames(); // should be {"Shire", "Rohan"}
    }
    @Test
    public void test_getLocations(){
        Graph g = new Graph();
        Location mordor = g.getOrCreate("Mordor", true);
        Location gondor = g.getOrCreate("Gondor", true);
        Location shire = g.getOrCreate("Shire", true);
        Location scadrial = g.getOrCreate("Scadrial",  true);
        Location roshar =  g.getOrCreate("Roshar", true);
        TestUtils.checkIterableWithoutOrder(g.getLocations(), mordor, gondor, shire, scadrial, roshar);
    }
}