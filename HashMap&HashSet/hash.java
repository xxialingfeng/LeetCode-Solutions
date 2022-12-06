import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

  /**
   * Leetcode 763 : Partition Labels.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param s String
   * @return list of integer
   */
  public List<Integer> partitionLabels(String s) {
    List<Integer> ans = new ArrayList<>();
    Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      map.put(c, i + 1);
    }
    char c = s.charAt(0);
    int idx = map.get(c);
    ans.add(idx);
    int lastMaxIndex = map.get(s.charAt(0));
    for (int i = 1; i < s.length(); i++) {
      int currMaxIndex = map.get(s.charAt(i));
      if (currMaxIndex <= lastMaxIndex) {
        continue;
      } else if (i + 1 < lastMaxIndex && currMaxIndex > lastMaxIndex) {
        int temp = ans.get(ans.size() - 1);
        ans.remove(ans.size() - 1);
        ans.add(temp + currMaxIndex - lastMaxIndex);
        lastMaxIndex = currMaxIndex;
      } else {
        lastMaxIndex = currMaxIndex;
        ans.add(currMaxIndex - i);
      }
    }
    return ans;
  }

  /**
   * Leetcode 765 : Couples Holding Hands.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param row int[]
   * @return int
   */
  public int minSwapsCouples(int[] row) {
    int n = row.length;
    int pair = n / 2;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < row.length; i++) {
      map.put(row[i], i);
    }
    int ans = 0;
    for (int i = 0; i < row.length - 1; i += 2) {
      if (row[i] % 2 == 0) {
        if (row[i + 1] != row[i] + 1) {
          int idx = map.get(row[i] + 1);
          map.put(row[i + 1], idx);
          map.put(row[i] + 1, i + 1);
          exchange(row, i + 1, idx);
          ans++;
        }
      } else {
        if (row[i + 1] != row[i] - 1) {
          int idx = map.get(row[i] - 1);
          map.put(row[i + 1], idx);
          map.put(row[i] - 1, i + 1);
          exchange(row, i + 1, idx);
          ans++;
        }
      }
    }
    return ans;
  }

  /**
   * exchange index.
   * @param row int[]
   * @param i int
   * @param j int
   */
  public void exchange(int[] row, int i, int j) {
    int temp = 0;
    temp = row[i];
    row[i] = row[j];
    row[j] = temp;
  }

  /**
   * Leetcode 768 : Max Chunks To Make Sorted II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(m) & O(m)
   * @param arr int[]
   * @return int
   */
  public int maxChunksToSorted(int[] arr) {
    int[] copy = new int[arr.length];
    System.arraycopy(arr, 0, copy, 0, arr.length);
    Arrays.sort(copy);
    int ans = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      int ori = arr[i];
      int sorted = copy[i];
      map.put(ori, map.getOrDefault(ori, 0) + 1);
      if (map.get(ori) == 0) {
        map.remove(ori);
      }
      map.put(sorted, map.getOrDefault(sorted, 0) - 1);
      if (map.get(sorted) == 0) {
        map.remove(sorted);
      }
      if (map.size() == 0) {
        ans++;
      }
    }
    return ans;
  }
}
