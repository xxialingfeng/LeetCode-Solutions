
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

class Node {
  public int val;
  public List<Node> children;

  public Node() {}

  public Node(int _val) {
    val = _val;
  }

  public Node(int _val, List<Node> _children) {
    val = _val;
    children = _children;
  }
}

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

  /**
   * Leetcode 559:  Maximum Depth of N-ary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(height) Recursion will use stack space, yet stack
   *      space is determined by the depth of recursion.
   * @param root Node
   * @return int
   */
  public int maxDepth(Node root) {
    int max = 1;
    if (root == null) {
      return 0;
    }
    if (root.children == null) {
      return 1;
    }
    for (Node node : root.children) {
      max = Math.max(max, 1 + maxDepth(node));
    }
    return max;
  }

  private int[][][] memo;
  private final int mod = 1000000007;

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
    memo = new int[m][n][maxMove + 1];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        Arrays.fill(memo[i][j], -1);
      }
    }
    return dfs576(0, maxMove, m, n, startRow, startColumn);
  }

  /**
   * DFS + memoization.
   * @param index int
   * @param maxMove int
   * @param m int
   * @param n int
   * @param startRow int
   * @param startColumn int
   * @return int
   */
  public int dfs576(int index, int maxMove, int m, int n, int startRow, int startColumn) {
    if (startRow < 0 || startRow >= m || startColumn < 0 || startColumn >= n) {
      return 1;
    }
    if (index >= maxMove) {
      return 0;
    }

    if (memo[startRow][startColumn][index] != -1) {
      return memo[startRow][startColumn][index];
    }
    int ans = 0;
    ans =  (ans + dfs576(index + 1, maxMove, m, n, startRow + 1, startColumn)) % mod;
    ans =  (ans + dfs576(index + 1, maxMove, m, n, startRow - 1, startColumn)) % mod;
    ans =  (ans + dfs576(index + 1, maxMove, m, n, startRow, startColumn + 1)) % mod;
    ans =  (ans + dfs576(index + 1, maxMove, m, n, startRow, startColumn - 1)) % mod;
    memo[startRow][startColumn][index] = ans;
    return ans;
  }

  List<Integer> res = new ArrayList<Integer>();

  /**
   * Leetcoee 589: N-ary Tree Preorder Traversal.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root Node
   * @return list
   */
  public List<Integer> preorder(Node root) {
    //递归版

    if (root == null)
      return res;
    res.add(root.val);
    for (Node child:root.children) {
      preorder(child);
    }

    return res;
  }


  List<Integer> list = new ArrayList<>();

  /**
   * Leetcode 590 : N-ary Tree Postorder Traversal.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   */
  public List<Integer> postorder(Node root) {
    dfs590(root);
    return list;
  }

  /**
   * dfs method.
   * @param root Node
   */
  public void dfs590(Node root) {
    if (root == null) {
      return;
    }
    for (Node node : root.children) {
      dfs590(node);
    }
    list.add(root.val);
  }

  int max_num;
  int res600 = 1;

  /**
   * Leetcode 600 : Non-negative Integers without Consecutive Ones.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param n int
   * @return int
   */
  public int findIntegers(int n) {
    max_num = n;
    dfs600(1);
    return res600;
  }

  /**
   * dfs method.
   * @param n int
   */
  public void dfs600(int n) {
    if (n > max_num)
      return ;
    res600++;
    if (n % 2 == 1) {
      dfs600(n << 1);
    }
    else {
      dfs600(n << 1);
      dfs600((n << 1) + 1);
    }
  }

  StringBuilder sb606 = new StringBuilder();

  /**
   * Leetcode 606 : Construct String from Binary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return String
   */
  public String tree2str606(TreeNode root) {
    dfs606(root);
    return sb606.toString();
  }

  /**
   * dfs method.
   * @param root TreeNode
   */
  public void dfs606(TreeNode root) {
    if (root == null) {
      return;
    }
    sb606.append(root.val);
    if (root.left != null || root.right != null) {
      sb606.append("(");
      dfs606(root.left);
      sb606.append(")");
      if (root.right != null) {
        sb606.append("(");
        dfs606(root.right);
        sb606.append(")");
      }
    }
  }

  /**
   * Leetcode 655 : Print Binary Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(height * pow (2, height)) & O(height)
   * @param root TreeNode
   * @return list of list of string
   */
  public List<List<String>> printTree(TreeNode root) {
    int height = calDepth(root);
    int m = height + 1;
    int n = (1 << (height + 1)) - 1;
    List<List<String>> res = new ArrayList<List<String>>();
    for (int i = 0; i < m; i++) {
      List<String> row = new ArrayList<String>();
      for (int j = 0; j < n; j++) {
        row.add("");
      }
      res.add(row);
    }
    dfs655(res, root, 0, (n - 1) / 2, height);
    return res;
  }

  /**
   * return depth.
   * @param root TreeNode
   * @return depth
   */
  public int calDepth(TreeNode root) {
    int h = 0;
    if (root.left != null) {
      h = Math.max(h, calDepth(root.left) + 1);
    }
    if (root.right != null) {
      h = Math.max(h, calDepth(root.right) + 1);
    }
    return h;
  }

  /**
   * dfs method.
   * @param res list of list of string
   * @param root TreeNode
   * @param r int
   * @param c int
   * @param height int
   */
  public void dfs655(List<List<String>> res, TreeNode root, int r, int c, int height) {
    res.get(r).set(c, Integer.toString(root.val));
    if (root.left != null) {
      dfs655(res, root.left, r + 1, c - (1 << (height - r - 1)), height);
    }
    if (root.right != null) {
      dfs655(res, root.right, r + 1, c + (1 << (height - r - 1)), height);
    }
  }

  TreeSet<Integer> set = new TreeSet<>();

  /**
   * Leetcode 671 : Second Minimum Node In a Binary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param root TreeNode
   * @return int
   */
  public int findSecondMinimumValue(TreeNode root) {
    if (root == null) {
      return -1;
    }
    if (root.left == null && root.right == null) {
      return -1;
    }
    dfs671(root);
    if (set.size() < 2) {
      return -1;
    }
    set.pollFirst();
    return set.pollFirst();
  }

  /**
   * dfs method.
   * @param node TreeNode
   */
  public void dfs671(TreeNode node) {
    if (node == null) {
      return;
    }
    dfs671(node.left);
    dfs671(node.right);
    set.add(node.val);
  }
}
