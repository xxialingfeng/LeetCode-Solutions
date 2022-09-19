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
}
