package edu.duke.ece651.rn176.graph;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static edu.duke.ece651.rn176.graph.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class GraphReaderTest {
    Graph g = new Graph();
    GraphReader gr = new GraphReader(null);

    private void checkEdgesFor(Graph g, String name, String... expected){
        Location loc = g.getOrCreate(name, false);
        transformAndCheckIterableNoOrder(loc.getEdges(), (e) ->e.getDestination().getName() + " | " + e.getWeight(), expected);
    }
    @Test
    public void test_parseLine(){
        gr.parseLine("Mordor:Gondor:99:Rohan:37",g);
        transformAndCheckIterableNoOrder(g.getLocations(), Location::getName, "Mordor", "Gondor", "Rohan");//(loc)->loc.getName
        checkIterableWithoutOrder(g.getUndefinedNames(), "Gondor", "Rohan");
        checkEdgesFor(g, "Mordor", "Gondor | 99", "Rohan | 37");
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
    @Test
    public void test_readGraph() throws IOException {
        String data = "Mordor:Gondor:99\n" + "Gondor:Mordor:99:Rohan:37\n" + "Rohan:Gondor:37";
        Reader r = readerFromString(data);
        GraphReader gr = new GraphReader(r);
        Graph g = gr.readGraph();
        transformAndCheckIterableNoOrder(g.getLocations(), Location::getName, "Mordor", "Gondor", "Rohan");
        checkIterableWithoutOrder(g.getUndefinedNames());
        checkEdgesFor(g, "Mordor", "Gondor | 99");
        checkEdgesFor(g, "Gondor", "Mordor | 99", "Rohan | 37");
        checkEdgesFor(g, "Rohan", "Gondor | 37");

    }
    @Test
    public void test_readGraph_undef_nodes() throws IOException {
        String data = "Mordor:Gondor:99:Rohan:42\n" + "Rohan:Shire:12\n" + "Shire:Brie:23";
        Reader r = readerFromString(data);
        GraphReader gr = new GraphReader(r);
        IllegalArgumentException exn = assertThrows(IllegalArgumentException.class, ()->gr.readGraph());
        String startStr = "The following Locations were undefined:\n";
        assertTrue(exn.getMessage().startsWith(startStr), exn.getMessage());
        assertTrue(exn.getMessage().contains("Gondor"), exn.getMessage());
        assertTrue(exn.getMessage().contains("Brie"), exn.getMessage());
        assertFalse(exn.getMessage().contains("Rohan"), exn.getMessage());
        assertFalse(exn.getMessage().contains("Shire"), exn.getMessage());
    }

    @Test
    public void test_readGraph_graph1() throws IOException {
        InputStream st = getClass().getClassLoader().getResourceAsStream("graphs/graph1.txt");
        InputStreamReader read =  new InputStreamReader(st);
        GraphReader gr = new GraphReader(read);
        Graph g = gr.readGraph();
        transformAndCheckIterableNoOrder(g.getLocations(), Location::getName, "Durham", "Philadelphia", "Louisville", "Montreal", "Dallas");
        checkEdgesFor(g, "Durham", "Philadelphia | 370",
                "Dallas | 1039",
                "Louisville | 508");


        checkEdgesFor(g, "Philadelphia", "Durham | 372",
                "Louisville | 628",
                "Montreal | 408");

        checkEdgesFor(g, "Louisville", "Durham | 500",
                "Philadelphia | 643",
                "Dallas | 775",
                "Montreal | 862");

        checkEdgesFor(g, "Dallas", "Louisville | 754",
                "Durham | 1053");

        checkEdgesFor(g, "Montreal", "Philadelphia | 399",
                "Louisville | 873");

    }
}