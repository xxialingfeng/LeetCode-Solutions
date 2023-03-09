import java.util.Arrays;

/**
 * A collection of leetcode problems related to subsequence problems.
 */
public class subsequence {

  /**
   * Leetcode 891 : Sum of Subsequence Widths.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(logn) -> stack space
   * @param nums int[]
   * @return int
   */
  public int sumSubseqWidths(int[] nums) {
    if (nums.length == 1) {
      return 0;
    }
    Arrays.sort(nums);
    long sum = nums[0];
    long pow = 1;
    int mod = 1000000007;
    int size = nums.length;
    for (int i = 1; i < size; i++) {
      pow = pow * 2 % mod;
      sum = (sum + pow * nums[i]) % mod;
    }

    sum -= nums[size - 1];
    pow = 1;
    for (int i = size - 2; i >= 0; i--) {
      pow = pow * 2 % mod;
      sum = (sum + mod - pow * nums[i] % mod) % mod;
    }
    return (int) sum;
  }
}
