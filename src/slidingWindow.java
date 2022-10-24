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

  /**
   * Leetcode 680 : Valid Palindrome II.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param s String
   * @return boolean
   */
  public boolean validPalindrome(String s) {
    if (isPalin(s)) {
      return true;
    }
    int l = 0;
    int r = s.length() - 1;
    while (l < r) {
      if (s.charAt(l) != s.charAt(r)) {
        return isPalin(s.substring(l, r)) || isPalin(s.substring(l + 1, r + 1));
      }
      l++;
      r--;
    }
    return false;
  }

  /**
   * return if it's palindrome.
   * @param s string
   * @return boolean
   */
  public boolean isPalin(String s) {
    int left = 0;
    int right = s.length() - 1;
    while (left < right) {
      if (s.charAt(left) != s.charAt(right)) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }
}
