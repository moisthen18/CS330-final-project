public class InsertionSort {
	// Counts how many time operation runs 
    private long comparisons = 0;

    // sorts the array from smallest to largest
    public void sort(int[] a) {
        comparisons = 0;

        for (int i = 1; i < a.length; i++) {
            int key = a[i];     // the #s we are inserting
            int j = i - 1;

            // moves the bigger #s one spot to the right
            while (j >= 0) {
                comparisons++;    // counts comparison
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;  // so the key belong in this spot and stop looking
                }
            }
            a[j + 1] = key;  // put the key into its spot
        }
    }
    
    public long getComparisons() {
        return comparisons;
    }
}