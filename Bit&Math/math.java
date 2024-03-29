import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**.
 * A collection of leetcode problems related to math
 */
public class
math {
  /**.
  * Leetcode 507: perfect number
  * @Difficulty: Easy
  * @OptimalComplexity: O(D)&O(1)
  */
  public boolean checkPerfectNumber(int num) {
    double mid = Math.sqrt(num);
    if (num < 4) {
      return false;
    }
    int sum = 1;
    for (int i = 2; i <= (int)mid; i++) {
      if (num % i != 0) {
        continue;
      }
      sum += i;
      sum += (num / i);
    }
    return sum == num;
  }

  /**
   * Leetcode 672 : Bulb Switcher II.
   * @param n int
   * @param presses int
   * @return int
   */
  public int flipLights(int n, int presses) {
    if (presses == 0) return 1;
    if (n == 1) return 2;
    if (n == 2) return (presses == 1) ? 3 : 4;
    return (presses > 2) ? 8 : (presses == 1) ? 4 : 7;
  }

  static final int TARGET = 24;
  static final double EPSILON = 1e-6;
  static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

  public boolean judgePoint24(int[] nums) {
    List<Double> list = new ArrayList<Double>();
    for (int num : nums) {
      list.add((double) num);
    }
    return solve(list);
  }

  /**
   * Leetcode 679 : 4 Game.
   * @Difficulty: Hard
   * @OptimalComplexity: O(1) & O(1)
   * @param list list of double
   * @return boolean
   */
  public boolean solve(List<Double> list) {
    if (list.size() == 0) {
      return false;
    }
    if (list.size() == 1) {
      return Math.abs(list.get(0) - TARGET) < EPSILON;
    }
    int size = list.size();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i != j) {
          List<Double> list2 = new ArrayList<Double>();
          for (int k = 0; k < size; k++) {
            if (k != i && k != j) {
              list2.add(list.get(k));
            }
          }
          for (int k = 0; k < 4; k++) {
            if (k < 2 && i > j) {
              continue;
            }
            if (k == ADD) {
              list2.add(list.get(i) + list.get(j));
            } else if (k == MULTIPLY) {
              list2.add(list.get(i) * list.get(j));
            } else if (k == SUBTRACT) {
              list2.add(list.get(i) - list.get(j));
            } else {
              if (Math.abs(list.get(j)) < EPSILON) {
                continue;
              } else {
                list2.add(list.get(i) / list.get(j));
              }
            }
            if (solve(list2)) {
              return true;
            }
            list2.remove(list2.size() - 1);
          }
        }
      }
    }
    return false;
  }

  List<Integer> list = new ArrayList<>();

  /**
   * Leetcode 728 : Self Dividing Numbers.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n * m) & O(n)
   * @param left int
   * @param right right
   * @return list of integer
   */
  public List<Integer> selfDividingNumbers(int left, int right) {
    for (int i = left; i <= right; i++) {
      if (isValid(i)) {
        list.add(i);
      }
    }
    return list;
  }

  public boolean isValid(int n) {
    int s = n;
    int least = n % 10;
    int last = n;
    while (n > 0) {
      if (least == 0  || (s % least) != 0) {
        return false;
      }
      n /= 10;
      least = n % 10;
    }
    return true;
  }

  /**
   * Leetcode 775 : Global and Local Inversions.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums
   * @return
   */
  public boolean isIdealPermutation(int[] nums) {
    int local = 0;
    for (int i = 0; i < nums.length; i ++ ) {
      if (Math.abs(nums[i] - i) > 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 780 : Reaching Points.
   * @Difficulty: Hard
   * @OptimalComplexity: O(logn) & O(1)
   * @param sx int
   * @param sy int
   * @param tx int
   * @param ty int
   * @return boolean
   */
  public boolean reachingPoints(int sx, int sy, int tx, int ty) {
    while (tx >= sx && ty >= sy) {
      if (tx == sx && ty == sy) {
        return true;
      }
      if (tx < ty) {
        ty -= Math.max((ty - sy) / tx, 1) * tx;
      } else {
        tx -= Math.max((tx - sx) / ty, 1) * ty;
      }
    }
    return false;
  }

  /**
   * Leetcode 781 : Rabbits in Forest.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param answers int[]
   * @return int
   */
  public int numRabbits(int[] answers) {
    Map<Integer,Integer> map = new HashMap<>();
    int num = 0;
    for (int answer : answers) {
      if (map.containsKey(answer) && map.get(answer) > 0) {
        map.put(answer,map.get(answer) - 1);
      } else {
        num += answer + 1;
        map.put(answer,answer);
      }
    }
    return num;
  }

  /**
   * Leetcode 788 : otated Digits.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param n int
   * @return int
   */
  public int rotatedDigits(int n) {
    int cnt = 0;
    for (int i = 1; i <= n; i++) {
      if (isGood(i)) {
        cnt++;
      }
    }
    return cnt;
  }

  /**
   * helper for leetcode 788.
   * @param n int
   * @return boolean
   */
  public boolean isGood(int n) {
    Set<Integer> set = new HashSet<>();
    while (n > 0) {
      int last = n % 10;
      set.add(last);
      n /= 10;
    }
    if (set.contains(3) || set.contains(4) || set.contains(7)) {
      return false;
    }
    return set.contains(2) || set.contains(5) || set.contains(6) || set.contains(9);
  }

  /**
   * Leetcode 789 : Escape The Ghosts.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param ghosts int[][]
   * @param target int[]
   * @return boolean
   */
  public boolean escapeGhosts(int[][] ghosts, int[] target) {
    int x = target[0];
    int y = target[1];
    int sum = Math.abs(x) + Math.abs(y);
    for (int[] ghost : ghosts) {
      int gx = ghost[0];
      int gy = ghost[1];
      int gsum = Math.abs(gx - x) + Math.abs(gy - y);
      if (gsum <= sum) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 793 : Preimage Size of Factorial Zeroes Function.
   * @Difficulty: Hard
   * @OptimalComplexity: O(log2n) & O(1)
   * @param k int
   * @return int
   */
  public int preimageSizeFZF(int k) {
    long left = 0L;
    long right = 5L * k;
    while (left <= right) {
      long mid = left + (right - left) / 2;
      if (countZero(mid) > k) {
        right = mid - 1;
      } else if (countZero(mid) < k) {
        left = mid + 1;
      } else {
        return 5;
      }
    }
    return 0;
  }

  /**
   * Leetcode 172 : Factorial Trailing Zeroes.
   * @Difficulty: Medium
   * @OptimalComplexity: O(logn) & O(1)
   * @param n long
   * @return int
   */
  public int countZero(long n) {
    int ans = 0;
    while (n >= 5) {
      ans += n / 5;
      n /= 5;
    }
    return ans;
  }

  /**
   * Leetcode 810 : Chalkboard XOR Game.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(1)
   * @param nums int[]
   * @return boolean
   */
  public boolean xorGame(int[] nums) {
    int flag = 0;
    int size = nums.length;
    if (size % 2== 0) {
      return true;
    }
    for (int num : nums) {
      flag ^= num;
    }
    return flag == 0;
  }

  /**
   * Leetcode 812 : Largest Triangle Area.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n3) & O(1)
   * @param points int[][]
   * @return double
   */
  public double largestTriangleArea(int[][] points) {
    double res = 0;
    for (int i = 0; i < points.length - 2; i++) {
      double x1 = points[i][0];
      double y1 = points[i][1];
      for (int j = i + 1; j < points.length - 1; j++) {
        double x2 = points[j][0];
        double y2 = points[j][1];
        for (int k = j + 1; k < points.length; k++) {
          double x3 = points[k][0];
          double y3 = points[k][1];
          res = Math.max(res, Math.abs((x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2) / 2));
        }
      }
    }
    return res;
  }

  /**
   * Leetcode 829 : Consecutive Numbers Sum.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(1)
   * @param n int
   * @return int
   */
  public int consecutiveNumbersSum(int n) {
    int res = 0;
    for (int i = 1; n > 0; n -= i++) {
      if (n % i == 0) {
        res++;
      }
    }
    return res;
  }

  /**
   * Leetcode 835 : Image Overlap.
   * @Difficulty: medium
   * @OptimalComplexity: O(n2) & O(n)
   * @param img1 int[][]
   * @param img2 int[][]
   * @return int
   */
  public int largestOverlap(int[][] img1, int[][] img2) {
    Set<Integer> set2 = new HashSet<>();
    Set<Integer> set1 = new HashSet<>();
    int n = img2.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (img2[i][j] == 1) {
          set2.add(i * 100 + j);
        }
        if (img1[i][j] == 1) {
          set1.add(i * 100 + j);
        }
      }
    }
    Map<Integer, Integer> map = new HashMap<>();
    for (int a : set1) {
      for (int b : set2) {
        map.put(a - b, map.getOrDefault(a - b, 0) + 1);
      }
    }
    int max = 0;
    for (int i : map.keySet()) {
      max = Math.max(max, map.get(i));
    }
    return max;

  }

  /**
   * Leetcode 836 : Rectangle Overlap.
   * @Difficulty: Easy
   * @OptimalComplexity: O(1) & O(1)
   * @param rec1 int[]
   * @param rec2 int[]
   * @return boolean
   */
  public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
    if (rec2[1] >= rec1[3] || rec1[1] >= rec2[3]) {
      return false;
    }
    if (rec1[0] >= rec2[2] || rec1[2] <= rec2[0]) {
      return false;
    }
    return true;
  }

  /**
   * Leetcode 840 : Magic Squares In Grid.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int numMagicSquaresInside(int[][] grid) {
    int count = 0;
    for (int i = 1; i < grid.length - 1; i++) {
      for (int j = 1; j < grid[i].length - 1; j++) {
        if (grid[i][j] == 5) {
          count += grid[i - 1][j - 1] * grid[i - 1][j] * grid[i - 1][j + 1] * grid[i][j - 1] * grid[i][j]
              * grid[i][j + 1] * grid[i + 1][j - 1] * grid[i + 1][j] * grid[i + 1][j + 1] == 362880
              && grid[i - 1][j - 1] + grid[i - 1][j] + grid[i - 1][j + 1] == 15
              && grid[i][j - 1] + grid[i][j] + grid[i][j + 1] == 15
              && grid[i + 1][j - 1] + grid[i + 1][j] + grid[i + 1][j + 1] == 15
              && grid[i - 1][j - 1] + grid[i][j - 1] + grid[i + 1][j - 1] == 15
              && grid[i - 1][j] + grid[i][j] + grid[i + 1][j] == 15
              && grid[i - 1][j + 1] + grid[i][j + 1] + grid[i + 1][j + 1] == 15
              && grid[i - 1][j - 1] + grid[i][j] + grid[i + 1][j + 1] == 15
              && grid[i - 1][j + 1] + grid[i][j] + grid[i + 1][j - 1] == 15 ? 1 : 0;
        }
      }
    }
    return count;
  }

  /**
   * Leetcode 858 : Mirror Reflection.
   * @Difficulty: Medium
   * @OptimalComplexity: O(logn) & O(1)
   * @param p int
   * @param q int
   * @return int
   */
  public int mirrorReflection(int p, int q) {
    int g = gcd(p, q);
    p /= g;
    p %= 2;
    q /= g;
    q %= 2;

    if (p == 1 && q == 1) return 1;
    return p == 1 ? 0 : 2;
  }

  public int gcd(int a, int b) {
    if (a == 0) return b;
    return gcd(b % a, a);
  }

  /**
   * Leetcode 866 : Prime Palindrome.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m) & O(1)
   * @param n int
   * @return int
   */
  public int primePalindrome(int n) {
    if (n == 1 || n == 2) {
      return 2;
    }
    for (int i = n; i < Integer.MAX_VALUE;) {
      if (isPrime(i) && isPalin(i)) {
        return i;
      }
      if (i > 11 && (i + "").length() % 2 == 0) {
        i = (int)(Math.pow(10, (i + "").length()) + 1);
      } else {
        i++;
      }
    }
    return 0;
  }

  /**
   * if a number is a prime
   * @param n int
   * @return boolean
   */
  public boolean isPrime(int n) {
    for (int i = 2; i <= Math.sqrt(n); i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * if the number is a palindrome.
   * @param n int
   * @return boolean
   */
  public boolean isPalin(int n) {
    String str = String.valueOf(n);
    int left = 0;
    int right = str.length() - 1;
    while (left < right) {
      if (str.charAt(left) != str.charAt(right)) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  /**
   * Leetcode 878 : Nth Magical Number.
   * @Difficulty: Hard
   * @OptimalComplexity: O(logn) & O(1)
   * @param n int
   * @param a int
   * @param b int
   * @return int
   */
  public int nthMagicalNumber(int n, int a, int b) {
    if (a % b == 0) {
      return nthMagicalNumber(n, b);
    }
    if (b % a == 0) {
      return nthMagicalNumber(n, a);
    }
    int p = a * b / gcd878(a, b);
    long l = 1;
    long r = (long) n * Math.min(a, b);
    while (l <= r) {
      long mid = l + (r - l) / 2;
      if (mid / a + mid / b - mid / p >= n) { //num1 + num2 - num(1 + 2)
        r = mid - 1;
      } else {
        l = mid + 1;
      }
    }
    return (int)(l % 1000000007);
  }

  public int nthMagicalNumber(int n, int a) {
    return (int)((long) n * a % 1000000007);
  }

  public int gcd878(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  /**
   * Leetcode 914 :  X of a Kind in a Deck of Cards.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param deck int[]
   * @return boolean
   */
  public boolean hasGroupsSizeX(int[] deck) {
    if (deck.length == 1) {
      return false;
    }
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int card : deck) {
      map.put(card, map.getOrDefault(card, 0) + 1);
    }
    if (map.size() == 1) {
      return true;
    }
    int t = 0;
    for (int a : map.values()) {
      t = gcd(t, a);
    }
    return t >= 2;

  }

  /**
   * Leetcode 923 : 3Sum With Multiplicity.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(
   * @param arr
   * @param target
   * @return
   */
  public int threeSumMulti(int[] arr, int target) {
    Arrays.sort(arr);
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : arr) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }
    long ans = 0;
    for (int i = 0; i < arr.length; i += map.get(arr[i]) ) {
      int n = map.get(arr[i]);
      if (n >= 3 && 3 * arr[i] == target) {
        ans = (ans + (long) (n - 2) * (n - 1) * n / 6) % 1000000007;
      }
      if (n >= 2 && map.containsKey(target - 2 * arr[i]) && target != 3 * arr[i]) {
        ans = (ans + (long) n * (n - 1) / 2 * map.get(target - 2 * arr[i])) % 1000000007;
      }
      for (int j = i + n; j < arr.length; j += map.get(arr[j])) {
        if (map.containsKey(target - arr[i] - arr[j]) && target - arr[i] - arr[j] > arr[j]) {
          ans = (ans + (long) n * map.get(arr[j]) * map.get(target - arr[i] - arr[j])) % 1000000007;
        }
      }
    }
    return (int) (ans % 1000000007);
  }

  /**
   * Leetcode 970 : Powerful Integers.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m * n) & O(n)
   * @param x int
   * @param y int
   * @param bound int
   * @return list of integer
   */
  public List<Integer> powerfulIntegers(int x, int y, int bound) {
    List<Integer> ans = new ArrayList<>();
    if (x == 1 && y == 1) {
      if (bound == 0 || bound == 1) {
        return new ArrayList<>();
      } else {
        ans.add(2);
        return ans;
      }
    }
    if (x == 1) {
      int count = 0;
      while (Math.pow(y, count) + 1 <= bound) {
        ans.add((int)Math.pow(y, count) + 1);
        count++;
      }
      return ans;
    }
    if (y == 1) {
      int count = 0;
      while (Math.pow(x, count) + 1 <= bound) {
        ans.add((int)Math.pow(x, count) + 1);
        count++;
      }
      return ans;
    }
    Set<Double> set = new HashSet<>();
    int count = 0;
    double sum = 0;
    boolean xbound = false;
    boolean ybound = true;
    while (sum <= bound) {
      sum = Math.pow(x, count);
      count++;
    }
    for (int i = 0; i < count; i++) {
      int cnty = 0;
      while (Math.pow(x, i) + Math.pow(y, cnty) <= bound) {
        set.add(Math.pow(x, i) + Math.pow(y, cnty));
        cnty++;
      }
    }
    for (double res : set) {
      ans.add((int)res);
    }
    return ans;
  }

  /**
   * Leetcode 972 : Equal Rational Numbers.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @param t string
   * @return boolean
   */
  public boolean isRationalEqual(String s, String t) {
    String[] sp = s.split("\\.");
    String[] tp = t.split("\\.");
    double sd = divide(sp);
    double td = divide(tp);
    return Math.abs(Integer.parseInt(sp[0]) - Integer.parseInt(tp[0]) + sd - td)<0.00000001;
  }

  public double divide(String[] n) {
    if (n.length == 1) {
      return 0;
    }
    double b = 0;
    double c = 0;
    for (int i = 0; i < n[1].length(); i++) {
      if (n[1].charAt(i) == '(') {
        if (i > 0) {
          b = (double)Integer.parseInt(n[1].substring(0,i)) / Math.pow(10,i);
        }
        String s = n[1].substring(i + 1, n[1].length() - 1);
        c = (double)Integer.parseInt(s) / (Math.pow(10,i) * (Math.pow(10,s.length()) - 1));
        break;
      }
      if (i == n[1].length() - 1) {
        return (double) Integer.parseInt(n[1]) / Math.pow(10, n[1].length());
      }
    }
    return b + c;
  }

  /**
   * Leetcode 974 : Subarray Sums Divisible by K.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int subarraysDivByK(int[] nums, int k) {
    int ans = 0;
    int sum = 0;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    for (int num : nums) {
      sum += num;
      int currm = (sum % k + k) % k;
      int currc = map.getOrDefault(currm, 0);
      ans += currc;
      map.put(currm, currc + 1);
    }
    return ans;
  }

  /**
   * Leetcode 1006 : Clumsy Factorial.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param n int
   * @return int
   */
  public int clumsy(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    if (n == 3) {
      return 6;
    }
    int ans = n * (n - 1) / (n - 2) + n - 3;
    int i;
    for (i = n - 4; i >= 4; i -= 4) {
      ans -= arithm(i);
    }
    if (i == 0) {
      return ans;
    }
    if (i == 1) {
      return ans - 1;
    }
    if (i == 2) {
      return ans - 2;
    }
    if (i == 3) {
      return ans - 6;
    }
    return ans;
  }

  public int arithm(int n) {
    return n * (n - 1) / (n - 2) - n + 3;
  }



}
