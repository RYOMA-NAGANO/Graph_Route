package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


public class LocationTest {
  Location rohan = new Location("Rohan");
  Location mordor = new Location("Mordor");
  Location gondor = new Location("Gondor");
  @Test
  public void test_getName() {
    assertEquals("Gondor", gondor.getName());
  }

  @Test
  public void test_toString() {
    assertEquals("Mordor", mordor.toString());
  }

  private void checkEdgeIterable(Iterable<Edge> it, Edge... expected) {
    TestUtils.checkIterableWithoutOrder(it, expected);
  }

  @Test
  public void test_getEdges() {
    checkEdgeIterable(rohan.getEdges());
    Edge e1 = new Edge(mordor, 99);
    Edge e2 = new Edge(rohan, 37);
    Edge e3 = new Edge(gondor, 37);
    Edge e4 = new Edge(gondor,99);
    rohan.addEdge(e3);
    gondor.addEdge(e1);
    gondor.addEdge(e2);
    mordor.addEdge(e4);
    checkEdgeIterable(rohan.getEdges(), e3);
    checkEdgeIterable(gondor.getEdges(), e1, e2);
    checkEdgeIterable(gondor.getEdges(), e2, e1);
    checkEdgeIterable(mordor.getEdges(), e4);
  }
  @Test
  public void test_duplicate_dest() {
    rohan.addEdge(new Edge(gondor, 12));
    assertThrows(IllegalArgumentException.class, ()->rohan.addEdge(new Edge(gondor, 57)));
  }
}
