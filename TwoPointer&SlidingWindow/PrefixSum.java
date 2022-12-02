import java.util.HashMap;
import java.util.Map;

/**
 * A collection for leetcode problems related to prefix sum
 */
public class PrefixSum {

  /**.
   * Leetcode 525: Contiguous Array
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int findMaxLength(int[] nums) {
    //prefix sum + hashmap is a great way to find the subarray which has the given value.
    //we can not use sliding window because it is not ">="
    int maxLength = 0;
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    int counter = 0;
    map.put(counter, -1);
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      int num = nums[i];
      if (num == 1) {
        counter++;
      } else {
        counter--;
      }
      if (map.containsKey(counter)) {
        int prevIndex = map.get(counter);
        maxLength = Math.max(maxLength, i - prevIndex);
      } else {
        map.put(counter, i);
      }
    }
    return maxLength;
  }

  /**.
   * Leetcode 523 : Continuous Subarray Sum
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param k int
   * @return boolean
   */
  public boolean checkSubarraySum(int[] nums, int k) {
    if (nums.length < 2) {
      return false;
    }
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      int mod = sum % k; //if the value mod appears again, it means the sum of the interval
      //equals n * k
      if (map.containsKey(mod)) {
        if (i - map.get(mod) > 1) {
          return true;
        }
      } else {
        map.put(mod, i);
      }
    }
    return false;
  }

  /**
   * Leetcode 689 : Maximum Sum of 3 Non-Overlapping Subarrays.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @param k int
   * @return int[]
   */
  public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
    int[] ans = new int[3];
    int sum1 = 0;
    int sum2 = 0;
    int sum3 = 0;
    int idx1 = 0;
    int idx2 = 0;
    int maxSum2 = 0;
    int maxSum1 = 0;
    int maxSum3 = 0;
    int maxSumIdx1 = 0;
    for (int i = 2 * k; i < nums.length; i++) {
      sum1 += nums[i - 2 * k];
      sum2 += nums[i - k];
      sum3 += nums[i];
      if (i >= 3 * k - 1) {
        if (sum1 > maxSum1) {
          maxSum1 = sum1;
          idx1 = i - 3 * k + 1;
        }
        if (maxSum1 + sum2 > maxSum2) {
          maxSum2 = maxSum1 + sum2;
          maxSumIdx1 = idx1;
          idx2 = i - 2 * k + 1;
        }
        if (maxSum2 + sum3 > maxSum3) {
          maxSum3 = maxSum2 + sum3;
          ans[0] = maxSumIdx1;
          ans[1] = idx2;
          ans[2] = i - k + 1;
        }
        sum1 -= nums[i - k * 3 + 1];
        sum2 -= nums[i - k * 2 + 1];
        sum3 -= nums[i - k + 1];
      }
    }
    return ans;
  }

  /**
   * Leetcode 724 : Find Pivot Index.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int pivotIndex(int[] nums) {
    int[] prefix = new int[nums.length];
    prefix[0] = nums[0];
    for (int i = 1; i < prefix.length; i++) {
      prefix[i] = prefix[i - 1] + nums[i];
    }
    int Sleft = 0;
    int Sright = 0;
    for (int i = 0; i < nums.length; i++) {
      Sleft = prefix[i] - nums[i];
      Sright = prefix[nums.length - 1] - prefix[i];
      if (Sleft == Sright) {
        return i;
      }
    }
    return -1;
  }
}
