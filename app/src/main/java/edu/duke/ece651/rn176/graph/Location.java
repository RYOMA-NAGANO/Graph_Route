package edu.duke.ece651.rn176.graph;

public class Location {
  private final String name;

  public Location(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }
  
}
