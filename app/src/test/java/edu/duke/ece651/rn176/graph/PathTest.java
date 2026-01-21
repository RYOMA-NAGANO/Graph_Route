package edu.duke.ece651.rn176.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static edu.duke.ece651.rn176.graph.TestUtils.checkIterableWithoutOrder;
import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    Location start = new Location("start");
    Location locA = new Location("A");
    Edge edgeA = new Edge(locA, 12);
    Location locB = new Location("B");
    Edge edgeB = new Edge(locB, 15);
    Path empty = new Path(start);
    Location mid1 = new Location("middle1");
    Location mid2 = new Location("middle2");
    Location end = new Location("end");
    Edge e1 = new Edge(mid1, 1);
    Edge e2 = new Edge(mid2, 7);
    Edge e3 = new Edge(end, 12);

    private void pathCheckHelper(Path toCheck, Location start, ArrayList<Edge> expectedEdges, int expectedWeight){
        assertEquals(start, toCheck.getStart());
        assertEquals(expectedWeight, toCheck.getTotalWeight());
        assertIterableEquals(expectedEdges, toCheck.getEdges());
    }
    @Test
    public void test_empty_path(){
        pathCheckHelper(empty, start, new ArrayList<>(), 0);
    }
    @Test
    public void test_extendby(){
        ArrayList<Edge> expected = new ArrayList<>();
        Path p1 = empty.extendBy(edgeA);
        pathCheckHelper(empty, start, expected, 0);
        expected.add(edgeA);
        pathCheckHelper(p1, start, expected, 12);
        Path p2 = p1.extendBy(edgeB);
        pathCheckHelper(p1, start, expected, 12);
        expected.add(edgeB);
        pathCheckHelper(p2, start, expected, 27);
    }

    @Test
    public void test_toString() {
        assertEquals("{start}", empty.toString());

        Path p1 = empty.extendBy(e1);
        assertEquals("{start -> middle1(1)}", p1.toString());

        Path p2 = p1.extendBy(e2);
        assertEquals("{start -> middle1(1) -> middle2(7)}", p2.toString());

        Path p3 = p2.extendBy(e3);
        assertEquals("{start -> middle1(1) -> middle2(7) -> end(12)}", p3.toString());
    }
    @Test
    public void test_constructor_null_loc(){
        assertThrows(NullPointerException.class, () -> new Path(null));
    }

    @Test
    public void test_hashCode_equals() {
        Path empty2 = new Path(start);
        assertEquals(empty.hashCode(), empty2.hashCode());
        assertEquals(empty, empty2);
        Path p1 = empty.extendBy(e1).extendBy(e2);
        Path p2 = empty.extendBy(e1).extendBy(e2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertEquals(p1, p2);
        assertNotEquals(empty.hashCode(), p1.hashCode());
        assertNotEquals(empty, p1);
        assertNotEquals(empty, null);
        assertEquals(empty, empty);
        assertNotEquals(empty, start);
    }

}