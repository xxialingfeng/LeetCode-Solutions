import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This is a collection of leetcode problems related to hash.
 */
class hash {
  Map<Integer, Integer> map = new HashMap<>();
  int bound;
  Random random = new Random();

  /**
   * Leetcode 710 : Random Pick with Blacklist.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & o(1)
   * @param n int
   * @param blacklist int[]
   */
  public hash(int n, int[] blacklist) {
    int m = blacklist.length;
    bound = n - m;
    Set<Integer> set = new HashSet<>();
    for (int i : blacklist) {
      if (i >= bound) {
        set.add(i);
      }
    }
    int w = bound;
    for (int i : blacklist) {
      if (i < bound) {
        while (set.contains(w)) {
          w++;
        }
        map.put(i, w);
        w++;
      }
    }
  }

  public int pick() {
    int x = random.nextInt(bound);
    return map.getOrDefault(x, x);
  }

  /**
   * Leetcode 729 : My Calendar I.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   */
  static class MyCalendar {
    Map<Integer, Integer> map;

    /**
     * my calendar class.
     */
    public MyCalendar() {
      map = new HashMap<>();
    }

    public boolean book(int start, int end) {
      for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int s = entry.getKey();
        int e = entry.getValue();
        if (start >= s && start < e) {
          return false;
        }
        if (end > s && start < s) {
          return false;
        }
      }
      map.put(start, end);
      return true;
    }
  }

}
