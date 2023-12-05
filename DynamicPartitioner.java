import java.util.Arrays;

public class DynamicPartitioner implements Partitioner {
    static long recursiveCalls = 0;

    int[] values;

    public DynamicPartitioner(int[] values) {
        this.values = values;
    }

    @Override
    public void run() {
        recursiveCalls = 0;
        findPartition(values, values.length);
    }

    @Override
    public long getRecursiveCalls() {
        return recursiveCalls;
    }

    // A utility function that returns true if there is
    // a subset of arr[] with sum equal to given sum
    static int isSubsetSum(int arr[], int n, int sum,
                           int[][] dp)
    {
        recursiveCalls++;
        // Base Cases
        if (sum == 0)
            return 1;
        if (n == 0 && sum != 0)
            return 0;

        // return solved subproblem
        if (dp[n][sum] != -1) {
            return dp[n][sum];
        }

        // If last element is greater than sum, then
        // ignore it
        if (arr[n - 1] > sum)
            return isSubsetSum(arr, n - 1, sum, dp);

        /* else, check if sum can be obtained by any of
                the following
                (a) including the last element
                (b) excluding the last element
        */
        // also store the subproblem in dp matrix
        if (isSubsetSum(arr, n - 1, sum, dp) != 0
            || isSubsetSum(arr, n - 1, sum - arr[n - 1], dp)
                   != 0)
            return dp[n][sum] = 1;
        return dp[n][sum] = 0;
        // return dp[n][sum] = isSubsetSum(arr, n - 1, sum,
        // dp) || isSubsetSum(arr, n - 1, sum - arr[n - 1],
        // dp);
    }

    // Returns true if arr[] can be partitioned in two
    // subsets of equal sum, otherwise false
    static int findPartition(int arr[], int n)
    {
        // Calculate sum of the elements in array
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += arr[i];

        // If sum is odd, there cannot be two subsets
        // with equal sum
        if (sum % 2 != 0)
            return 0;

        // To store overlapping subproblems
        int dp[][] = new int[n + 1][sum + 1];
        for (int row[] : dp)
            Arrays.fill(row, -1);

        // Find if there is subset with sum equal to
        // half of total sum
        return isSubsetSum(arr, n, sum / 2, dp);
    }
}
