import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**.
 * A collection for leetcode problems related to dynamic programming
 */
public class DP {
  /**.
  * Leetcode 514 : Freedom Trail
  * @Difficulty: Hard
  * @OptimalComplexity: O(mn2) & O(mn)
  */
  public int findRotateSteps(String ring, String key) {
    int[][] dp = new int[key.length()][ring.length()];
    List<Integer>[] pos = new List[26]; //List<Integer>[] pos to restore all the pos.
    for (int i = 0; i < 26; i++) {
      pos[i] = new ArrayList<>();
    }
    for (int i = 0; i < ring.length(); i++) {
      pos[ring.charAt(i) - 'a'].add(i);
    }
    for (int i = 0; i < key.length(); i++) {
      Arrays.fill(dp[i], 0x3f3f3f);
    }
    for (int i : pos[key.charAt(0) - 'a']) {
      dp[0][i] = Math.min(i, ring.length() - i) + 1;
    }
    for (int i = 1; i < key.length(); i++) {
      for (int j : pos[key.charAt(i) - 'a']) {
        for (int k : pos[key.charAt(i - 1) - 'a']) {
          dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + Math.min(Math.abs(j-k), ring.length() - Math.abs(j-k)) + 1);
        }
      }
    }
    return Arrays.stream(dp[key.length() - 1]).min().getAsInt();
  }

  /**
   * Leetcode 546: Remove Boxes.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n4) & O(n3)
   * @param boxes int[]
   * @return int
   */
  public int removeBoxes(int[] boxes) {
    int length = boxes.length;
    int[][][] dp;
    dp = new int[length][length][length];
    return calculatePoints(dp, boxes, 0, length - 1, 0);
  }

  /**
   * interval DP.
   * @param dp int[][][]
   * @param boxes int[]
   * @param l int
   * @param r int
   * @param k int
   * @return int
   */
  public int calculatePoints(int[][][] dp, int[] boxes, int l, int r, int k) {
    if (l > r) {
      return 0;
    }
    if (dp[l][r][k] == 0) {
      dp[l][r][k] = calculatePoints(dp, boxes, l, r - 1, 0) + (k + 1) * (k + 1);
      for (int i = l; i < r; i++) {
        if (boxes[i] == boxes[r]) {
          dp[l][r][k] = Math.max(dp[l][r][k], calculatePoints(dp, boxes, l, i, k + 1) + calculatePoints(dp, boxes, i + 1, r - 1, 0));
        }
      }
    }
    return dp[l][r][k];
  }

  /**
   * Leetcode 552: Student Attendance Record II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   */
  public int checkRecord(int n) {
    //Can optimize since dp[i][][] is always transferred from dp[i-1][][].
    long[][][] dp = new long[n + 1][2][3];
    int M = 1000000007;
    dp[0][0][0] = 1;
    dp[0][0][1] = 1;
    dp[0][1][0] = 1;
    for (int i = 1 ;i <= n;i++) {
      dp[i][0][0] = (dp[i - 1][0][0] + dp[i - 1][0][1] + dp[i - 1][0][2]) % M;
      dp[i][0][1] = dp[i - 1][0][0] % M;
      dp[i][0][2] = dp[i - 1][0][1] % M;
      dp[i][1][0] = (dp[i - 1][0][0] + dp[i - 1][0][1] + dp[i - 1][0][2]
          + dp[i - 1][1][0] + dp[i - 1][1][1] + dp[i - 1][1][2]) % M;
      dp[i][1][1] = dp[i - 1][1][0] % M;
      dp[i][1][2] = dp[i - 1][1][1] % M;
    }
    int sum = 0;
    for (int i = 0;i < 2;i++) {
      for (int j = 0;j < 3;j++) {
        sum += dp[n - 1][i][j];
        sum %= M;
      }
    }
    return sum;
  }

  /**
   * Leetcode 576: Out of Boundary Paths.
   * @Difficulty: Medium
   * @OptimalComplexity: O(maxMove*m*n) & O(m*n)
   * @param m int
   * @param n int
   * @param maxMove int
   * @param startRow int
   * @param startColumn int
   * @return int
   */
  public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
    final int MOD = 1000000007;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int outCounts = 0;
    int[][] dp = new int[m][n];
    dp[startRow][startColumn] = 1;
    for (int i = 0; i < maxMove; i++) {
      int[][] dpNew = new int[m][n];
      for (int j = 0; j < m; j++) {
        for (int k = 0; k < n; k++) {
          int count = dp[j][k];
          if (count > 0) {
            for (int[] direction : directions) {
              int j1 = j + direction[0], k1 = k + direction[1];
              if (j1 >= 0 && j1 < m && k1 >= 0 && k1 < n) {
                dpNew[j1][k1] = (dpNew[j1][k1] + count) % MOD;
              } else {
                outCounts = (outCounts + count) % MOD;
              }
            }
          }
        }
      }
      dp = dpNew;
    }
    return outCounts;
  }

  /**
   * Leetcode 629 : K Inverse Pairs Array.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * k) & O(k)
   * @param n int
   * @param k int
   * @return int
   */
  public int kInversePairs(int n, int k) {
    int mod = 1000000007;
    if (k == 0) {
      return 1;
    }
    int[][] dp = new int[n + 1][k + 1];
    for (int i = 1; i <= n; i++) {
      dp[i][0] = 1;
    }
    dp[1][0] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 1; j <= k; j++) {
        dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
        if (j >= i) {
          dp[i][j] = (dp[i][j] + mod - dp[i - 1][j - i]) % mod;
        }
      }
    }
    return dp[n][k];
  }

  /**
   * Leetcode 630 : Course Schedule III.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param courses int[][]
   * @return int
   */
  public int scheduleCourse(int[][] courses) {
    if (courses.length == 1) {
      return 1;
    }
    Arrays.sort(courses, (a, b) -> (a[1] - b[1]));
    int[] dp = new int[10001];
    int ans = 0;
    for (int[] course : courses) {
      int start = course[0];
      int end = course[1];
      for (int i = end; i >= start; i--) {
        dp[i] = Math.max(dp[i - start] + 1, dp[i]);
        ans = Math.max(ans, dp[i]);
      }
    }
    return ans;
  }

  /**
   * Leetcode 646 : Maximum Length of Pair Chain.
   * @param pairs int[][]
   * @return int
   */
  public int findLongestChain(int[][] pairs) {
    Arrays.sort(pairs, (a, b) -> (a[0] - b[0]));
    int[] dp = new int[pairs.length + 1];
    Arrays.fill(dp, 1);
    for (int i = 0; i < pairs.length; i++) {
      for (int j = 0; j < i; j++) {
        if (pairs[i][0] > pairs[j][1]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }
    return dp[pairs.length - 1];
  }

  /**
   * Leetcode 650 : 2 Keys Keyboard.
   * @param n int
   * @return int
   */
  public int minSteps(int n) {
    if (n == 1) {
      return 0;
    }
    int[] dp = new int[n + 1];
    dp[1] = 0;
    for (int i = 2; i <= n; i++) {
      dp[i] = i;
      for (int j = 2; j <= i; j++) {
        if (i % j == 0) {
          dp[i] = dp[j] + dp[i/j];
          break;
        }
      }
    }
    return dp[n];
  }

  /**
   * Leetcode 664 : Strange Printer.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n3) & O (n2)
   * @param s String
   * @return int
   */
  public int strangePrinter(String s) {
    int[][] dp = new int[s.length()][s.length()];
    for (int i = 0; i < s.length(); i++) {
      dp[i][i] = 1;
    }
    for (int i = s.length() - 1; i >= 0; i--) {
      for (int j = i + 1; j < s.length(); j++) {
        if (s.charAt(i) == s.charAt(j)) {
          dp[i][j] = dp[i][j - 1];
        } else {
          int min = Integer.MAX_VALUE;
          for (int k = i; k < j; k++) {
            min = Math.min(min, dp[i][k] + dp[k + 1][j]);
          }
          dp[i][j] = min;
        }
      }
    }
    return dp[0][s.length() - 1];
  }

  /**
   * Leetcode 673 : Number of Longest Increasing Subsequence.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n)
   * @param nums int[]
   * @return int
   */
  public int findNumberOfLIS(int[] nums) {
    if (nums.length == 1) {
      return 1;
    }

    int[] dp = new int[nums.length + 1];
    int[] res = new int[nums.length];
    Arrays.fill(dp, 1);
    Arrays.fill(res, 1);
    int max = 1;
    for (int i = 1; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          if (dp[i] < dp[j] + 1) {
            dp[i] = dp[j] + 1;
            res[i] = res[j];
          } else if (dp[i] == dp[j] + 1) {
            res[i] += res[j];
          }
        }
      }
      max = Math.max(max, dp[i]);
    }

    int ans = 0;
    for (int i = 0; i < nums.length; i++) {
      if (dp[i] == max) {
        ans += res[i];
      }
    }
    return ans;
  }

  int[][] directions = new int[][]{{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}};

  /**
   * Leetcode 688 : Knight Probability in Chessboard.
   * @OptimalComplexity: O（n2 * k) & O(n2 * k)
   * @Difficulty: Medium
   * @param n int
   * @param k int
   * @param row int
   * @param column int
   * @return double
   */
  public double knightProbability(int n, int k, int row, int column) {
    double[][][] dp = new double[k+1][n][n];
    for (int step = 0; step <= k; step++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (step == 0) {
            dp[step][i][j] = 1;
          } else {
            for (int[] direction : directions) {
              int newR = direction[0] + i;
              int newC = direction[1] + j;
              if (newR >= 0 && newR < n && newC >= 0 && newC < n) {
                dp[step][i][j] += dp[step - 1][newR][newC] / 8.0;
              }
            }
          }
        }
      }
    }
    return dp[k][row][column];
  }

  /**
   * Leetcode 712 : Minimum ASCII Delete Sum for Two Strings.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1)
   * @param s1 string
   * @param s2 string
   * @return int
   */
  public int minimumDeleteSum(String s1, String s2) {
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 1; i <= s1.length(); i++) {
      dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
    }
    for (int i = 1; i <= s2.length(); i++) {
      dp[0][i] = dp[0][i - 1] + s2.charAt(i - 1);
    }
    for (int i = 1; i <= s1.length(); i++) {
      for (int j = 1; j <= s2.length(); j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
        }
      }
    }
    return dp[s1.length()][s2.length()];
  }

  /**
   * Leetcode 714 : Best Time to Buy and Sell Stock with Transaction Fee.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param prices int[]
   * @param fee int
   * @return int
   */
  public int maxProfit(int[] prices, int fee) {
    int[] dp = new int[prices.length + 1]; // with s
    int[] dp2 = new int[prices.length + 1]; // without s
    dp[0] = -prices[0];
    for (int i = 1; i < prices.length; i++) {
      dp[i] = Math.max(dp2[i - 1] - prices[i], dp[i - 1]);
      dp2[i] = Math.max(dp2[i - 1], dp[i - 1] + prices[i] - fee);
    }
    return Math.max(dp[prices.length - 1], dp2[prices.length - 1]);
  }

  /**
   * Leetcode 718 : Maximum Length of Repeated Subarray.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1)
   * @param nums1 int[]
   * @param nums2 int[]
   * @return int
   */
  public int findLength(int[] nums1, int[] nums2) {
    int res = 0;
    int[][] dp = new int[nums1.length + 1][nums2.length + 1];
    for (int i = 1; i <= nums1.length; i++) {
      for (int j = 1; j <= nums2.length; j++) {
        if (nums1[i - 1] == nums2[j - 1]) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        }
        res = Math.max(res, dp[i][j]);
      }
    }
    return res;
  }

  /**
   * Leetcode 1928: Minimum Cost to Reach Destination in Time.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param maxTime int
   * @param edges int[][]
   * @param passingFees int[]
   * @return int
   */
  public int minCost(int maxTime, int[][] edges, int[] passingFees) {
    int[][] dp = new int[maxTime + 1][passingFees.length];
    for (int i = 0; i < maxTime + 1; i++) {
      Arrays.fill(dp[i], Integer.MAX_VALUE/2);
    }
    dp[0][0] = passingFees[0];
    for (int i = 1; i <= maxTime; i++) {
      for (int[] edge : edges) {
        int from = edge[0];
        int to = edge[1];
        int cost = edge[2];
        if (i >= cost) {
          dp[i][to] = Math.min(dp[i][to], dp[i - cost][from] + passingFees[to]);
          dp[i][from] = Math.min(dp[i][from], dp[i - cost][to] + passingFees[from]);
        }
      }
    }
    int minFees = Integer.MAX_VALUE/2;
    for (int time = 1; time <= maxTime; ++time) {
      minFees = Math.min(minFees, dp[time][passingFees.length - 1]);
    }
    return minFees == Integer.MAX_VALUE/2 ? -1 : minFees;
  }

  /**
   * Leetcode 787 : Cheapest Flights Within K Stops.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param n int
   * @param flights int[][]
   * @param src int
   * @param dst int
   * @param k int
   * @return int
   */
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[][] dp = new int[n][k + 2];
    for (int i = 0; i < n; i++) {
      Arrays.fill(dp[i], Integer.MAX_VALUE);
    }
    for (int j = 0; j <= k + 1; j++) {
      dp[src][j] = 0;
    }
    for (int l = 1; l <= k + 1; l++) {
      for (int[] flight : flights) {
        int from = flight[0];
        int to = flight[1];
        int cost = flight[2];
        if (dp[from][l - 1] != Integer.MAX_VALUE) {
          dp[to][l] = Math.min(dp[to][l], dp[from][l - 1] + cost);
        }
      }
    }
    return dp[dst][k + 1] == Integer.MAX_VALUE ? -1 : dp[dst][k + 1];
  }

  /**
   * Leetcode 940 : Distinct Subsequences II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(26)
   * @param s String
   * @return int
   */
  public int distinctSubseqII(String s) {
    int n = s.length();
    long[] dp = new long[26];
    dp[s.charAt(0) - 'a'] = 1;
    for (int i = 1; i < n; i++) {
      dp[s.charAt(i) - 'a'] = (1 + Arrays.stream(dp).sum()) % 1000000007;
    }
    return (int) (Arrays.stream(dp).sum() % 1000000007);
  }

  /**
   * Leetcode 740 : Delete and Earn.
   * @Difficulty: Mediun
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int deleteAndEarn(int[] nums) {
    int[] s = new int[10001];
    for (int num : nums) {
      s[num] += num;
    }
    int[] dp = new int[s.length + 1];
    dp[0] = s[0];
    dp[1] = Math.max(s[0], s[1]);
    for (int i = 2; i < s.length; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + s[i]);
    }
    return dp[s.length - 1];
  }

  /**
   * Leetcode 741 : Cherry Pickup.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n3) & O(n3)
   * @param grid int[][]
   * @return int
   */
  public int cherryPickup(int[][] grid) {
    int n = grid.length;
    int[][][] f = new int[n * 2 - 1][n][n];
    for (int i = 0; i < n * 2 - 1; ++i) {
      for (int j = 0; j < n; ++j) {
        Arrays.fill(f[i][j], Integer.MIN_VALUE);
      }
    }
    f[0][0][0] = grid[0][0];
    for (int k = 1; k < n * 2 - 1; ++k) {
      for (int x1 = Math.max(k - n + 1, 0); x1 <= Math.min(k, n - 1); ++x1) {
        int y1 = k - x1;
        if (grid[x1][y1] == -1) {
          continue;
        }
        for (int x2 = x1; x2 <= Math.min(k, n - 1); ++x2) {
          int y2 = k - x2;
          if (grid[x2][y2] == -1) {
            continue;
          }
          int res = f[k - 1][x1][x2]; // both go right
          if (x1 > 0) {
            res = Math.max(res, f[k - 1][x1 - 1][x2]); // down, right
          }
          if (x2 > 0) {
            res = Math.max(res, f[k - 1][x1][x2 - 1]); // right, down
          }
          if (x1 > 0 && x2 > 0) {
            res = Math.max(res, f[k - 1][x1 - 1][x2 - 1]); // both go down
          }
          res += grid[x1][y1];
          if (x2 != x1) { //avoid picking the same cherry
            res += grid[x2][y2];
          }
          f[k][x1][x2] = res;
        }
      }
    }
    return Math.max(f[n * 2 - 2][n - 1][n - 1], 0);
  }

  /**
   * Leetcode 746 : Min Cost Climbing Stairs.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param cost int[]
   * @return int
   */
  public int minCostClimbingStairs(int[] cost) {
    if (cost.length == 2) {
      return Math.min(cost[0], cost[1]);
    }
    int[] dp = new int[cost.length + 1];
    System.arraycopy(cost, 0, dp, 0, cost.length);
    for (int i = 2; i < cost.length; i++) {
      dp[i] = Math.min(dp[i - 1] + cost[i], dp[i - 2] + cost[i]);
    }
    return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
  }

  /**
   * Leetcode 790 : Domino and Tromino Tiling.
   * @Difficulty: Medium
   * @OptimalComplexity: On(n) & O(1)
   * @param n int
   * @return int
   */
  public int numTilings(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    if (n == 3) {
      return 5;
    }
    long[] dp = new long[n + 1];
    dp[1] = 1;
    dp[2] = 2;
    dp[3] = 5;
    for (int i = 4; i <= n; i++) {
      dp[i] = (2 * dp[i - 1] + dp[i - 3]) % 1000000007;
    }
    return (int)dp[n];
  }

  /**
   * Leetcode 799 : Champagne Tower.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1)
   * @param poured int
   * @param query_row int
   * @param query_glass int
   * @return double
   */
  public double champagneTower(int poured, int query_row, int query_glass) {
    double[][] dp = new double[query_row + 2][query_row + 2];
    dp[0][0] = poured;
    for (int i = 0; i <= query_row; i++) {
      for (int j = 0; j <= i; j++) {
        if (dp[i][j] >= 1) {
          double remain = dp[i][j] - 1;
          dp[i][j] = 1;

          dp[i + 1][j] += remain / 2;
          dp[i + 1][j + 1] += remain / 2;
        }
      }
    }
    return dp[query_row][query_glass];
  }

  /**
   * leetcode 801 : Minimum Swaps To Make Sequences Increasing.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(1)
   * @param nums1 int[]
   * @param nums2 int[]
   * @return int
   */
  public int minSwap(int[] nums1, int[] nums2) {
    int[][] dp = new int[nums1.length][2];
    for (int[] arr : dp) {
      Arrays.fill(arr, Integer.MAX_VALUE);
    }
    dp[0][0] = 0;
    dp[0][1] = 1;
    for (int i = 1; i < nums1.length; i++) {
      boolean flag1 = nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1];
      boolean flag2 = nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1];
      if (flag1) {
        dp[i][0] = dp[i - 1][0];
        dp[i][1] = dp[i - 1][1] + 1;
      }
      if (flag2) {
        dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
        dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + 1);
      }
    }
    return Math.min(dp[nums1.length - 1][0], dp[nums1.length - 1][1]);
  }

  /**
   * leetcode 801 : Minimum Swaps To Make Sequences Increasing.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(1)
   * @param nums1 int[]
   * @param nums2 int[]
   * @return int
   */
  public int minSwapBS(int[] nums1, int[] nums2) {
    int n = nums1.length;
    int a = 0;
    int b = 1;
    for (int i = 1; i < n; i++) {
      int at = a; //current
      int bt = b; //current
      a = b = n; //next val
      if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1])  {
        a = Math.min(a, at);
        b = Math.min(b, bt + 1);
      }
      if (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]) {
        a = Math.min(a, bt);
        b = Math.min(b, at + 1);
      }
    }
    return Math.min(a, b);
  }

  /**
   * Leetcode 805 : Split Array With Same Average.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2 * sum) & O(n * sum)
   * @param nums int[]
   * @return boolean
   */
  public boolean splitArraySameAverage(int[] nums) {
    if (nums.length == 1) {
      return false;
    }
    int n = nums.length;
    int m = n / 2;
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }
    boolean isPossible = false;
    for (int i = 1; i <= m; i++) {
      if (sum * i % n == 0) {
        isPossible = true;
        break;
      }
    }
    if (!isPossible) {
      return false;
    }
    Set<Integer>[] dp = new Set[m + 1];
    for (int i = 0; i <= m; i++) {
      dp[i] = new HashSet<Integer>();
    }
    dp[0].add(0);
    for (int num : nums) {
      for (int i = m; i >= 1; i--) {
        for (int x : dp[i - 1]) {
          int curr = x + num;
          if (curr * n == sum * i) {
            return true;
          }
          dp[i].add(curr);
        }
      }
    }
    return false;
  }

  /**
   * Leetcode 813 : Largest Sum of Averages.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2 * k) & O(n2)
   * @param nums int[]
   * @param k int
   * @return double
   */
  public double largestSumOfAverages(int[] nums, int k) {
    int n = nums.length;
    double[][] dp = new double[n + 1][k + 1];
    double[] prefix = new double[nums.length + 1];
    for (int i = 1; i <= nums.length; i++) {
      prefix[i] = nums[i - 1] + prefix[i - 1];
      dp[i][1] = prefix[i] / i;
    }
    for (int i = 1; i <= nums.length; i++) {
      for (int K = 2; K <= k; K++) {
        for (int j = 0; j < i; j++) {
          dp[i][K] = Math.max(dp[i][K], dp[j][K - 1] + (prefix[i] - prefix[j]) / (i - j));
        }
      }
    }
    return dp[nums.length][k];
  }

  /**
   * Leetcode 837 : New 21 Game.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(1)
   * @param n int
   * @param k int
   * @param maxPts int
   * @return double
   */
  public double new21Game(int n, int k, int maxPts) {
    double[] dp = new double[k + maxPts];
    if (k == 0) {
      return 1.0;
    }
    for (int i = k; i <= n && i < dp.length; i++) {
      dp[i] = 1.0;
    }
    for (int i = k - 1; i >= 0; i--) {
      for (int j = 1; j <= maxPts; j++) {
        dp[i] += dp[i + j] / maxPts;
      }
    }
    return dp[0];
  }

  /**
   * Leetcode 152 : Maximum Product Subarray.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int maxProduct(int[] nums) {
    int[] dpMax = new int[nums.length + 1];
    int[] dpMin = new int[nums.length + 1];
    Arrays.fill(dpMin, Integer.MAX_VALUE);
    Arrays.fill(dpMax, Integer.MIN_VALUE);
    dpMax[0] = 1;
    dpMin[0] = 1;
    for (int i = 1; i <= nums.length; i++) {
      dpMax[i] = Math.max(dpMax[i - 1] * nums[i - 1], Math.max(dpMin[i - 1] * nums[i - 1], nums[i - 1]));
      dpMin[i] = Math.min(dpMin[i - 1] * nums[i - 1], Math.min(dpMax[i - 1] * nums[i - 1], nums[i - 1]));
    }
    int ans = nums[0];
    for (int i = 1; i <= nums.length; i++) {
      ans = Math.max(ans, dpMax[i]);
    }
    return ans == Integer.MIN_VALUE ? 0 : ans;
  }

  /**
   * Leetcode 871 : Minimum Number of Refueling Stops.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n^2) & O(n)
   * @param target int
   * @param startFuel int
   * @param stations int[][]
   * @return int
   */
  public int minRefuelStops(int target, int startFuel, int[][] stations) {
    if (target == startFuel) {
      return 0;
    }
    int[] dp = new int[stations.length + 1];
    dp[0] = startFuel;
    for (int i = 0; i < stations.length; i++) {
      for (int j = i; j >= 0; j--) {
        if (dp[j] >= stations[i][0]) {
          dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
        }
      }
    }
    for (int i = 0; i <= stations.length; i++) {
      if (dp[i] >= target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Leetcode 873 : Length of Longest Fibonacci Subsequence.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param arr int[]
   * @return int
   */
  public int lenLongestFibSubseq(int[] arr) {
    int[][] dp = new int[arr.length][arr.length];
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      map.put(arr[i], i);
    }
    int ans = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = i - 1; j >= 0 && arr[j] * 2 > arr[i]; j--) {
        int k = map.getOrDefault(arr[i] - arr[j], -1);
        if (k >= 0) {
          dp[j][i] = Math.max(dp[k][j] + 1, 3);
        }
        ans = Math.max(ans, dp[j][i]);
      }
    }
    return ans;
  }

  /**
   * leetcode 879 : Profitable Schemes.
   * @Difficulty: Hard
   * @OptimalComplexity: O(len * n * minProfit) & O(len * n * minProfit)
   * @param n int
   * @param minProfit int
   * @param group int[]
   * @param profit int[]
   * @return int
   */
  public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
    int[][][] dp = new int[group.length + 1][n + 1][minProfit + 1];
    dp[0][0][0] = 1;
    for (int i = 1; i <= group.length; i++) {
      int member = group[i - 1];
      int earn = profit[i - 1];
      for (int j = 0; j <= n; j++) {
        for (int k = 0; k <= minProfit; k++) {
          if (j < member) {
            dp[i][j][k] = dp[i - 1][j][k];
          } else {
            dp[i][j][k] = (dp[i - 1][j][k] + dp[i - 1][j - member][Math.max(0, k - earn)] ) % 1000000007;
          }
        }
      }
    }
    int sum = 0;
    for (int j = 0; j <= n; j++) {
      sum = (sum + dp[group.length][j][minProfit]) % 1000000007;
    }
    return sum;
  }

  /**
   * Leetcode 887 : Super Egg Drop.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * k) & O(n * k);
   * @param k int
   * @param n int
   * @return int
   */
  public int superEggDrop(int k, int n) {
    if (n == 1) {
      return 1;
    }
    int[][] dp = new int[n + 1][k + 1];
    for (int i = 1; i <= k; ++i) {
      dp[1][i] = 1;
    }
    int count = -1;
    for (int i = 2; i <= n; ++i) {
      for (int j = 1; j <= k; ++j) {
        dp[i][j] = 1 + dp[i - 1][j - 1] + dp[i - 1][j];
      }
      if (dp[i][k] >= n) {
        count = i;
        break;
      }
    }
    return count;
  }

  int ans;

  /**
   * Leetcode 902 : Numbers At Most N Given Digit Set.
   * @Difficulty: hard
   * @OptimalComplexity: O(n * 10) & O(n)
   * @param digits list of string
   * @param n int
   * @return int
   */
  public int atMostNGivenDigitSet(String[] digits, int n) {
    String t = String.valueOf(n);
    int len = t.length();
    int arrlen = digits.length;
    ans = 0;
    for (int i = 1; i < len; i++) {
      ans += Math.pow(arrlen, i);
    }
    int[] dp = new int[len + 1];
    dp[0] = 1;
    for (int i = 1; i <= len; i++) {
      for (String str : digits) {
        if (Integer.valueOf(str) < Integer.valueOf(t.substring(t.length() - i, t.length() - i + 1))) {
          dp[i] += Math.pow(digits.length, i - 1);
        } else if (str.equals(t.substring(t.length() - i, t.length() - i + 1))) {
          dp[i] += dp[i - 1];
        }
      }
    }
    //dfs(digits, n, 0, 0, len);
    return ans + dp[t.length()];
  }

  /**
   * dfs method for leetcode 902(TLE).
   * @param digits list of string
   * @param n int
   * @param currsum long
   * @param idx int
   * @param len int
   */
  public void dfs(String[] digits, int n, long currsum, int idx, int len) {
    if (idx == len) {
      ans++;
      return;
    }
    for (int i = 0; i < digits.length; i++) {
      long newsum = currsum * 10 + Integer.parseInt(digits[i]);
      if (newsum > n) {
        break;
      }
      dfs(digits, n, newsum, idx + 1, len);
    }
  }

  int mod = 1000000007;

  /**
   * Leetcode 903 : Valid Permutations for DI Sequence.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n3) & O(n3)
   * @param s string
   * @return int
   */
  public int numPermsDISequence(String s) {
    int len = s.length();
    int[][] dp = new int[len + 1][len + 1];
    dp[0][0] = 1;
    for (int i = 1; i <= len; i++) {
      for (int j = 0; j <= i; j++) {
        if (s.charAt(i - 1) == 'D') {
          for (int k = j; k < i; k++) {
            dp[i][j] = (dp[i][j] + dp[i - 1][k]) % mod;
          }
        } else {
          for (int k = 0; k < j; k++) {
            dp[i][j] = (dp[i][j] + dp[i - 1][k]) % mod;
          }
        }
      }
    }
    int res = 0;
    for (int i = 0; i <= len; i++) {
      res = (res + dp[len][i]) % mod;
    }
    return res;
  }

  /**
   * Leetcode 920 : Number of Music Playlists.
   * @Difficulty: hard
   * @OptimalComplexity: O(n * l) * O(n * l)
   * @param n int
   * @param goal int
   * @param k int
   * @return int
   */
  public int numMusicPlaylists(int n, int goal, int k) {
    long[][] dp = new long[goal + 1][n + 1];
    dp[0][0] = 1;
    for (int i = 1; i <= goal; i++) {
      for (int j = 1; j <= n; j++) {
        dp[i][j] = (dp[i][j] +  dp[i - 1][j - 1] * (n - j + 1) + dp[i - 1][j] * Math.max(j - k, 0)) % 1000000007;
      }
    }
    return (int) dp[goal][n];
  }

  /**
   * Leetcode 926 : Flip String to Monotone Increasing.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m) & O(m)
   * @param s string
   * @return int
   */
  public int minFlipsMonoIncr(String s) {
    int m = s.length();
    int[][] dp = new int[m + 1][2];
    for (int i = 1; i <= m; i++) {
      if (s.charAt(i - 1) == '0') {
        dp[i][0] = dp[i - 1][0];
        dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
      } else {
        dp[i][0] = dp[i - 1][0] + 1;
        dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]);
      }
    }
    return Math.min(dp[m][0], dp[m][1]);
  }

  private final int[][] path = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {3, 9, 0}, {}, {1, 7, 0}, {2, 6}, {1, 3}, {4, 2}};

  /**
   * Leetcode 935 : Knight Dialer.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * 10 * m) & O(n * 10)
   * @param n int
   * @return i t
   */
  public int knightDialer(int n) {
    int[][] dp = new int[n][10];
    for (int num = 0; num < 10; num++) {
      dp[0][num] = 1;
    }

    for (int time = 1; time < n; time++) {
      for (int num = 0; num < 10; num++) {
        for (int arrive : path[num]) {
          dp[time][num] = (dp[time][num] + dp[time - 1][arrive]) % 1000000007;
        }
      }
    }
    int res = 0;
    for (int num = 0; num < 10; num++) {
      res = (res + dp[n - 1][num]) % 1000000007;
    }
    return res;
  }

  /**
   * Leetcode 956 : Tallest Billboard.
   * @Difficulty: Hard
   * @Difficulty: O(n * m) & O(n2)
   * @param rods int[]
   * @return int
   */
  public int tallestBillboard(int[] rods) {
    if (rods.length <= 1) {
      return 0;
    }
    if (rods.length == 2 && rods[0] != rods[1]) {
      return 0;
    }
    int n = rods.length;
    int sum = 0;
    for (int rod : rods) {
      sum += rod;
    }
    int[][] dp = new int[rods.length + 1][sum + 1];
    for (int i = 1; i <= rods.length; i++) {
      for (int j = 0; j <= sum; j++) {
        if (dp[i - 1][j] < j) {
          continue;
        }
        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
        int k = j + rods[i - 1];
        dp[i][k] = Math.max(dp[i][k], dp[i - 1][j] + rods[i - 1]);
        k = Math.abs(j - rods[i - 1]);
        dp[i][k] = Math.max(dp[i][k], dp[i - 1][j] + rods[i - 1]);
      }
    }
    return dp[rods.length][0] / 2;
  }

  /**
   * Leetcode 960 : Delete Columns to Make Sorted III.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * m * m) & O(m)
   * @param strs list of strings
   * @return int
   */
  public int minDeletionSize(String[] strs) {
    int[] dp = new int[strs[0].length()];
    Arrays.fill(dp, 1);
    for (int i = 0; i < strs[0].length(); i++) {
      for (int j = 0; j < i; j++) {
        boolean flag = true;
        for (String str : strs) {
          if (str.charAt(j) > str.charAt(i)) {
            flag = false;
            break;
          }
        }
        if (flag) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }
    int keep_len = 0;
    for (int d : dp) {
      keep_len = Math.max(d, keep_len);
    }
    return strs[0].length() - keep_len;
  }

  /**
   * Leetcode 975 : Odd Even Jump.
   * @Difficulty: Hard
   * @OptimalComplexity: O(
   * @param arr O(nlogn) & O(n)
   * @return int[]
   */
  public int oddEvenJumps(int[] arr) {
    int len = arr.length;
    int[] dp = new int[len];
    TreeMap<Integer, Integer> map = new TreeMap<>();
    dp[len - 1] = 3;
    map.put(arr[len - 1], len - 1);
    int sum = 1;
    for (int i = len - 2; i >= 0; i--) {
      Integer j;
      if ((j = map.ceilingKey(arr[i])) != null && dp[map.get(j)] >= 2) {
        dp[i] = 1;
        sum++;
      }
      if ((j = map.floorKey(arr[i])) != null && ((j = map.get(j)) == len - 1 || dp[j] == 1 || dp[j] == 3)) {
        dp[i] += 2;
      }
      map.put(arr[i], i);
    }
    return sum;
  }

  /**
   * Leetcode 983 : Minimum Cost For Tickets.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param days int[]
   * @param costs int[]
   * @return int
   */
  public int mincostTickets(int[] days, int[] costs) {
    int[] dp = new int[days[days.length - 1] + 1];
    Set<Integer> set = new HashSet<>();
    for (int day : days) {
      set.add(day);
    }
    for (int i = 1; i <= days[days.length - 1]; i++) {
      if (set.contains(i)) {
        dp[i] = Math.min(dp[i - 1] + costs[0], Math.min(dp[i - 7 > 0 ? i - 7 : 0] + costs[1], dp[i - 30 > 0 ? i - 30 : 0] + costs[2]));
      } else {
        dp[i] = dp[i - 1];
      }
    }
    return dp[days[days.length - 1]];
  }

  /**
   * Leetcode 1000 : Minimum Cost to Merge Stones.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n^3) & O(n^2)
   * @param stones int[][]
   * @param k int
   * @return int
   */
  public int mergeStones(int[] stones, int k) {
    int n = stones.length;
    if (n == 1) {
      return 0;
    }
    if ((n - 1) % (k - 1) != 0) {
      return -1;
    }
    int[][] dp = new int[n][n];
    int[] prefix = new int[stones.length + 1];
    for (int i = 1; i <= stones.length; i++) {
      prefix[i] = prefix[i - 1] + stones[i - 1];
    }
    for (int j = 1; j < n; j++) {
      for (int i = j - 1; i >= 0; i--) {
        dp[i][j] = 0x3f3f3f3f;
        for (int l = i; l < j; l += k - 1) {
          dp[i][j] = Math.min(dp[i][j], dp[i][l] + dp[l + 1][j]);
        }
        if ((j - i) % (k - 1) == 0) {
          dp[i][j] += prefix[j + 1] - prefix[i];
        }
      }
    }
    return dp[0][n - 1];

  }

}
