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
    public Location getLocation(String name){
        return nodes.get(name);
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
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("<");
        String sep = "";
        ArrayList<String> keys = new ArrayList<>(defedLocs);
        Collections.sort(keys);
        for(String s: keys){
            ans.append(sep);
            ans.append(s);
            sep = ",";
        }
        ans.append(">");
        return ans.toString();
    }

    public Path shortestPath(Location start, Location end){
        Path empty = new Path(start);
        PriorityQueue<Path> paths = new PriorityQueue<>();
        paths.add(empty);
        HashSet<Location> visited = new HashSet<>();
        while(paths.size() > 0){
            Path curr = paths.remove();
            Location last = curr.getEnd();
            if(last.equals(end)){
                return curr;
            }
            if(visited.contains(last)){
                continue;
            }
            visited.add(last);
            for(Edge e: last.getEdges()){
                Path newPath = curr.extendBy(e);
                paths.add(newPath);
            }
        }
        return null;
    }
}
