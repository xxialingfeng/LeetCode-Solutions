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

  /**
   * Leetcode 849 : Maximize Distance to Closest Person.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(1)
   * @param seats int[]
   * @return int
   */
  public int maxDistToClosest(int[] seats) {
    int ans = 1;
    for (int i = 0; i < seats.length; i++) {
      if (seats[i] == 1) {
        continue;
      }
      if (i == 0) {
        int right = 1;
        while (right < seats.length && seats[right] == 0) {
          right++;
        }
        ans = Math.max(ans, right - i);
      } else if (i == seats.length - 1) {
        int left = seats.length - 2;
        while (left >= 0 && seats[left] == 0) {
          left--;
        }
        ans = Math.max(ans, i - left);
      } else {
        int left = i - 1;
        int right = i + 1;
        while (left >= 0 && seats[left] == 0) {
          left--;
        }
        while (right < seats.length && seats[right] == 0) {
          right++;
        }
        int temp = Math.min(i - left, right - i);
        ans = Math.max(ans, temp);
      }
    }
    return ans;
  }

  /**
   * Leetcode 1013 : Partition Array Into Three Parts With Equal Sum.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param arr int[]
   * @return boolean
   */
  public boolean canThreePartsEqualSum(int[] arr) {
    int sum = 0;
    for (int num : arr) {
      sum += num;
    }
    if (sum % 3 != 0) {
      return false;
    }
    int target = sum / 3;
    int[] prefix = new int[arr.length + 1];
    for (int i = 1; i < prefix.length; i++) {
      prefix[i] = prefix[i - 1] + arr[i - 1];
    }
    int l = 1;
    int r = arr.length - 1;
    while (l < prefix.length && prefix[l] != target) {
      l++;
    }
    while (r >= 0 && sum - prefix[r] != target) {
      r--;
    }
    if (r <= l) {
      return false;
    }
    return prefix[r] - prefix[l] == target;
  }

}
