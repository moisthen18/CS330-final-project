import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

// Algorithm marks lines to change if test a different sort

public class Harness {

    static final int TRIALS = 30;   // how many times we repeat
    static final int[] SIZES = {250, 500, 1000, 2000, 4000, 8000};

    public static void main(String[] args) {
    	
    	// this lets us ask java for the CPU time
        ThreadMXBean cpu = ManagementFactory.getThreadMXBean();
        boolean cpuOk = cpu.isCurrentThreadCpuTimeSupported();

        // runs the sort a few times, so it let java warm up
        for (int w = 0; w < 5; w++) {
            int[] warm = new Milestone_one(2000).generate();
            new InsertionSort().sort(warm);                      // Algorithm
        }

        System.out.println("Algorithm: Insertion Sort   (basic operation: a[j] > key)");
        System.out.println("Trials per size: " + TRIALS + "   CPU time supported: " + cpuOk);
        System.out.println();
        System.out.printf("%6s %12s %12s %12s %12s %10s %10s%n",
                "n", "mean ops", "best ops", "worst ops", "mean/n^2", "CPU ms", "wall ms");

        for (int n : SIZES) {
            double totalOps = 0;  // add the comparison from every test
            long totalCpu = 0;  // add up the CPU time from every test
            long totalWall = 0; // adds up the wall-clock time from every test
            long bestOps = 0;
            long worstOps = 0;

            for (int t = 0; t < TRIALS; t++) {
            	
            	// get a new random list of of n #s
                int[] data = new Milestone_one(n).generate();    

                // average case: sort the random list, count and time it as well
                int[] copy = data.clone();
                InsertionSort sorter = new InsertionSort();      // Algorithm

                long c0 = cpuOk ? cpu.getCurrentThreadCpuTime() : 0; // this start the clock
                long w0 = System.nanoTime();
                sorter.sort(copy);                               // Algorithm
                long w1 = System.nanoTime();     // this stop the the clock
                long c1 = cpuOk ? cpu.getCurrentThreadCpuTime() : 0;

                totalOps += sorter.getComparisons();
                totalCpu += (c1 - c0);
                totalWall += (w1 - w0);

                // best case: it "copy" is now sorted, and sort it again 
                InsertionSort bestSorter = new InsertionSort();  // Algorithm
                bestSorter.sort(copy.clone());                   // Algorithm
                bestOps = bestSorter.getComparisons();

                // worst case: reverse/flip the sorted list , so it backward, and then sort it
                int[] rev = new int[n];
                for (int i = 0; i < n; i++) {
                    rev[i] = copy[n - 1 - i];
                }
                InsertionSort worstSorter = new InsertionSort(); // Algorithm
                worstSorter.sort(rev);                           // Algorithm
                worstOps = worstSorter.getComparisons();
            }

            double meanOps = totalOps / TRIALS;
            double cpuMs = totalCpu / (double) TRIALS / 1_000_000.0;
            double wallMs = totalWall / (double) TRIALS / 1_000_000.0;

            System.out.printf("%6d %12.0f %12d %12d %12.4f %10.3f %10.3f%n",
                    n, meanOps, bestOps, worstOps, meanOps / ((double) n * n), cpuMs, wallMs);
        }
    }
}