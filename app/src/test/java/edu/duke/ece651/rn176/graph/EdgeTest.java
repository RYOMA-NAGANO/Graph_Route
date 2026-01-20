package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EdgeTest {
  @Test
  public void test_all() {
    Location testLocation = new Location("Roshar");
    Edge testEdge = new Edge(testLocation, 42);
    assertEquals(testLocation, testEdge.getDestination());
    assertEquals(42, testEdge.getWeight());
    assertEquals("42 -> Roshar", testEdge.toString()); 
  }

}
