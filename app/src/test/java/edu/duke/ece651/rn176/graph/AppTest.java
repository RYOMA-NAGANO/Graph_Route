package edu.duke.ece651.rn176.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private void runMainAndCheck(String expectedOut,
                                 String expectedErr,
                                 String... args){
        if(args != null && args.length > 0){
            args[0] = getClass().getClassLoader().getResource(args[0]).getPath();
        }
        runMainAndCheckRaw(expectedOut, expectedErr, args);
    }
    private void runMainAndCheckRaw(String expectedOut,
                                 String expectedErr,
                                 String... args) {
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        try {
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outBytes));
            ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errBytes));
            App.main(args);
            String extraInfo = "System.out: " + outBytes + "\nSystem.err: " + errBytes + "\n";
            assertEquals(expectedOut, outBytes.toString());
            assertEquals(expectedErr, errBytes.toString());
        }
        finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
        }
    }

    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT,
            mode = ResourceAccessMode.READ_WRITE)
    @ResourceLock(value = Resources.SYSTEM_ERR,
            mode = ResourceAccessMode.READ_WRITE)
    public void test_main() {
        runMainAndCheck("{A -> B(1) -> C(5) -> E(1) -> F(10)}" + System.lineSeparator(), "", "graphs/graph2.txt", "A", "F");
        runMainAndCheck("", "There is no Location named G" + System.lineSeparator(), "graphs/graph2.txt", "A", "G");
        String usage = "This program requires three command line arguments:" + System.lineSeparator() +
                "  1. The graph file to read" +  System.lineSeparator() +
                "  2. The source location name" + System.lineSeparator() +
                "  3. The destination location name"  + System.lineSeparator();
        runMainAndCheck("", usage, "graphs/graph2.txt", "A");
        runMainAndCheck("", usage, "graphs/graph2.txt", "A", "B", "C");
        runMainAndCheck("No path" + System.lineSeparator(), "", "graphs/graph2.txt", "F", "A");
        runMainAndCheck("", "Could not read file invalid.txt (No such file or directory)\n", "invalid.txt", "A", "G");
    }

}