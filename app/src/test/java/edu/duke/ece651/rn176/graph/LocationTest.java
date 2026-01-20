package edu.duke.ece651.rn176.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LocationTest {
  @Test
  public void test_getName() {
    Location testLoc = new Location("Gondor");
    assertEquals("Gondor", testLoc.getName());
  }

  @Test
  public void test_toString() {
    Location testLocation = new Location("Mordor");
    assertEquals("Mordor", testLocation.toString());
  }
}
