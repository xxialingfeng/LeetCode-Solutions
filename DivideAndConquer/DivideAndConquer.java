public class DivideAndConquer {

  /**
   * Leetcode 152 : Maximum Product Subarray.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int
   */
  public int maxProduct(int[] nums) {
    return divideAndConquer(nums, 0, nums.length - 1);
  }

  /**
   * DC for leetcode 152.
   * @param nums int[]
   * @param l int
   * @param r int
   * @return int
   */
  public int divideAndConquer(int[] nums, int l, int r) {
    int n = nums.length;
    int mult = 1;
    if (l >= r) {
      return l == r ? nums[l] : Integer.MIN_VALUE;
    }
    for (int i = l; i <= r; i++) {
      if (nums[i] == 0) {
        return Math.max(Math.max(divideAndConquer(nums, l, i - 1), divideAndConquer(nums, i + 1, r)), 0);
      }
      mult *= nums[i];
    }

    if (mult > 0) {
      return mult;
    }
    int max = Integer.MIN_VALUE;
    int curr = 1;
    while (curr > 0) {
      curr *= nums[l++];
    }
    max = Math.max(max,mult / curr);
    curr = 1;
    while (curr > 0) {
      curr *= nums[r--];
    }
    max = Math.max(max,mult / curr);
    return max;
  }

  /**
   * Leetcode 856 : Score of Parentheses.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @return int
   */
  public int scoreOfParentheses(String s) {
    if (s.length() == 2) {
      return 1;
    }
    int score = 0;
    int len = 0;
    for (int i = 0; i < s.length(); i++) {
      score += s.charAt(i) == '(' ? 1 : -1;
      if (score == 0) {
        len = i + 1;
        break;
      }
    }
    if (len == s.length()) {
      return 2 * scoreOfParentheses(s.substring(1, s.length() - 1));
    } else {
      return scoreOfParentheses(s.substring(0, len)) + scoreOfParentheses(s.substring(len));
    }
  }
}
