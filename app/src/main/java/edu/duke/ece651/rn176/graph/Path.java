package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path {
    private final Location start;
    private final List<Edge> edges;
    private final int totalWeight;
    public Path(Location start){
        this.start = start;
        this.edges = new ArrayList<>();
        this.totalWeight = 0;
    }
    private Path(Location start, List<Edge> oldEdges, int oldWeight, Edge newEdge){
        this.start = start;
        this.edges = new ArrayList<>(oldEdges);
        this.edges.add(newEdge);
        this.totalWeight = oldWeight + newEdge.getWeight();
    }
    public Path extendBy(Edge e){
        return new Path(start, edges, totalWeight, e);
    }
    public Iterable<Edge> getEdges(){
        return edges;
    }
    public Location getStart(){
        return start;
    }
    public int getTotalWeight(){
        return totalWeight;
    }
}
