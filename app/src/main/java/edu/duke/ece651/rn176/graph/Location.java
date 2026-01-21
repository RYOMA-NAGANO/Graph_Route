package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class represents a Location (i.e., Node) in our
 * graph. Each location has a name, and 0 or more
 * {@link edu.duke.ece651.rn176.graph.Edge Edges}
 * connecting it to other Locations in the graph.
 * @author Ryoma Nagano
 * @version 1.0
 */
public class Location {
  private final String name;
  private final Map<Location, Edge> edges;

  /**
   * Constructs a Location with the given name, and
   * no outgoing edges.
   * @param name is the name of this Location
   */
  public Location(String name) {
    this.name = name;
    this.edges = new HashMap<>();
  }

  public Iterable<Edge> getEdges() {
    return edges.values();
  }
  /**
   * Adds an Edge to the outgoing edges of this Location.
   * @param e is the Edge to add
   * @throws IllegalArgumentException if this Location
   *         already has an outgoing Edge to e's destination
   */
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
