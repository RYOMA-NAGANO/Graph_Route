package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.List;

public class Location {
  private final String name;
  private final List<Edge> edges;

  public Location(String name) {
    this.name = name;
    this.edges = new ArrayList<>();
  }

  public Iterable<Edge> getEdges() {
    return edges;
  }

  public void addEdge(Edge e) {
    for (Edge e2 : edges) {
      if (e.getDestination().equals(e2.getDestination())) {
        throw new IllegalArgumentException("Attempt to add duplicate edge from " + this + " to " + e.getDestination());
      }
      edges.add(e);
    }
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }

}
