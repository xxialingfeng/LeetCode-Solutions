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
}
