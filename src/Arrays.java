import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A collection of Leetcode problems related to Arrays.
 * @author xialingfeng
 */
public class Arrays {

  /**.
   * @leetcode 500: Keyboard Row.
   * @Difficulty: Easy
   * @OptimalComplexity: O(L) & O(26).
   */
  //Intuition
  public String[] findWords(String[] words) {
    List<String> ans = new ArrayList<>();
    Set<Character> Up = new HashSet<>();
    Set<Character> Mid = new HashSet<>();
    Set<Character> Down = new HashSet<>();
    String firstLine = "qwertyuiop";
    String secondLine = "asdfghjkl";
    String thirdLine = "zxcvbnm";
    char[] temp1 = firstLine.toCharArray();
    for (char chr : temp1) {
      Up.add(chr);
    }
    char[] temp2 = secondLine.toCharArray();
    for (char chr : temp2) {
      Mid.add(chr);
    }
    char[] temp3 = thirdLine.toCharArray();
    for (char chr : temp3) {
      Down.add(chr);
    }
    for (String word : words) {
      if (isValid(word, Up) || isValid(word, Mid) || isValid(word, Down)) {
        ans.add(word);
      }
    }
    String[] res = new String[ans.size()];
    for (int i = 0; i < ans.size(); i++) {
      res[i] = ans.get(i);
    }
    return res;
  }

  /**.
   * @param str String
   * @param set HashSet
   * @return if all the characters can be found within set.
   */
  public boolean isValid(String str, Set<Character> set) {
    for (int i = 0; i < str.length(); i++) {
      if (!set.contains(Character.toLowerCase(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }
  //Best Solution

  /**.
   * Best solution with every character indexed
   * @param words String[]
   * @return String[]
   */
  public String[] OptimalFindWords(String[] words) {
    List<String> list = new ArrayList<String>();
    String rowIdx = "12210111011122000010020202";
    for (String word : words) {
      boolean isValid = true;
      char idx = rowIdx.charAt(Character.toLowerCase(word.charAt(0)) - 'a');
      for (int i = 1; i < word.length(); ++i) {
        if (rowIdx.charAt(Character.toLowerCase(word.charAt(i)) - 'a') != idx) {
          isValid = false;
          break;
        }
      }
      if (isValid) {
        list.add(word);
      }
    }
    String[] ans = new String[list.size()];
    for (int i = 0; i < list.size(); ++i) {
      ans[i] = list.get(i);
    }
    return ans;
  }

  /**.
   * Leetcode 519: Random Flip Matrix.
   * @Difficulty: Medium
   * @OptimalComplexity: O(F) & O(F)
   */
  Map<Integer, Integer> map519 = new HashMap<>();
  int m519;
  int n519;
  int total519;
  Random random = new Random();

  /**.
   * Constructor
   * @param m int
   * @param n int
   */
  public Arrays(int m, int n) {
    this.m519 = m;
    this.n519 = n;
    this.total519 = m * n;
  }

  /**.
   * Array mapping, 2D -> 1D (i, j) -> i * n + j
   * @return int[]
   */
  public int[] flip() {
    int x = random.nextInt(total519);
    total519--;
    int idx = map519.getOrDefault(x, x);
    map519.put(x, map519.getOrDefault(total519, total519));
    return new int[]{idx / n519, idx % n519};
  }

  /**.
   * Reset array
   */
  public void reset() {
    total519 = m519 * n519;
    map519.clear();
  }

  /**
   * Leetcode 554: Brick Wall.
   * @param wall List<List<Integer>>
   * @return int
   */
  public int leastBricks(List<List<Integer>> wall) {
    int max = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (List<Integer> row : wall) {
      int sum = 0;
      for (int i = 0; i < row.size() - 1; i++) {
        sum += row.get(i);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        max = Math.max(max, map.get(sum));
      }
    }
    return wall.size() - max;
  }
}
