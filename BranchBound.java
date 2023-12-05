import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BranchBound implements Partitioner {
    static long recursiveCalls = 0;

    int[] values;

    public BranchBound(int[] values) {
        this.values = values;
    }

    public static boolean[] attemptPartition(int[] values) {
        int totalSum = Arrays.stream(values).sum();
        // If the total sum is not even, partition is not possible
        if (totalSum % 2 != 0) return null;

        List<Boolean> best_assignment_ref = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) best_assignment_ref.add(false);
        boolean[] test_assignment = new boolean[values.length];

        List<Integer> best_err_ref = new ArrayList<>(1);
        best_err_ref.add(totalSum);
        partitionFromIndex(values, 0, totalSum, totalSum, test_assignment, 0, best_assignment_ref, best_err_ref);
        for (int i = 0; i < test_assignment.length; i++) {
            test_assignment[i] = best_assignment_ref.get(i);
        }
        return test_assignment;
    }

    private static void partitionFromIndex(int[] values, int start_index, int total_value, int unassigned_value, boolean[] test_assignment, int test_value, List<Boolean> best_assignment, List<Integer> best_err_arr) {
        int test_err = Math.abs(2 * test_value - total_value);
        if (start_index >= values.length) {
            int best_err = best_err_arr.get(0);
            if (test_err < best_err) {
                best_err_arr.set(0, test_err);
                for (int i = 0; i < values.length; i++) best_assignment.set(i, test_assignment[i]);
            }
            return;
        }
        if (test_err - unassigned_value < best_err_arr.get(0)) {
            unassigned_value -= values[start_index];
            test_assignment[start_index] = true;
            recursiveCalls++;
            partitionFromIndex(values, start_index + 1, total_value, unassigned_value, test_assignment, test_value + values[start_index], best_assignment, best_err_arr);

            test_assignment[start_index] = false;
            recursiveCalls++;
            partitionFromIndex(values, start_index + 1, total_value, unassigned_value, test_assignment, test_value, best_assignment, best_err_arr);
        }
    }
    @Override
    public void run() {
        recursiveCalls = 0;
        attemptPartition(values);
    }

    @Override
    public long getRecursiveCalls() {
        return recursiveCalls;
    }

}
