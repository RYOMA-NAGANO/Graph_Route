package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

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
    @Test
    public void test_toString(){
        Graph g = new Graph();
        assertEquals("<>", g.toString());
        Location mordor = g.getOrCreate("Mordor", true);
        assertEquals("<Mordor>", g.toString());
        Location gondor = g.getOrCreate("Gondor", true);
        Location shire = g.getOrCreate("Shire", true);
        Location scadrial = g.getOrCreate("Scadrial",  true);
        Location roshar = g.getOrCreate("Roshar", true);
        String expected = "<Gondor,Mordor,Roshar,Scadrial,Shire>";
        assertEquals(expected, g.toString());
    }

    private Graph readGraphFromFile(String name) throws IOException {
        InputStream st = getClass().getClassLoader().getResourceAsStream(name);
        InputStreamReader reader = new InputStreamReader(st);
        GraphReader gr = new GraphReader(reader);
        Graph g = gr.readGraph();
        return g;
    }
    private void checkShortestPath(Graph g, String startName, String endName, int expectedCost, String... expectedNames) {
        Location start = g.getOrCreate(startName, false);
        Location end = g.getOrCreate(endName, false);
        Path p = g.shortestPath(start, end);

        if (expectedNames == null) {
            assertNull(p);
            return;
        }

        assertNotNull(p);
        assertEquals(expectedCost, p.getTotalWeight());
        assertEquals(startName, p.getStart().getName());

        Iterable<String> nodeNames = TestUtils.transformIterable(p.getEdges(), (e) -> e.getDestination().getName());
        ArrayList<String> nameList = new ArrayList<>();
        Collections.addAll(nameList, expectedNames);
        assertIterableEquals(nameList, nodeNames);
    }
    @Test
    public void test_shortestPath1() throws IOException {
        Graph g = readGraphFromFile("graphs/graph1.txt");
        checkShortestPath(g, "Durham", "Durham", 0);
        checkShortestPath(g, "Durham", "Montreal", 778, "Philadelphia", "Montreal");
        checkShortestPath(g, "Dallas", "Philadelphia", 1397, "Louisville", "Philadelphia");
    }
    @Test
    public void test_shortestPath2() throws IOException {
        Graph g = readGraphFromFile("graphs/graph2.txt");
        checkShortestPath(g, "D", "A", 0, (String[]) null);
        checkShortestPath(g, "A", "F", 17, "B", "C", "E", "F");
    }


}