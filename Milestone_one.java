import java.util.Random;

public class Milestone_one {

    private final int n;        // how many numbers we want
    private final int range;    // biggest number allowed (4 times n)
    private final Random rng;   // the random number maker

    // counts how many comparisons we do while checking for repeats
    private long comparisons = 0;

    // use this one for a normal random list
    public Milestone_one(int n) {
        this(n, new Random());
    }

    // use this one if you want the same list every time
    public Milestone_one(int n, long seed) {
        this(n, new Random(seed));
    }

    private Milestone_one(int n, Random rng) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be at least 1");
        }
        this.n = n;
        this.range = 4 * n;
        this.rng = rng;
    }

    // makes the array of n different random numbers
    public int[] generate() {
        int[] a = new int[n];
        comparisons = 0;
        int filled = 0;   // how many numbers are saved so far

        while (filled < n) {
            // pick a random number from 1 to 4n
            int candidate = rng.nextInt(range) + 1;

            // look through the saved numbers to see if we already have it
            boolean duplicate = false;
            for (int j = 0; j < filled; j++) {
                comparisons++;
                if (a[j] == candidate) {
                    duplicate = true;
                    break;   // found a repeat, stop looking
                }
            }

            // if it is new, save it. if not, pick again.
            if (!duplicate) {
                a[filled] = candidate;
                filled++;
            }
        }
        return a;
    }

    public long getComparisons() {
        return comparisons;
    }

    public static void main(String[] args) {
        // make 25 numbers and print them
        int n = 25;
        Milestone_one gen = new Milestone_one(n);
        int[] data = gen.generate();

        System.out.println("n = " + n + ", values drawn from 1.." + (4 * n));
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + (i < data.length - 1 ? " " : "\n"));
        }
        System.out.println("Duplicate-check comparisons: " + gen.getComparisons());

        // comparisons divided by n squared should stay about the same
        System.out.println("\n   n      comparisons   comparisons / n^2");
        for (int m = 1000; m <= 16000; m *= 2) {
            Milestone_one g = new Milestone_one(m);
            g.generate();
            double ratio = (double) g.getComparisons() / ((double) m * m);
            System.out.printf("%6d %14d %14.4f%n", m, g.getComparisons(), ratio);
        }
    }
}