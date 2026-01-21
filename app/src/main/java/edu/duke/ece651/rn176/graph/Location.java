package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
  private final String name;
  private final Map<Location, Edge> edges;

  public Location(String name) {
    this.name = name;
    this.edges = new HashMap<>();
  }

  public Iterable<Edge> getEdges() {
    return edges.values();
  }

  public void addEdge(Edge e) {
    Location dst = e.getDestination();
    if(edges.get(dst) != null){
        throw new IllegalArgumentException("Attempt to add duplicate edge from " + this + " to " + e.getDestination());
      }
    edges.put(dst,e);
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }

}
