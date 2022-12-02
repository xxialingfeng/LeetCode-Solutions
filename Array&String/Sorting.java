/**
 * A collection of leetcode problems related to Sorting.
 */
public class Sorting {

  /**
   * Leetcode 31 : Next Permutation.
   * @Difficulty: Medium
   * @OptimalComplexity: O(N) & O(1)
   * @param nums int[]
   */
  public void nextPermutation(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    if (i >= 0) {
      int j = nums.length - 1;
      while (j >= 0 && nums[i] >= nums[j]) {
        j--;
      }
      swap(nums, i, j);
    }
    reverse(nums, i + 1);
  }

  /**
   * Swap elements.
   * @param crr int[]
   * @param i int
   * @param j int
   */
  public void swap(int[] crr, int i, int j) {
    int temp = crr[i];
    crr[i] = crr[j];
    crr[j] = temp;
  }

  /**
   * swap elements int char[].
   * @param nums char[]
   * @param i int
   * @param j int
   */
  public void swap(char[] nums, int i, int j) {
    char temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /**
   * Reverse int[].
   * @param arr int[]
   * @param left int
   */
  public void reverse(int[] arr, int left) {
    int i = left;
    int j = arr.length - 1;
    while (i < j) {
      swap(arr, i, j);
      i++;
      j--;
    }
  }

  /**
   * Reverse char[].
   * @param nums char[]
   * @param begin int
   */
  public void reverse(char[] nums, int begin) {
    int i = begin;
    int j = nums.length - 1;
    while (i < j) {
      swap(nums, i, j);
      i++;
      j--;
    }
  }

  /**
   * Leetcode 556: Next Greater Element III.
   * @Difficulty: Medium
   * @OptimalComplexity: O(longn) & O(longn)
   * @param n int
   * @return int
   */
  public int nextGreaterElement(int n) {
    char[] nums = Integer.toString(n).toCharArray();
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    if (i < 0) {
      return -1;
    }

    int j = nums.length - 1;
    while (j >= 0 && nums[i] >= nums[j]) {
      j--;
    }
    swap(nums, i, j);
    reverse(nums, i + 1);
    long ans = Long.parseLong(new String(nums));
    return ans > Integer.MAX_VALUE ? -1 : (int) ans;
  }


}
