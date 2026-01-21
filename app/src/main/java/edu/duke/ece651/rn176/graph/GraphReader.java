package edu.duke.ece651.rn176.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class GraphReader {
     private final Reader input;
     public GraphReader(Reader input) {
         this.input = input;
     }
     void parseLine(String line, Graph ans) {
         line =  line.trim();//get rid of leading/trailing whitespace
         if (line.length() == 0) return;//silently ignore blank lines
         String[] parts = line.split(":");
         if (parts.length % 2 != 1){
             throw new IllegalArgumentException("Input file does not properly pair " + "destination:weight on '"+line+"'");
         }
         Location start = ans.getOrCreate(parts[0], true);
         for(int i = 1; i < parts.length; i+=2){
             Location dest = ans.getOrCreate(parts[i], false);
             int weight = Integer.parseInt(parts[i+1]);
             start.addEdge(new Edge(dest, weight));
         }

     }
     public Graph readGraph() throws IOException {
         Graph ans = new Graph();
         try (BufferedReader br = new BufferedReader(input)) {
             String line;
             while ((line = br.readLine()) != null) {
                 parseLine(line, ans);
             }
         }
         return ans;
     }

}
