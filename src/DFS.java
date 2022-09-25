/**.
 * A collection for leetcode problems related to DFS
 */
public class DFS {

  /**.
   * Leetcode 526: Beautiful Arrangement
   * @Difficulty: Medium
   * @OptimalComplexity: O(n!) & O(n)
   * @param n int
   * @return int
   */
  public int countArrangement(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    boolean[] visited = new boolean[n+1];
    return dfs526(visited, 1, n);
  }

  /**.
   * DFS function find all the possibilities
   * @param visited boolean[]
   * @param step int
   * @param n int
   * @return int
   */
  public int dfs526(boolean[] visited, int step, int n) {
    if (step == n + 1) {
      return 1;
    }
    int ans = 0;
    for (int i = 1; i <= n; i++) {
      if (!visited[i] && (i % step == 0 || step % i == 0)) {
        visited[i] = true;
        ans += dfs526(visited, step + 1, n);
        visited[i] = false;
      }
    }
    return ans;
  }

  int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0}; //direction array
  int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

  /**.
   * Leetcode 529: Minesweeper
   * @Difficulty: Medium
   * @OptimalComplexity: O(mn) & O(mn)
   * @param board char[][]
   * @param click int[]
   * @return char[][]
   */
  public char[][] updateBoard(char[][] board, int[] click) {
    if (board[click[0]][click[1]] == 'M') {
      board[click[0]][click[1]] = 'X';
      return board;
    }
    if (board[click[0]][click[1]] == 'E') {
      board[click[0]][click[1]] = 'B';
      dfs(board, click[0], click[1]);
    }
    return board;
  }

  /**.
   * DFS function
   * @param board char[][]
   * @param x int
   * @param y int
   */
  public void dfs(char[][] board, int x, int y) {
    int count = 0;
    //count how many mines are surrounded
    for (int i = 0; i < 8; i++) {
      int tx = x + dx[i];
      int ty = y + dy[i];
      if (tx >= 0 && tx < board.length && ty >= 0 && ty < board[0].length && board[tx][ty] == 'M') {
        count++;
      }
    }

    if (count > 0) {
      board[x][y] = (char)(count + '0');
    } else {
      board[x][y] = 'B';
      //if there is a mine surrounded.
      for (int i = 0; i < 8; i++) {
        int tx = x + dx[i];
        int ty = y + dy[i];
        if (tx >= 0 && tx < board.length && ty >= 0 && ty < board[0].length && board[tx][ty] == 'E') {
          dfs(board, tx, ty);
        }
      }
    }
  }


  private static final int M = 1000000007;
  /**
   * Leetcode 552: Student Attendance Record II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   */

  public int checkRecord(int n) {
    int[][][] memo = new int[n + 1][3][2];
    return dfs552(n, 0, 0, 0, memo);
  }

  /**
   * memoization dfs.
   * @param n int
   * @param index int
   * @param late int
   * @param absent int
   * @param memo int[][][]
   * @return int
   */

  public int dfs552 (int n, int index, int late, int absent, int[][][] memo) {
    //if current is not late, set late to zero
    if (index == n) {
      return 1;
    }
    int ans = 0;
    if (memo[index][late][absent] != 0) {
      return memo[index][late][absent];
    }
    ans = (ans + dfs552(n, index + 1, 0, absent, memo)) % M;
    if (absent < 1) {
      ans = (ans + dfs552(n, index + 1, 0, 1, memo)) % M;
    }
    if (late < 2) {
      ans = (ans + dfs552(n, index + 1, late+1, absent, memo)) % M;
    }
    memo[index][late][absent] = ans;
    return ans;
  }
}
