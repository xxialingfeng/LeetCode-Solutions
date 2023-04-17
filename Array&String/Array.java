import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

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
    for (int[] op : ops) {
      minR = Math.min(minR, op[0]);
      minC = Math.min(minC, op[1]);
    }
    return minR * minC;
  }

  /**
   * Leetcode 605 : Can Place Flowers.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param flowerbed int[]
   * @param n int
   * @return boolean
   */
  public boolean canPlaceFlowers(int[] flowerbed, int n) {
    if (n == 0) {
      return true;
    }
    if (flowerbed.length == 1) {
      return flowerbed[0] == 0 && n <= 1;
    }
    if (flowerbed.length == 2) {
      return flowerbed[0] == 0 && flowerbed[1] == 0 && n <= 1;
    }
    int cnt = 0;
    for (int i = 0; i < flowerbed.length; i++) {
      if (flowerbed[i] == 1) {
        continue;
      }
      if (i == 0 && flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
        cnt++;
        flowerbed[i] = 1;
      } else if (i == flowerbed.length-1 && flowerbed[i] == 0 && flowerbed[i - 1] == 0) {
        cnt++;
        flowerbed[i] = 1;
      } else if (i >= 1 && i <= flowerbed.length-1 && flowerbed[i-1] == 0 && flowerbed[i+1] == 0) {
        cnt++;
        flowerbed[i] = 1;
      }
    }
    return cnt >= n;
  }

  /**
   * Leetcode 628 : Maximum Product of Three Numbers.
   * @Difficulty: Easy
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param nums int[]
   * @return int
   */
  public int maximumProduct(int[] nums) {
    int n = nums.length - 1;
    if (nums.length == 3) {
      return nums[0] * nums[1] * nums[2];
    }
    Arrays.sort(nums);
    if (nums[1] < 0) {
      return Math.max(nums[n] * nums[n - 1] * nums[n - 2], nums[0] * nums[1] * nums[n]);
    }
    return nums[n] * nums[n - 1] * nums[n - 2];
  }

  /**
   * Leetcode 639 :Decode Ways II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param s String
   * @return int
   */
  public int numDecodings(String s) {
    if (s.length() == 0) {
      return 0;
    }
    if (s.length() == 1) {
      return s.charAt(0) == '0' ? 0 : s.charAt(0) == '*' ? 9 : 1;
    }
    if (s.charAt(0) == '0') {
      return 0;
    }
    long[] dp = new long[s.length() + 1];
    char[] chr = s.toCharArray();
    dp[0] = 1;
    dp[1] = chr[0] == '*' ? 9 : 1;
    for (int i = 2; i <= chr.length; i++) {
      char charOne = chr[i - 1];
      char charTwo = chr[i - 2];
      //dp[i-1]
      if (charOne == '*') {
        dp[i] += dp[i - 1] * 9;
      } else if (charOne > '0') {
        dp[i] += dp[i - 1];
      }

      //dp[i-2]
      if (charTwo == '*') {
        if (charOne == '*') {
          dp[i] += dp[i - 2] * 15;
        } else if (charOne <= '6') {
          dp[i] += dp[i - 2] * 2;
        } else {
          dp[i] += dp[i - 2];
        }
      } else if (charTwo == '1' || charTwo == '2') {
        if (charOne == '*') {
          dp[i] += charTwo == '1' ? 9 * dp[i - 2] : 6 * dp[i - 2];
        } else if ((charTwo - '0') * 10 + charOne - '0' <= 26) {
          dp[i] += dp[i - 2];
        }
      }
      dp[i] %= 1000000007;
    }
    return (int)dp[s.length()];
  }

  /**
   * Leetcode 645 : Set Mismatch.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(2)
   * @param nums int[]
   * @return int[]
   */
  public int[] findErrorNums(int[] nums) {
    Arrays.sort(nums);
    int sum = (1 + nums.length) * nums.length / 2;
    int count = 0;
    int mul = 0;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      count += num;
      if (set.contains(num)) {
        mul = num;
      }
      set.add(num);
    }
    return new int[]{mul, mul + sum - count};
  }

  /**
   * Leetcode 665 : Non-decreasing Array.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return boolean
   */
  public boolean checkPossibility(int[] nums) {
    if (nums == null || nums.length <= 1) {
      return true;
    }
    int cnt = 0;
    for (int i = 1; i < nums.length && cnt < 2; i++) {
      if (nums[i - 1] <= nums[i]) {
        continue;
      }
      cnt++;
      if (i - 2 >= 0 && nums[i - 2] > nums[i]) {
        nums[i] = nums[i - 1];
      } else {
        nums[i - 1] = nums[i];
      }
    }
    return cnt <= 1;
  }

  /**
   * Leetcode 667 : Beautiful Arrangement II.
   * @param n int
   * @param k int
   * @return int[]
   */
  public int[] constructArray(int n, int k) {
    int[] ans = new int[n];
    int a = 1;
    for (int i = 0; i <= k; i += 2) {
      ans[i] = a;
      a++;
    }
    int b = k + 1;
    for (int i = 1; i <= k; i = i + 2) {
      ans[i] = b;
      b--;
    }
    for (int i = k + 1; i < n; i++) {
      ans[i] = i + 1;
    }
    return ans;
  }

  /**
   * Leetcode 678 : Valid Parenthesis String.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param s String
   * @return boolean
   */
  public boolean checkValidString(String s) {
    int min = 0;
    int max = 0;
    for (int i = 0; i < s.length(); i++) {
      char crr = s.charAt(i);
      if (crr == '(') {
        min++;
        max++;
      } else if (crr == ')') {
        min = Math.max(0, min - 1);
        max--;
        if (max < 0 ) {
          return false;
        }
      } else {
        min = Math.max(0, min - 1);
        max += 1;
      }
    }
    return min == 0;
  }

  /**
   * Leetcode 696 : Count Binary Substrings.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param s String
   * @return int
   */
  public int countBinarySubstrings(String s) {
    int ans = 0;
    int last = 0;
    int curr = 1;
    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i) == s.charAt(i - 1)) {
        curr++;
      } else {
        last = curr;
        curr = 1;
      }
      if (last >= curr) {
        ans++;
      }
    }
    return ans;
  }

  /**
   * Leetcode 697 : Degree of an Array.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int findShortestSubArray(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }
    int max = 0;
    for (int key : map.keySet()) {
      if (map.get(key) > max) {
        max = map.get(key);
      }
    }
    List<Integer> list = new ArrayList<>();
    for (int key : map.keySet()) {
      if (map.get(key) == max) {
        list.add(key);
      }
    }
    int res = Integer.MAX_VALUE;;
    for (int i : list) {
      int left = 0;
      int right = nums.length - 1;
      while (nums[left] != i) {
        left++;
      }
      while (nums[right] != i) {
        right--;
      }
      res = Math.min(res, right - left + 1);
    }
    return res;
  }

  /**
   * Leetcode 699 : Falling Squares.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n)
   * @param positions int[][]
   * @return list of integer.
   */
  public List<Integer> fallingSquares(int[][] positions) {
    List<Integer> ans = new ArrayList<>();
    int height = 0;
    int max = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < positions.length; i++) {
      height = 0;
      int left = positions[i][0];
      int right = positions[i][1]+left;
      for (int j = 0; j < i; j++) {
        if (right <= positions[j][0] || left >= positions[j][1] + positions[j][0]) {
          continue;
        }
        height = Math.max(map.get(j), height);
      }
      height += positions[i][1];
      max = Math.max(max, height);
      ans.add(max);
      map.put(i, height);
    }
    return ans;
  }

  /**
   * Leetcode 717 : 1-bit and 2-bit Characters.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param bits int[]
   * @return boolean
   */
  public boolean isOneBitCharacter(int[] bits) {
    int left = 0;
    while (left < bits.length) {
      if (left == bits.length - 1) {
        return true;
      }
      if (bits[left] == 1) {
        left = left + 2;
      } else if (bits[left] == 0) {
        left = left + 1;
      }
    }
    return false;
  }

  /**
   * Leetcode 732 : My Calendar III.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   */
  static class MyCalendarThree {

    private final TreeMap<Integer, Integer> cnt;

    public MyCalendarThree() {
      cnt = new TreeMap<Integer, Integer>();
    }

    public int book(int start, int end) {
      int ans = 0;
      int gap = 0;
      cnt.put(start, cnt.getOrDefault(start, 0) + 1);
      cnt.put(end, cnt.getOrDefault(end, 0) - 1);

      for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
        int count = entry.getValue();
        gap += count;
        ans = Math.max(gap, ans);
      }
      return ans;
    }
  }

  /**
   * Leetcode 731 : My Calendar II.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   */
  static class MyCalendarTwo {
    TreeMap<Integer, Integer> map;

    /**
     * constructor.
     */
    public MyCalendarTwo() {
      map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
      int ans = 0;
      map.put(start, map.getOrDefault(start, 0) + 1);
      map.put(end, map.getOrDefault(end, 0) - 1);
      for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int cnt = entry.getValue();
        ans += cnt;
        if (ans > 2) {
          map.put(start, map.getOrDefault(start, 0) - 1);
          map.put(end, map.getOrDefault(end, 0) + 1);
          return false;
        }
      }
      return true;
    }
  }

  /**
   * Leetcode 738 : Monotone Increasing Digits.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param n int
   * @return int
   */
  public int monotoneIncreasingDigits(int n) {
    if (isIncreasing(n)) {
      return n;
    }
    if (n <= 10) {
      return n == 10 ? 9 : n - 1;
    }
    char[] s = String.valueOf(n).toCharArray();
    int flag = s.length;
    for (int i = s.length - 1; i >= 1; i--) {
      if (s[i] < s[i - 1]) {
        flag = i;
        s[i - 1]--;;
      }
    }
    for (int i = flag; i < s.length; i++) {
      s[i] = '9';
    }
    return Integer.parseInt(new String(s));
  }

  /**
   * if the number is nonotone increasing digits.
   * @param n int
   * @return boolean
   */
  public boolean isIncreasing(int n) {
    String num = String.valueOf(n);
    for (int i = 1; i < num.length(); i++) {
      if (num.charAt(i) < num.charAt(i - 1)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 739 : Daily Temperatures.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param temperatures int[]
   * @return int[]
   */
  public int[] dailyTemperatures(int[] temperatures) {
    int[] ans = new int[temperatures.length];
    for (int i = temperatures.length - 1; i >= 0; i--) {
      int j = i + 1;
      while (j < temperatures.length) {
        if (temperatures[j] > temperatures[i]) {
          ans[i] = j - i;
          break;
        } else if (ans[j] == 0) {
          break;
        } else {
          j += ans[j];
        }
      }
    }
    return ans;
  }

  /**
   * Leetcode 747 : Largest Number At Least Twice of Others.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums
   * @return
   */
  public int dominantIndex(int[] nums) {
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int i = 0; i < nums.length; i++) {
      map.put(nums[i], i);
    }
    int max = map.lastKey();
    int idx = map.get(max);
    map.remove(max);
    int sec = map.lastKey();
    return max / 2 >= sec ? idx : -1;
  }

  /**
   * Leetcode 452 : Minimum Number of Arrows to Burst Balloons.
   * @param points int[][]
   * @return int
   */
  public int findMinArrowShots(int[][] points) {
    Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
    int count = 1;
    for (int i = 1; i < points.length; i++) {
      if (points[i][0] <= points[i - 1][1]) {
        points[i][1] = Math.min(points[i][1], points[i - 1][1]);
      } else {
        count++;
      }
    }
    return count;
  }

  /**
   * Leetcode 757 : Set Intersection Size At Least Two.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param intervals int[][]
   * @return int
   */
  public int intersectionSizeTwo(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));
    int n = intervals.length;
    int curr = intervals[n - 1][0];
    int next = intervals[n - 1][0] + 1;
    int ans = 2;
    for (int i = n - 2; i >= 0; i--) {
      int[] temp = intervals[i];
      if (temp[1] < curr) {
        curr = temp[0];
        next = temp[0] + 1;
        ans += 2;
      } else if (temp[1] >= next) {
        continue;
      } else {
        next = curr;
        curr = temp[0];
        ans++;
      }
    }
    return ans;
  }

  /**
   * leetcode 794 : Valid Tic-Tac-Toe State.
   * @Difficulty: Medium
   * @OptimalComplexity: O(9) & O(9)
   * @param board array of string
   * @return boolean
   */
  public boolean validTicTacToe(String[] board) {
    Map<String, Integer> map = new HashMap<>();
    map.put("X", 0);
    map.put("O", 0);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i].charAt(j) == 'X') {
          map.put("X", map.getOrDefault("X", 0) + 1);
        } else if (board[i].charAt(j) == 'O') {
          map.put("O", map.getOrDefault("O", 0) + 1);
        }
      }
    }
    if (map.get("X") < map.get("O") || map.get("X") > map.get("O") + 1) {
      return false;
    }
    if (win(board, 'X') && win(board, 'O')) {
      return false;
    }
    if (win(board, 'X')) {
      return map.get("X") == map.get("O") + 1;
    }
    if (win(board, 'O')) {
      return map.get("X") == map.get("O");
    }
    return true;
  }

  /**
   * Check if someone wins.
   * @param board array of string
   * @param p char
   * @return boolean
   */
  public boolean win(String[] board, char p) {
    for (int i = 0; i < 3; ++i) {
      if ((p == board[0].charAt(i) && p == board[1].charAt(i) && p == board[2].charAt(i)) ||
          (p == board[i].charAt(0) && p == board[i].charAt(1) && p == board[i].charAt(2))) {
        return true;
      }
    }
    return ((p == board[0].charAt(0) && p == board[1].charAt(1) && p == board[2].charAt(2)) ||
        (p == board[0].charAt(2) && p == board[1].charAt(1) && p == board[2].charAt(0)));
  }

  /**
   * Leetcode 795 : Number of Subarrays with Bounded Maximum.
   * @Difficulty: Medium
   * @OptimalComplexity: O(b) & O(1)
   * @param nums int[]
   * @param left int
   * @param right int
   * @return int
   */
  public int numSubarrayBoundedMax(int[] nums, int left, int right) {
    return numSub(nums, right) - numSub(nums, left - 1);
  }

  /**
   * Leetcode 795 : Number of Subarrays with Bounded Maximum.
   * @Difficulty: Medium
   * @OptimalComplexity: O(b) & O(1)
   * @param nums int[]
   * @param left int
   * @param right int
   * @return int
   */
  public int numSubarrayBoundedMax2(int[] nums, int left, int right) {
    int j = -1;
    int ans = 0;
    int temp = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > right) {
        j = i;
      }
      if (nums[i] >= left) {
        temp = i - j;
      }
      ans += temp;
    }
    return ans;
  }

  /**
   * number of subarrays.
   * @param arr int[]
   * @param max int
   * @return int
   */
  public int numSub(int[] arr, int max) {
    int res = 0;
    int numSubArray = 0;
    for (int num : arr) {
      if (num <= max) {
        numSubArray++;
        res += numSubArray;
      } else {
        numSubArray = 0;
      }
    }
    return res;
  }

  /**
   * Leetcode 806 : Number of Lines To Write String.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(m)
   * @param widths int[]
   * @param s String
   * @return int[]
   */
  public int[] numberOfLines(int[] widths, String s) {
    int[] ans = new int[2];
    int sum = 0;
    int cnt = 0;
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      int idx = s.charAt(i) - 'a';
      if (sum + widths[idx] > 100) {
        list.add(sum);
        sum = 0;
      }
      sum += widths[idx];
      if (i == s.length() - 1) {
        cnt = sum;
        list.add(sum);
      }
    }
    ans[0] = list.size();
    ans[1] = cnt;
    return ans;
  }

  /**
   * Leetcode 807 : Max Increase to Keep City Skyline.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int maxIncreaseKeepingSkyline(int[][] grid) {
    int[][] copy = new int[grid.length][grid.length];
    for (int i = 0; i < grid.length; i++) {
      System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
    }
    int sum = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        copy[i][j] = Math.min(rowMax(grid[i]), colMax(grid, j));
        sum += copy[i][j] - grid[i][j];
      }
    }
    return sum;
  }

  /**
   * max val of row.
   * @param arr int[]
   * @return int
   */
  public int rowMax(int[] arr) {
    int max = 0;
    for (int i : arr) {
      max = Math.max(max, i);
    }
    return max;
  }

  /**
   * max val of cal.
   * @param copy int[][]
   * @param idx int
   * @return int
   */
  public int colMax(int[][] copy, int idx) {
    List<Integer> list = new ArrayList<>();
    for (int[] ints : copy) {
      list.add(ints[idx]);
    }
    int max = 0;
    for (int i : list) {
      max = Math.max(i, max);
    }
    return max;
  }

  /**
   * Leetcode 822 : Card Flipping Game.
   * @Difficulty: Meidum
   * @OptimalComplexity: O(n) & O(n)
   * @param fronts int[]
   * @param backs int[]
   * @return int
   */
  public int flipgame(int[] fronts, int[] backs) {
    Set<Integer> set = new HashSet<>();
    Set<Integer> optout = new HashSet<>();
    for (int i = 0; i < backs.length; i++) {
      if (backs[i] == fronts[i]) {
        optout.add(backs[i]);
      }
    }
    for (int i = 0; i < backs.length; i++) {
      if (backs[i] != fronts[i] && !optout.contains(backs[i])) {
        set.add(backs[i]);
      } else {
        optout.add(backs[i]);
      }
    }
    for (int front : fronts) {
      if (!optout.contains(front)) {
        set.add(front);
      }
    }
    int min = 10000;
    for (int i : set) {
      min = Math.min(min, i);
    }
    return min == 10000 ? 0 : min;
  }

  /**
   * Leetcode 828 : Count Unique Characters of All Substrings of a Given String.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @return int
   */
  public int uniqueLetterString(String s) {
    Map<Character, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      if (!map.containsKey(s.charAt(i))) {
        map.put(s.charAt(i), new ArrayList<>());
        map.get(s.charAt(i)).add(-1);
      }
      map.get(s.charAt(i)).add(i);
    }
    int res = 0;
    for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
      List<Integer> list = entry.getValue();
      list.add(s.length());
      for (int i = 1; i < list.size() - 1; i++) {
        res += (list.get(i) - list.get(i - 1)) * (list.get(i + 1) - list.get(i));
      }
    }
    return res;
  }

  /**
   * Leetcode 907 : Sum of Subarray Minimums.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m)
   * @param arr int[]
   * @return int
   */
  public int sumSubarrayMins(int[] arr) {
    long res = 0;
    for (int i = 0; i < arr.length; i++) {
      int l = i - 1;
      while (l >= 0 && arr[i] < arr[l]) {
        l--;
      }
      int r = i + 1;
      while (r < arr.length && arr[i] <= arr[r]) {
        r++;
      }
      res += (long) arr[i] * (i - l) * (r - i);
      res %= 1000000007;
    }
    return (int)(res);
  }

  /**
   * Leetcode 832 : Flipping an Image.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n2) & O(1)
   * @param image int[][]
   * @return int[][]
   */
  public int[][] flipAndInvertImage(int[][] image) {
    int n = image.length;
    for (int i = 0; i < image.length; i++) {
      int[] curr = image[i];
      for (int j = 0; j < image[i].length / 2; j++) {
        if (curr[j] == curr[n - j - 1]) {
          if (curr[j] == 1) {
            curr[j] = 0;
          } else {
            curr[j] = 1;
          }
          if (curr[n - j - 1] == 1) {
            curr[n - j - 1] = 0;
          } else {
            curr[n - j - 1] = 1;
          }

        }
      }
      image[i] = curr;
    }
    if (n % 2 != 0) {
      for (int i = 0; i < image.length; i++) {
        if (image[i][n / 2] == 1 ) {
          image[i][n / 2] = 0;
        } else {
          image[i][n / 2] = 1;
        }
      }
    }
    return image;
  }

  /**
   * Leetcode 846 : Hand of Straights.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param hand int[]
   * @param groupSize int
   * @return boolean
   */
  public boolean isNStraightHand(int[] hand, int groupSize) {
    int n = hand.length;
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int i : hand) {
      map.put(i, map.getOrDefault(i, 0) + 1);
    }
    while (!map.isEmpty()) {
      int key = map.firstKey();
      map.put(key, map.get(key) - 1);
      if (map.get(key) <= 0) {
        map.remove(key);
      }
      int curr = key + 1;
      for (int i = 1; i < groupSize; i++) {
        if (!map.containsKey(curr)) {
          return false;
        }
        map.put(curr, map.get(curr) - 1);
        if (map.get(curr) <= 0) {
          map.remove(curr);
        }
        curr++;
      }
    }
    return true;
  }

  /**
   * Leetcode 850 : Rectangle Area II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * m log(n)) & O(m)
   * @param rectangles int[][]
   * @return int
   */
  public int rectangleArea(int[][] rectangles) {
    int n = rectangles.length;
    long ans = 0;
    Arrays.sort(rectangles, (a, b) -> a[1] - b[1]);
    TreeSet<Integer> set = new TreeSet<>();
    for (int[] rectangle : rectangles) {
      set.add(rectangle[0]);
      set.add(rectangle[2]);
    }
    List<Integer> list = new ArrayList<>(set);
    for (int i = 0, len = list.size(); i < len - 1; i++) {
      long left = list.get(i);
      long right = list.get(i + 1);
      int top = 0;
      for (int[] rectangle : rectangles) {
        if (rectangle[0] <= left && rectangle[2] >= right) {
          ans += (rectangle[3] - rectangle[1]) * (right - left);
          if (rectangle[1] <= top) {
            ans -= (Math.min(rectangle[3], top) - rectangle[1]) * (right - left);
            top = Math.max(top, rectangle[3]);
          } else {
            top = rectangle[3];
          }
        }
      }
    }
    return (int)(ans % 1000000007);
  }

  /**
   * Leetcode 853 : Car Fleet.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param target int
   * @param position int[]
   * @param speed int[]
   * @return int
   */
  public int carFleet(int target, int[] position, int[] speed) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < position.length; i++) {
      map.put(position[i], speed[i]);
    }
    Arrays.sort(position);
    for (int i = 0; i < speed.length; i++) {
      speed[i] = map.get(position[i]);
    }
    int ans = 1;
    int idx = position.length - 1;
    while (idx >= 1) {
      if (speed[idx - 1] == speed[idx]) {
        ans++;
        idx--;
        continue;
      }
      double t = (double)( position[idx] - position[idx - 1]) / (speed[idx - 1] - speed[idx]);
      double dis = position[idx] + speed[idx] * t;
      if (t > 0 && dis <= target) {
        position[idx - 1] = position[idx];
        speed[idx - 1] = speed[idx];
        idx--;
      } else {
        ans++;
        idx--;
      }
    }
    return ans;
  }

  /**
   * Leetcode 861 : Score After Flipping Matrix.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m * n) & O(1)
   * @param grid int[][]
   * @return int
   */
  public int matrixScore(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    for (int i = 0; i < grid.length; i++) {
      if (grid[i][0] == 0) {
        for (int j = 0; j < m; j++) {
          grid[i][j] = grid[i][j] ^ 1;
        }
      }
    }
    for (int i = 0; i < m; i++) {
      int temp = 0;
      for (int[] ints : grid) {
        temp += ints[i];
      }
      if (temp <= n / 2) {
        for (int k = 0; k < n; k++) {
          grid[k][i] = grid[k][i] ^ 1;
        }
      }
    }
    int ans = 0;
    for (int[] ints : grid) {
      for (int j = 0; j < m; j++) {
        ans += (ints[j] * (1 << m - j - 1));
      }
    }
    return ans;
  }

  /**
   * Leetcode 867 : Transpose Matrix.
   * @Difficulty: Easy
   * @OptimalComplexity: O(m * n) & O(m * n)
   * @param matrix int[][]
   * @return int[][]
   */
  public int[][] transpose(int[][] matrix) {
    int[][] ans = new int[matrix[0].length][matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        ans[j][i] = matrix[i][j];
      }
    }
    return ans;
  }

  /**
   * Leetcode 870 : Advantage Shuffle.
   * @Difficulty: O(nlogn) & O(n)
   * @param nums1 int[]
   * @param nums2 int[]
   * @return int[]
   */
  public int[] advantageCount(int[] nums1, int[] nums2) {
    int[] ans = new int[nums1.length];
    Arrays.sort(nums1);
    Integer[] idx = new Integer[nums2.length];
    for (int i = 0; i < nums2.length; i++) {
      idx[i] = i;
    }
    Arrays.sort(idx, (i, j) -> nums2[i] - nums2[j]);
    int l = 0;
    int r = nums1.length - 1;
    for (int i = 0; i < idx.length; i++) {
      if (nums1[i] > nums2[idx[l]]) {
        nums2[idx[l++]] = nums1[i];
      } else {
        nums2[idx[r--]] = nums1[i];
      }
    }
    return nums2;
  }

  /**
   * Leetcode 874 : Walking Robot Simulation.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) * O(n)
   * @param commands int[]
   * @param obstacles int[][]
   * @return int
   */
  public int robotSim(int[] commands, int[][] obstacles) {
    Set<String> set = new HashSet<>();
    for (int[] obstacle : obstacles) {
      set.add(obstacle[0] + "," + obstacle[1]);
    }
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    int count = 0;
    int x = 0;
    int y = 0;
    int ans = 0;
    int tx;
    int ty;
    for (int command : commands) {
      if (command >= 0) {
        for (int i = 0; i < command; i++) {
          tx = x + dx[count];
          ty = y + dy[count];
          if (set.contains(tx + "," + ty)) {
            break;
          }
          x = tx;
          y = ty;
          ans = Math.max(ans, x * x + y * y);
        }
      } else {
        count = command == -1 ? (count + 1) % 4 : (count + 3) % 4;
      }
    }
    return ans;
  }

  /**
   * Leetcode 875 : Koko Eating Bananas.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param piles int[]
   * @param h int
   * @return int
   */
  public int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = Integer.MAX_VALUE;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (possible(piles, h, mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  /**
   * if possible to aet all the bananas in given time.
   * @param piles int[]
   * @param h int
   * @param mid int
   * @return boolean
   */
  public boolean possible(int[] piles, int h, int mid) {
    int sum = 0;
    for (int pile : piles) {
      sum += ((pile - 1)/ mid) + 1;
    }
    return sum <= h;
  }

  /**
   * Keetcode 881 : Boats to Save People.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param people int[]
   * @param limit int
   * @return int
   */
  public int numRescueBoats(int[] people, int limit) {
    Arrays.sort(people);
    int count = 0;
    int left = 0;
    int right = people.length - 1;
    while (left <= right) {
      if (left == right) {
        count++;
        break;
      }
      int curr = people[right];
      if (left <= right && curr + people[left] <= limit) {
        left++;
      }
      right--;
      count++;
    }
    return count;
  }

  /**
   * Leetcode 883 : Projection Area of 3D Shapes.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n2) & O(1)
   * @param grid int[][]
   * @return int
   */
  public int projectionArea(int[][] grid) {
    int xyarea = 0;
    int yzarea = 0;
    int zxarea = 0;

    for (int i = 0; i < grid.length; i++) {
      int maxyz = 0;
      int maxzx = 0;
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] != 0) {
          xyarea++;
        }
        maxyz = Math.max(maxyz, grid[i][j]);
        maxzx = Math.max(maxzx, grid[j][i]);
      }
      yzarea += maxyz;
      zxarea += maxzx;
    }

    return xyarea + yzarea + zxarea;
  }

  /**
   * Leetcode 884 : Uncommon Words from Two Sentences.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param s1 string
   * @param s2 string
   * @return list of string
   */
  public String[] uncommonFromSentences(String s1, String s2) {
    String[] split1 = s1.split(" ");
    String[] split2 = s2.split(" ");
    List<String> list = new ArrayList<>();
    Map<String, Integer> store1 = new HashMap<>();
    Map<String, Integer> store2 = new HashMap<>();
    for (String str : split1) {
      store1.put(str, store1.getOrDefault(str, 0) + 1);
    }
    for (String str : split2) {
      store2.put(str, store2.getOrDefault(str, 0) + 1);
    }
    for (String str : store1.keySet()) {
      if (store1.get(str) == 1 && !store2.containsKey(str)) {
        list.add(str);
      }
    }
    for (String str : store2.keySet()) {
      if (store2.get(str) == 1 && !store1.containsKey(str)) {
        list.add(str);
      }
    }
    String[] ans = new String[list.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = list.get(i);
    }
    return ans;
  }

  /**
   * Leetcode 885 : Spiral Matrix III.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param rows int
   * @param cols int
   * @param rStart int
   * @param cStart int
   * @return int[][]
   */
  public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
    int[][] ans = new int[rows * cols][2];
    int left = cStart;
    int right = cStart;
    int up = rStart;
    int down = rStart;
    int cnt = 0;
    while (cnt < rows * cols) {
      for (int i = left; i <= right; i++) {
        if (i >= 0 && i < cols && up >= 0 && up < rows) {
          ans[cnt] = new int[]{up, i};
          cnt++;
        }
      }
      right++;
      for (int i = up; i <= down; i++) {
        if (i >= 0 && i < rows && right >= 0 && right < cols) {
          ans[cnt] = new int[]{i, right};
          cnt++;
        }
      }
      down++;
      for (int i = right; i >= left; i--) {
        if (i >= 0 && i < cols && down >= 0 && down < rows) {
          ans[cnt] = new int[]{down, i};
          cnt++;
        }
      }
      left--;
      for (int i = down; i >= up; i--) {
        if (i >= 0 && i < rows && left >= 0 && left < cols) {
          ans[cnt] = new int[]{i, left};
          cnt++;
        }
      }
      up--;
    }
    return ans;
  }

  /**
   * Leetcode 892 : Surface Area of 3D Shapes.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n2) & O(1)
   * @param grid int[][]
   * @return int
   */
  public int surfaceArea(int[][] grid) {
    int ans = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] != 0) {
          ans += grid[i][j] * 4 + 2;
        }
        if (i > 0) {
          ans -= Math.min(grid[i - 1][j], grid[i][j]) * 2;
        }

        if (j > 0) {
          ans -= Math.min(grid[i][j - 1], grid[i][j]) * 2;
        }
      }
    }
    return ans;
  }

  /**
   * Leetcode 896 : Monotonic Array.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return boolean
   */
  public boolean isMonotonic(int[] nums) {
    boolean increase = false;
    boolean decrease = false;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > nums[i - 1]) {
        if (decrease) {
          return false;
        }
        increase = true;
      } else if (nums[i] < nums[i - 1]){
        if (increase) {
          return false;
        }
        decrease = true;
      } else {
        continue;
      }
    }
    return true;
  }

  /**
   * Leetcode 899 : Orderly Queue.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @param k k
   * @return string
   */
  public String orderlyQueue(String s, int k) {
    if (k == 1) {
      String toCompare = s;
      for (int i = 0; i < s.length() - 1; i++) {
        s = s.substring(1) + s.charAt(0);
        if (s.compareTo(toCompare) < 0) {
          toCompare = s;
        }
      }
      return toCompare;
    } else {
      char[] chs = s.toCharArray();
      Arrays.sort(chs);
      return new String(chs);
    }
  }

  /**
   * Leetcode 905 : Sort Array By Parity.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int[]
   */
  public int[] sortArrayByParity(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    while (left < right) {
      while (left < nums.length && nums[left] % 2 == 0) {
        left++;
      }
      while (right >= 0 && nums[right] % 2 != 0) {
        right--;
      }
      if (left < right && left < nums.length && right >= 0) {
        swap(nums, left, right);
        left++;
        right--;
      }
    }
    return nums;
  }

  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /**
   * Leetcode 908 : Smallest Range I.
   * @Difficulty: Easy
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int smallestRangeI(int[] nums, int k) {
    if (nums.length == 1) {
      return 0;
    }
    Arrays.sort(nums);
    if (nums[nums.length - 1] - nums[0] <= k * 2) {
      return 0;
    }
    nums[0] += k;
    nums[nums.length - 1] -= k;
    return nums[nums.length - 1] - nums[0];
  }

  /**
   * Leetcode 910 : Smallest Range II.
   * @param nums int[]
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param k int
   * @return int
   */
  public int smallestRangeII(int[] nums, int k) {
    if (nums.length == 1) {
      return 0;
    }
    Arrays.sort(nums);
    int max = nums[nums.length - 1];
    int min = nums[0];
    int res = max - min;
    for (int i = 1; i < nums.length; i++) {
      min = Math.min(nums[0] + k, nums[i] - k);
      max = Math.max(nums[nums.length - 1] - k, nums[i - 1] + k);
      res = Math.min(max - min, res);
    }
    return res;
  }

  /**
   * Leetcode 915 : Partition Array into Disjoint Intervals。
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int partitionDisjoint(int[] nums) {
    int[] max = new int[nums.length];
    int[] min = new int[nums.length];
    int ma = nums[0];
    int mi = nums[nums.length - 1];
    for (int i = 0; i < nums.length; i++) {
      ma = Math.max(ma, nums[i]);
      max[i] = ma;
    }
    for (int i = nums.length - 1; i >= 0; i--) {
      mi = Math.min(mi, nums[i]);
      min[i] = mi;
    }
    int ans = 0;
    while (ans <= nums.length - 2) {
      if (max[ans] <= min[ans + 1]) {
        return ans + 1;
      }
      ans++;
    }
    return 1;
  }

  /**
   * Leetcode 918 : Maximum Sum Circular Subarray.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int
   */
  public int maxSubarraySumCircular(int[] nums) {
    int[] dp = new int[nums.length + 1];
    dp[0] = nums[0];
    for (int i = 1; i < nums.length; i++) {
      dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
    }
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < dp.length - 1; i++) {
      max = Math.max(max, dp[i]);
    }
    if (nums.length > 2) {
      int sum = nums[0] + nums[nums.length - 1];
      int min = nums[1];
      int curr = 0;
      for (int i = 1; i < nums.length - 1; i++) {
        sum += nums[i];
        curr = Math.min(curr, 0) + nums[i];
        min = Math.min(min, curr);
      }
      return Math.max(sum - min, max);
    } else {
      return max;
    }
  }

  /**
   * Leetcode 922 : Sort Array By Parity II.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int[]
   */
  public int[] sortArrayByParityII(int[] nums) {
    int l = 0;
    int r = nums.length - 1;
    while (l < nums.length && r >= 0) {
      while (l < nums.length &&  nums[l] % 2 == 0) {
        l += 2;
      }
      while (r >= 0 && nums[r] % 2 == 1) {
        r -= 2;
      }
      if (l < nums.length && r >= 0) {
        swap(nums, l, r);
        l += 2;
        r -= 2;
      }
    }
    return nums;
  }

  /**
   * Leetcode 927 : Three Equal Parts.
   * @Difficulty: O(n) & O(n)
   * @param arr int[]
   * @return int[]
   */
  public int[] threeEqualParts(int[] arr) {
    int n = arr.length, cnt = 0, tailZeros = 0;
    int[] ones = new int[n];
    for (int i = 0; i < n; ++i) {
      if (arr[i] == 1) ones[cnt++] = i;
    }
    int[] def = {-1, -1};
    //特判一下
    if (cnt == 0) return new int[]{0, n - 1};
    if (cnt % 3 != 0) return def;

    cnt /= 3;
    //最后一个数的末尾 0 是固定的，统计一下末尾 0
    for (int i = n - 1; arr[i] != 1; i--) tailZeros++;

    //根据统计的 1，找到前两个数的末尾 1 根据末尾 0 数去找真正的末尾
    int tail1 = ones[cnt - 1];
    int tail2 = ones[cnt * 2 - 1];
    for (int i = 0; i < tailZeros; ++i) {
      tail1++;
      tail2++;
      if (arr[tail1] != 0 || arr[tail2] != 0) return def;
    }

    //比较三个数是否相等
    for (int i = tail1, j = tail2, k = n - 1; i >= 0 && j > tail1 && k > tail2; i--, j--, k--) {
      if (arr[i] != arr[j] || arr[i] != arr[k]) return def;
    }

    return new int[]{tail1, tail2 + 1};
  }

  /**
   * Leetcode 939 ： Minimum Area Rectangle.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m2 * p * q) & O(m)
   * @param points int[][]
   * @return int
   */
  public int minAreaRect(int[][] points) {

    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int[] temp : points) {
      if (!map.containsKey(temp[0])) {
        map.put(temp[0], new ArrayList<>());
      }
      map.get(temp[0]).add(temp[1]);
    }
    int min = Integer.MAX_VALUE;
    for (int key : map.keySet()) {
      if (map.get(key).size() <= 1) {
        continue;
      }
      List<Integer> list = map.get(key);
      int y1 = -1;
      int y2 = -1;
      for (int i = 0; i < list.size(); i++) {
        for (int j = i + 1; j < list.size(); j++) {
          y1 = list.get(i);
          y2 = list.get(j);
          for (int k : map.keySet()) {
            if (map.get(k).size() > 1 && k != key && map.get(k).contains(y1) && map.get(k).contains(y2)) {
              min = Math.min(min, Math.abs(key - k) * Math.abs(y1 - y2));
            }
          }
        }
      }

    }
    return min == Integer.MAX_VALUE ? 0 : min;
  }

  /**
   * letcode 941 : Valid Mountain Array.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param arr int[]
   * @return boolean
   */
  public boolean validMountainArray(int[] arr) {
    int l = 0;
    int r = arr.length - 1;
    while (l < r) {
      while (l < r && arr[l] < arr[l + 1]) {
        l++;
      }
      while (l < r && arr[r] < arr[r - 1]) {
        r--;
      }
      if (l != r) {
        return false;
      }
    }
    if (l == arr.length - 1 || r == 0) {
      return false;
    }
    return true;
  }

  /**
   * Leetcode 942 : DI String Match.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @return int[]
   */
  public int[] diStringMatch(String s) {
    int[] ans = new int[s.length() + 1];
    int maxN = s.length();
    int minN = 0;
    int cnt = 0;
    while (cnt < s.length()) {
      if (s.charAt(cnt) == 'I') {
        ans[cnt] = minN;
        minN++;
        cnt++;
      } else {
        ans[cnt] = maxN;
        maxN--;
        cnt++;
      }
    }
    ans[ans.length - 1] = minN;
    return ans;
  }

  /**
   * Leetcode 945 : Minimum Increment to Make Array Unique.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(logn)
   * @param nums int[]
   * @return int
   */
  public int minIncrementForUnique(int[] nums) {
    Arrays.sort(nums);
    int ans = 0;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      set.add(num);
    }
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] == nums[i - 1]) {
        queue.offer(nums[i]);
      }
    }
    for (int i = 1; i <= 1000010; i++) {
      if (queue.isEmpty()) {
        break;
      }
      int n = queue.peek();
      if (!set.contains(i) && i > n) {
        ans += i - n;
        queue.poll();
      }
    }
    return ans;
  }

  /**
   * Leetcode 953 : Verifying an Alien Dictionary.
   * @Difficulty: Easy
   * @OptimalComplexity: O(26) & O(m * n)
   * @param words list of strings
   * @param order string
   * @return boolean
   */
  public boolean isAlienSorted(String[] words, String order) {
    Map<Character, Integer> map = new HashMap<>();
    int id = 0;
    for (char c : order.toCharArray()) {
      map.put(c, ++id);
    }
    for (int i = 1; i < words.length; i++) {
      String curr = words[i];
      String prev = words[i - 1];
      if (prev.startsWith(curr) && !prev.equals(curr)) {
        return false;
      }
      for (int j = 0; j < Math.min(curr.length(), prev.length()); j++) {
        if (map.get(curr.charAt(j)) == map.get(prev.charAt(j))) {
          continue;
        } else if (map.get(curr.charAt(j)) > map.get(prev.charAt(j))) {
          break;
        } else {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Leetcode 957 : Prison Cells After N Days.
   * @Difficulty: Medium
   * @OptimalComplexity: O(2^N) & (2^N * N)
   * @param cells int[]
   * @param n int
   * @return int
   */
  public int[] prisonAfterNDays(int[] cells, int n) {
    Map<Integer, int[]> map = new HashMap<>();
    Set<Integer> set = new HashSet<>();
    for (int i = 1; i <= 2 << 6; i++) {
      List<Integer> index = new ArrayList<>();
      for (int j = 1; j < cells.length - 1; j++) {
        if ((cells[j - 1] == 0 && cells[j + 1] == 0) || (cells[j - 1] == 1 && cells[j + 1] == 1)) {
          index.add(j);
        }
      }
      int[] toAdd = new int[8];
      for (Integer integer : index) {
        toAdd[integer] = 1;
      }
      map.put(i, toAdd);
      cells = toAdd;
      int temp = 1 << 9;
      for (int j = 0; j <= 7; j++) {
        if (toAdd[7 - j] == 1) {
          temp = temp | (1 << j);
        }
      }
      if (set.contains(temp)) {
        break;
      }
      set.add(temp);
    }

    int size = set.size();
    int day = n % size;
    return map.get(day == 0 ? size : day);
  }

  /**
   * Leetcode 962 : Maximum Width Ramp.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(N)
   * @param nums int[]
   * @return int
   */
  public int maxWidthRamp(int[] nums) {
    Integer[] temp = new Integer[nums.length];
    for (int i = 0; i < nums.length; i++) {
      temp[i] = i;
    }
    Arrays.sort(temp, (i, j) -> ((Integer) nums[i]).compareTo(nums[j]));
    int ans = 0;
    int m = nums.length;
    for (int i : temp) {
      ans = Math.max(ans, i - m);
      m = Math.min(m, i);
    }
    return ans;
  }

  /**
   * Leetcode 963 : Minimum Area Rectangle II.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n4) & O(1)
   * @param points int[][]
   * @return double
   */
  public double minAreaFreeRect(int[][] points) {
    if (points.length < 4) {
      return 0;
    }
    double ans = Double.MAX_VALUE / 2;
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          int[] point1 = points[i];
          int[] point2 = points[j];
          int[] point3 = points[k];
          for (int l = k + 1; l < points.length; l++) {
            int[] point4 = points[l];
            if (isRec(point1, point2, point3, point4)) {
              ans = Math.min(ans, area(point1[0], point1[1], point2[0], point2[1], point3[0], point3[1], point4[0], point4[1]));
            }
          }
        }
      }
    }
    return ans == Double.MAX_VALUE / 2 ? 0 : ans;
  }

  public boolean isRec(int[] point1, int[] point2, int[] point3, int[] point4) {
    double x1 = point1[0];
    double y1 = point1[1];
    double x2 = point2[0];
    double y2 = point2[1];
    double x3 = point3[0];
    double y3 = point3[1];
    double x4 = point4[0];
    double y4 = point4[1];
    double midx = (x1 + x2 + x3 + x4) / 4;
    double midy = (y1 + y2 + y3 + y4) / 4;
    return dis(midx, midy, x1, y1) == dis(midx, midy, x2, y2) && dis(midx, midy, x1, y1) == dis(midx, midy, x3, y3) && dis(midx, midy, x1, y1) == dis(midx, midy, x4, y4);
  }

  public double dis(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  public double disDouble(double x1, double y1, double x2, double y2) {
    return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
  }

  public double area(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
    double dis12 = disDouble(x1, y1, x2, y2);
    double dis13 = disDouble(x1, y1, x3, y3);
    double dis14 = disDouble(x1, y1, x4, y4);
    double dis23 = disDouble(x2, y2, x3, y3);
    double dis24 = disDouble(x2, y2, x4, y4);
    double dis34 = disDouble(x3, y3, x4, y4);
    if ((dis12 + dis13) == dis23) {
      return dis(x1, y1, x2, y2) * dis(x1, y1, x3, y3);
    }
    if ((dis12 + dis14) == dis24) {
      return dis(x1, y1, x2, y2) * dis(x1, y1, x4, y4);
    }
    if ((dis13 + dis14) == dis34) {
      return dis(x1, y1, x3, y3) * dis(x1, y1, x4, y4);
    }
    return 1;
  }

  /**
   * Leetcode 969 : Pancake Sorting.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n)
   * @param arr int[]
   * @return list of integer
   */
  public List<Integer> pancakeSort(int[] arr) {
    List<Integer> ans = new ArrayList<>();
    int lastIdx = arr.length - 1;
    Set<Integer> set = new HashSet<>();
    while (lastIdx != 0) {
      if (sorted(arr)) {
        return ans;
      }
      Map<Integer, Integer> map = new HashMap<>();
      for (int i = 0; i < arr.length; i++) {
        map.put(arr[i], i);
      }
      int max = 0;
      for (int i = 0; i < arr.length; i++) {
        if (!set.contains(arr[i])) {
          max = Math.max(max, arr[i]);
        }
      }
      set.add(max);
      int maxIdx = map.get(max);
      if (maxIdx == lastIdx) {
        lastIdx--;
      } else {
        int temp;
        for (int start = 0, end = maxIdx; start < end; start++, end--) {
          temp = arr[start];
          arr[start] = arr[end];
          arr[end] = temp;
        }
        for (int start = 0, end = lastIdx; start < end; start++, end--) {
          temp = arr[start];
          arr[start] = arr[end];
          arr[end] = temp;
        }
        ans.add(maxIdx + 1);
        ans.add(lastIdx + 1);
        lastIdx--;
      }
    }
    return ans;

  }

  public boolean sorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] < arr[i - 1]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 976 : Largest Perimeter Triangle.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return int
   */
  public int largestPerimeter(int[] nums) {
    Arrays.sort(nums);
    for (int i = nums.length - 1; i >= 2; i--) {
      int a = nums[i];
      int b = nums[i - 1];
      int c = nums[i - 2];
      if (a < b + c) {
        return a + b + c;
      }
    }
    return 0;
  }

  /**
   * Leetcode 977 ：
   * @Difficulty: Easy
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param nums int[]
   * @return int[]
   */
  public int[] sortedSquares(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      nums[i] = nums[i] * nums[i];
    }
    Arrays.sort(nums);
    return nums;
  }

  /**
   * Leetcode 985 : Sum of Even Numbers After Queries.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param queries int[][]
   * @return int[]
   */
  public int[] sumEvenAfterQueries(int[] nums, int[][] queries) {
    int initsum = 0;
    for (int num : nums) {
      if (num % 2 == 0) {
        initsum += num;
      }
    }
    int[] ans = new int[nums.length];
    for (int i = 0; i < queries.length; i++) {
      int[] query = queries[i];
      int val = query[0];
      int idx = query[1];
      if ((nums[idx] > 0 ? nums[idx] : -1 * nums[idx]) % 2 == 1) {
        if (Math.abs(nums[idx] + val) % 2 == 0) {
          nums[idx] += val;
          initsum += (nums[idx]);
          ans[i] = initsum;
        } else {
          nums[idx] += val;
          ans[i] = initsum;
        }
      } else {
        if ((nums[idx] + val) % 2 == 0) {
          nums[idx] += val;
          initsum += val;
          ans[i] = initsum;
        } else {
          initsum -= nums[idx];
          nums[idx] += val;
          ans[i] = initsum;
        }

      }
    }
    return ans;
  }

  /**
   * Leetcode 986 ： Interval List Intersections.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param firstList int[][]
   * @param secondList int[][]
   * @return int[][]
   */
  public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    int idxf = 0;
    int idxs = 0;
    List<int[]> list = new ArrayList<>();
    while (idxf < firstList.length && idxs < secondList.length) {
      if (firstList[idxf][1] < secondList[idxs][0]) {
        idxf++;
      } else if (firstList[idxf][0] > secondList[idxs][1]) {
        idxs++;
      } else if (firstList[idxf][1] <= secondList[idxs][1] && firstList[idxf][1] >= secondList[idxs][0]) {
        list.add(new int[]{Math.max(secondList[idxs][0], firstList[idxf][0]), firstList[idxf][1]});
        idxf++;
      } else if (firstList[idxf][1] >= secondList[idxs][1] && secondList[idxs][1] >= firstList[idxf][0]) {
        list.add(new int[]{Math.max(secondList[idxs][0], firstList[idxf][0]), secondList[idxs][1]});
        idxs++;
      }
    }
    int[][] ans = new int[list.size()][2];
    for (int i = 0; i < list.size(); i++) {
      ans[i] = list.get(i);
    }
    return ans;
  }

  /**
   * Leetcode 989 : Add to Array-Form of Integer.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param num int[]
   * @param k int
   * @return list of integer
   */
  public List<Integer> addToArrayForm(int[] num, int k) {
    List<Integer> ans = new ArrayList<>();
    boolean add = false;
    for (int i = num.length - 1; i >= 0; i--) {
      int toAdd = k % 10;
      num[i] += toAdd;
      if (num[i] >= 10) {
        num[i] -= 10;
        if (i >= 1) {
          num[i - 1] += 1;
        } else {
          add = true;
          k /= 10;
          break;
        }
      }
      k /= 10;
    }
    if (k == 0) {
      if (add) {
        ans.add(1);
      }
    }
    while (k > 0) {
      if (add) {
        int toAdd = k % 10;
        ans.add(0, toAdd + 1);
        add = false;
      } else {
        int toAdd = k % 10;
        ans.add(0, toAdd);
      }
      k /= 10;
    }
    for (int i : num) {
      ans.add(i);
    }
    List<Integer> res = new ArrayList<>();
    int[] curr = new int[ans.size()];
    for (int i = ans.size() - 1; i >= 0; i--) {
      curr[i] = ans.get(i);
    }
    for (int i = curr.length - 1; i >= 0; i--) {
      if (curr[i] >= 10) {
        curr[i] -= 10;
        if (i >= 1) {
          curr[i - 1] += 1;
        } else {
          res.add(1);
          break;
        }
      }
    }
    for (int i : curr) {
      res.add(i);
    }
    return res;
  }

  int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  /**
   * Leetcode 994 : Rotting Oranges.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int orangesRotting(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    boolean[][] visisted = new boolean[m][n];
    int ans = 0;
    int cnt = 0;
    for (int[] ints : grid) {
      for (int j = 0; j < n; j++) {
        if (ints[j] == 1) {
          cnt++;
        }
      }
    }
    while (cnt != 0) {
      visisted = new boolean[m][n];
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 2) {
            visisted[i][j] = true;
          }
        }
      }
      boolean flag = false;
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (visisted[i][j] && grid[i][j] == 2) {
            for (int[] dir : directions) {
              int x = i + dir[0];
              int y = j + dir[1];
              if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                flag = true;
                grid[x][y] = 2;
                cnt--;
              }
            }
          }
        }
      }
      ans++;
      if (!flag && !allRot(grid)) {
        return -1;
      }
    }
    return ans;
  }

  public boolean allRot(int[][] grid) {
    for (int[] ints : grid) {
      for (int j = 0; j < grid[0].length; j++) {
        if (ints[j] == 1) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Leetcode 992 : Subarrays with K Different Integers.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int subarraysWithKDistinct(int[] nums, int k) {
    return subarraysWithMostK(nums, k) - subarraysWithMostK(nums, k - 1);
  }

  public  int subarraysWithMostK(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    int count = 0;
    int left = 0;
    int right = 0;
    int res = 0;
    while (right < nums.length) {
      map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
      if (map.get(nums[right]) == 1) {
        count++;
      }
      right++;
      while (count > k) {
        map.put(nums[left], map.get(nums[left]) - 1);
        if (map.get(nums[left]) == 0) {
          count--;
        }
        left++;
      }
      res += right - left;
    }
    return res;
  }
}
