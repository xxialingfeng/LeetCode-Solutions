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
}
