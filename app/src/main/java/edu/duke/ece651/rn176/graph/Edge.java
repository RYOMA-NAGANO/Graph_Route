package edu.duke.ece651.rn176.graph;

public class Edge {
  private final Location destination;
  private final int weight;

  public Edge(Location destination, int weight) {
    this.destination = destination;
    this.weight = weight;
  }

  public Location getDestination() {
    return destination;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return getWeight() + " -> " + getDestination();
  }

}
