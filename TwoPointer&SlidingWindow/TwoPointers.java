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

  /**
   * Leetcode 845 : Longest Mountain in Array.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(1)
   * @param arr int[]
   * @return int
   */
  public int longestMountain(int[] arr) {
    int i = 0;
    int ans = 0;
    while (i < arr.length) {
      int left = i - 1;
      int right = i + 1;
      while (left >= 0 && arr[left] < arr[left + 1]) {
        left--;
      }
      while (right < arr.length && arr[right] < arr[right - 1]) {
        right++;
      }
      if (left == i - 1 || right == i + 1) {
        i++;
        continue;
      }
      ans = Math.max(ans, right - left - 1);
      i++;
    }
    return ans >= 3 ? ans : 0;
  }

}
