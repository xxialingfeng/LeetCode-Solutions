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
}
