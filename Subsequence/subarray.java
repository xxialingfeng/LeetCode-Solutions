import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A collection of leetcode problems related to subarray.
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

  /**
   * Leetcode 904 : Fruit Into Baskets.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param fruits int[]
   * @return int
   */
  public int totalFruit(int[] fruits) {
    Map<Integer, Integer> map = new HashMap<>();
    int l = 0;
    int r = 0;
    int ans = 0;
    while (r < fruits.length) {
      map.put(fruits[r], map.getOrDefault(fruits[r], 0) + 1);
      r++;
      while (map.size() > 2) {
        map.put(fruits[l], map.get(fruits[l]) - 1);
        if (map.get(fruits[l]) == 0) {
          map.remove(fruits[l]);
        }
        l++;
      }
      ans = Math.max(ans, r - l);
    }
    return ans;
  }
}
