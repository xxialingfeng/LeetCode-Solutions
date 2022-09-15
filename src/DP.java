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
}
