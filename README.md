# Graph Route

`Graph Route` is a Java command-line application that reads a weighted directed graph from a text file and prints the shortest path between two named locations.

The project is built with Gradle, targets Java 21, and includes JUnit tests for graph parsing, pathfinding, and CLI behavior.

## Features

- Reads graphs from a simple text format.
- Models locations, weighted edges, and paths as Java classes.
- Computes shortest paths using a priority-queue-based search.
- Reports helpful errors for bad input, missing files, and unknown locations.
- Includes unit tests for the core graph logic and application entry point.

## Requirements

- Java 21
- The included Gradle wrapper (`gradlew` or `gradlew.bat`)

## Project Layout

```text
app/src/main/java/edu/duke/ece651/rn176/graph/
  App.java          CLI entry point
  Graph.java        graph storage and shortest-path search
  GraphReader.java  parser for graph files
  Location.java     graph node
  Edge.java         weighted directed edge
  Path.java         path value object

app/src/test/
  java/...          JUnit test suite
  resources/graphs/ sample graph inputs
```

## Graph File Format

Each line defines one location and its outgoing edges:

```text
Location:Destination1:Weight1:Destination2:Weight2:...
```

Examples:

```text
Durham:Philadelphia:370:Dallas:1039:Louisville:508
Philadelphia:Durham:372:Louisville:628:Montreal:408
F
```

Notes:

- A line with only a location name, such as `F`, defines a node with no outgoing edges.
- Blank lines are ignored.
- Edge weights are parsed as integers.
- The graph is directed, so `A:B:5` does not automatically create `B:A:5`.
- Every referenced destination must also appear as a defined location somewhere in the file.

## Build

From the repository root:

```bash
./gradlew build
```

On Windows PowerShell:

```powershell
.\gradlew.bat build
```

## Run Tests

```bash
./gradlew test
```

On Windows PowerShell:

```powershell
.\gradlew.bat test
```

## Run The App

The application expects exactly 3 command-line arguments:

1. The graph file path
2. The source location name
3. The destination location name

Example:

```bash
./gradlew run --args="app/src/test/resources/graphs/graph2.txt A F"
```

On Windows PowerShell:

```powershell
.\gradlew.bat run --args="app/src/test/resources/graphs/graph2.txt A F"
```

Expected output:

```text
{A -> B(1) -> C(5) -> E(1) -> F(10)}
```

If no route exists, the program prints:

```text
No path
```

## Error Handling

The CLI reports common problems on standard error, including:

- incorrect number of command-line arguments
- an unreadable or missing graph file
- a source or destination location that does not exist
- malformed graph input
- destination nodes that were referenced but never defined

## Implementation Notes

- `GraphReader` parses the input file and validates that all referenced locations are defined.
- `Graph.shortestPath(...)` uses a priority queue to return the lowest-cost path it finds.
- The shortest-path logic is intended for non-negative edge weights.

## Development

This project uses:

- Gradle application plugin for building and running the CLI
- JUnit 5 for testing
- Clover plugin configuration for code coverage reporting
