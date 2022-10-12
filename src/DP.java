import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
}
