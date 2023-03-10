import java.util.HashSet;
import java.util.Set;

/**
 * A collection of leetcode problems related to subarray
 */
public class subarray {

  /**
   * Leetcode 898 : Bitwise ORs of Subarrays.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(n)
   * @param arr int[]
   * @return int
   */
  public int subarrayBitwiseORs(int[] arr) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < arr.length; i++) {
      set.add(arr[i]);
      for (int j = i - 1; j >= 0; j--) {
        if ((arr[i] & arr[j]) == arr[i]) {
          break;
        }
        arr[j] |= arr[i];
        set.add(arr[j]);
      }
    }
    return set.size();
  }
}
