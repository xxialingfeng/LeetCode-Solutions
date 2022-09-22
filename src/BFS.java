import java.util.LinkedList;
import java.util.Queue;

/**
 * A collection for leetcode problems related to BFS.
 */
public class BFS {

  /**
   * Leetcode 542: 01 Matrix.
   * @Difficulty: Medium
   * @OptimalComplexity: O(mn) & O(mn)
   * @param mat int[][]
   * @return int[][]
   */
  public int[][] updateMatrix(int[][] mat) {
    //A good problem! Worth doing it again!
    int[][] vector = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m = mat.length;
    int n = mat[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (mat[i][j] == 0) {
          queue.offer(new int[]{i, j});
          visited[i][j] = true;
        } else {
          mat[i][j] = m + n;
        }
      }
    }
    while (!queue.isEmpty()) {
      int[] s = queue.poll();
      for (int[] v : vector) {
        int r = s[0] + v[0];
        int c = s[1] + v[1];
        if (r >= 0 && r < m && c >= 0 && c < n && !visited[r][c]) {
          mat[r][c] = mat[s[0]][s[1]] + 1;
          queue.offer(new int[]{r, c});
          visited[r][c] = true;
        }
      }
    }
    return mat;
  }
}
