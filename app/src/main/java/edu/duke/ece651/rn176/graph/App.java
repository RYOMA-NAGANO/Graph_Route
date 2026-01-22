package edu.duke.ece651.rn176.graph;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class App {
    static void reportUsage(PrintStream err) {
        err.println("This program requires three command line arguments:");
        err.println("1. The graph file to read");
        err.println("2. The source location name");
        err.println("3. The destination location name");
    }
    public static Location getLocation(Graph g, String name) {
        Location ans = g.getLocation(name);
        if (ans == null) {
            throw new NoSuchElementException(
                    "There is no Location named " + name
            );
        }
        return ans;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            reportUsage(System.err);
            return;
        }

        try (FileReader fr = new FileReader(args[0])) {
            GraphReader gr = new GraphReader(fr);
            Graph g = gr.readGraph();

            Location start = getLocation(g, args[1]);
            Location end = getLocation(g, args[2]);

            Path ans = g.shortestPath(start, end);
            System.out.println(ans == null ? "No path" : ans.toString());
        }
        catch (IOException ioe) {
            System.err.println("Could not read file " + args[0] + ": "
                    + ioe.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
