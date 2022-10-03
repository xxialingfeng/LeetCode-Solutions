import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;

/**
 * A collection of Leetcode problems related to Arrays.
 * @author xialingfeng
 */
public class Array {

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
  public Array(int m, int n) {
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

  /**
   * Leetcode 561 : Array Partition.
   * @Difficulty: Easy
   * @OptimalComplexity: O(longn) & O(logn) sorting will use stack space which is log n
   * @param nums int[]
   * @return int
   */
  public int arrayPairSum(int[] nums) {
    Arrays.sort(nums);
    int sum = 0;
    for(int i = 0; i < nums.length; i += 2) {
      sum += nums[i];
    }
    return sum;
  }

  /**
   * Leetcode 565: Array Nesting.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int
   */
  public int arrayNesting(int[] nums) {
    boolean[] isUsed = new boolean[nums.length];
    int max = 0;
    for (int i = 0; i < nums.length; i++) {
      if (isUsed[i]) {
        continue;
      }
      int t = i;
      int count = 0;
      Set<Integer> set = new HashSet<>();
      while (!set.contains(nums[t])) {
        set.add(nums[t]);
        isUsed[t] = true;
        count++;
        t = nums[t];
      }
      max = Math.max(max, count);
    }
    return max;
  }

  /**
   * Leetcode 566: Reshape the Matrix.
   * @Difficulty: Easy
   * @OptimalComplexity: O(m*n) & O(m*n)
   * @param mat int[][]
   * @param r int
   * @param c int
   * @return int[][]
   */
  public int[][] matrixReshape(int[][] mat, int r, int c) {
    if (r * c != mat.length * mat[0].length) {
      return mat;
    }
    List<Integer> list = new ArrayList<>();
    for (int[] ints : mat) {
      for (int j = 0; j < mat[0].length; j++) {
        list.add(ints[j]);
      }
    }
    int[][] res = new int[r][c];
    int count = 0;
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        res[i][j] = list.get(count);
        count++;
      }
    }
    return res;
  }

  /**
   * Leetcode 575: Distribute Candies.
   * @Difficulty: Easy
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param candyType int[]
   * @return int
   */
  public int distributeCandies(int[] candyType) {
    Arrays.sort(candyType);
    int canEat = candyType.length / 2;
    int count = 1;
    for (int i = 1; i < candyType.length; i++) {
      if (candyType[i] != candyType[i-1]) {
        count++;
      }
    }
    return Math.min(count, canEat);
  }

  /**
   * Leetcode 599 : Minimum Index Sum of Two Lists.
   * @Difficulty: Easy
   * @OptimalComplexity: O(m+n) & O(m+n)
   * @param list1 string list
   * @param list2 string list
   * @return string list
   */
  public String[] findRestaurant(String[] list1, String[] list2) {
    List<String> list = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();
    Map<String, Integer> map2 = new HashMap<>();
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < list1.length; i++) {
      map.put(list1[i], i);
    }
    for (int i = 0; i < list2.length; i++) {
      if (map.containsKey(list2[i])) {
        map2.put(list2[i], map.get(list2[i]) + i);
      }
    }
    for (String str : map2.keySet()) {
      min = Math.min(map2.get(str), min);
    }
    for (String str : map2.keySet()) {
      if (map2.get(str) == min) {
        list.add(str);
      }
    }
    String[] res = new String[list.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = list.get(i);
    }
    return res;
  }

  /**
   * Leetcode 598 : Range Addition..
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param m int
   * @param n int
   * @param ops int[][]
   * @return int
   */
  public int maxCount(int m, int n, int[][] ops) {
    if (ops.length == 0) {
      return m * n;
    }
    int minR = Integer.MAX_VALUE;
    int minC = Integer.MAX_VALUE;
    for(int i = 0; i < ops.length; i++) {
      minR = Math.min(minR, ops[i][0]);
      minC = Math.min(minC, ops[i][1]);
    }
    return minR * minC;
  }
}
