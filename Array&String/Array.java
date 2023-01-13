import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;
import java.util.TreeMap;

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
}
