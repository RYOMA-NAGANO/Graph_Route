package edu.duke.ece651.rn176.graph;

import java.util.*;

public class Graph {
    private final Map<String, Location> nodes;
    private Set<String> defedLocs;
    public Graph(){
        this.nodes = new HashMap<>();
        this.defedLocs = new HashSet<>();
    }
    public Location getOrCreate(String name, boolean def){
        if(def){
            if(this.defedLocs.contains(name)){
                throw new IllegalArgumentException("Duplicate definition of " + name);
            }
            defedLocs.add(name);
        }
        Location answer = nodes.get(name);
        if(answer == null){
            answer = new Location(name);
            nodes.put(name, answer);
        }
        return answer;
    }
    public Iterable<Location> getLocations(){
        return nodes.values();
    }
    public Collection<String> getUndefinedNames(){
        ArrayList<String> answer = new ArrayList<>();
        for(String s: nodes.keySet()){
            if(!defedLocs.contains(s)){
                answer.add(s);
            }
        }
        return answer;
    }
}
