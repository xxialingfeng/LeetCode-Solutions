/**
 * This is a collection of leetcode problems related to sliding window.
 */
public class slidingWindow {

  /**
   * Leetcode 674 : Longest Continuous Increasing Subsequence.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int
   */
  public int findLengthOfLCIS(int[] nums) {
    int left = 0;
    int right = 1;
    int max = 1;
    while (right < nums.length) {
      if (nums[right] > nums[right - 1]) {
        right++;
        max = Math.max(max, right - left);
      } else {
        left = right;
        right++;
      }
    }
    return max;
  }
}
