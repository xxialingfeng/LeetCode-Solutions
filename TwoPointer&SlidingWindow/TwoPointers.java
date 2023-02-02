import java.util.ArrayList;
import java.util.List;

public class TwoPointers {

  /**
   * Leetcode 2104 : Sum of Subarray Ranges.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return long
   */
  public long subArrayRanges(int[] nums) {
    long ansMin = 0;
    long ansMax = 0;
    for (int i = 0; i < nums.length; i++) {
      int left = i - 1;
      int right = i + 1;
      while (left >= 0 && nums[left] >= nums[i]) {
        left--;
      }
      while (right < nums.length && nums[right] > nums[i]) {
        right++;
      }
      ansMin += (long) (i - left) * (right - i) * nums[i];
    }
    for (int i = 0; i < nums.length; i++) {
      int left = i - 1;
      int right = i + 1;
      while (left >= 0 && nums[left] <= nums[i]) {
        left--;
      }
      while (right < nums.length && nums[right] < nums[i]) {
        right++;
      }
      ansMax += (long) (i - left) * (right - i) * nums[i];
    }
    return ansMax - ansMin;
  }

  /**
   * Leetcode 830 : Positions of Large Groups.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @return list of list of integer
   */
  public List<List<Integer>> largeGroupPositions(String s) {
    List<List<Integer>> ans = new ArrayList<>();
    int left = 0;
    int right = 0;
    while (right < s.length()) {
      while (right < s.length() && s.charAt(right) == s.charAt(left)) {
        right++;
      }
      if (right - left >= 3) {
        List<Integer> curr = new ArrayList<>();
        curr.add(left);
        curr.add(right - 1);
        ans.add(curr);
      }
      left = right;
    }
    return ans;
  }

}
