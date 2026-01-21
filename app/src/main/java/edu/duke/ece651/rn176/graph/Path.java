package edu.duke.ece651.rn176.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path {
    private final Location start;
    private final List<Edge> edges;
    private final int totalWeight;
    public Path(Location start){
        this(start, new ArrayList<>(), 0);
    }

    private Path(Location start, List<Edge> oldEdges, int oldWeight, Edge newEdge){
        this(start, new ArrayList<>(oldEdges), oldWeight + newEdge.getWeight());
        this.edges.add(newEdge);
    }

    private Path(Location start, List<Edge> edges, int totalWeight){
        if (start == null){
            throw new NullPointerException("The starting node of a path must be non-null");
        }
        this.start = start;
        this.edges = edges;
        this.totalWeight = totalWeight;
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
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("{");
        ans.append(start.getName());
        for (Edge e: edges){
            ans.append(" -> ");
            ans.append(e.getDestination().getName());
            ans.append("(");
            ans.append(e.getWeight());
            ans.append(")");
        }
        ans.append("}");
        return ans.toString();
    }
    @Override
    public int hashCode(){
        final int prime = 1;
        int result = 1;
        result = prime * result + edges.hashCode();
        result = prime * result + start.hashCode();
        return result;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Path other = (Path) obj;
        return (edges.equals(other.edges) && start.equals(other.start));
    }
}
