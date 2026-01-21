package edu.duke.ece651.rn176.graph;

import org.junit.jupiter.api.Test;

import static edu.duke.ece651.rn176.graph.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class GraphReaderTest {
    Graph g = new Graph();
    GraphReader gr = new GraphReader(null);
    @Test
    public void test_parseLine(){
        gr.parseLine("Mordor:Gondor:99:Rohan:37",g);
        transformAndCheckIterableNoOrder(g.getLocations(), Location::getName, "Mordor", "Gondor", "Rohan");//(loc)->loc.getName
        checkIterableWithoutOrder(g.getUndefinedNames(), "Gondor", "Rohan");
        Location mordor = g.getOrCreate("Mordor", false);
        transformAndCheckIterableNoOrder(mordor.getEdges(), (e)->e.getDestination().getName() + " | " + e.getWeight(), "Gondor | 99", "Rohan | 37");
        checkIterableWithoutOrder(g.getOrCreate("Gondor", false).getEdges());
        checkIterableWithoutOrder(g.getOrCreate("Rohan", false).getEdges());
    }
    @Test
    public void test_parseLine_empty(){
        gr.parseLine("         ",g);
        checkIterableWithoutOrder(g.getLocations());
        checkIterableWithoutOrder(g.getUndefinedNames());
    }
    @Test
    public void test_parseLine_errors(){
        assertThrows(IllegalArgumentException.class, ()->gr.parseLine("Mordor:Gondor:99:Rohan",g));
        assertThrows(NumberFormatException.class, ()->gr.parseLine("Mordor:99:Gondor:37:Rohan",g));
    }
}