
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

  /**
   * . Leetcode 526: Beautiful Arrangement
   *
   * @param n int
   * @return int
   * @Difficulty: Medium
   * @OptimalComplexity: O(n !) & O(n)
   */
  public int countArrangement(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    boolean[] visited = new boolean[n + 1];
    return dfs526(visited, 1, n);
  }

  /**
   * . DFS function find all the possibilities
   *
   * @param visited boolean[]
   * @param step    int
   * @param n       int
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

  /**
   * . Leetcode 529: Minesweeper
   *
   * @param board char[][]
   * @param click int[]
   * @return char[][]
   * @Difficulty: Medium
   * @OptimalComplexity: O(mn) & O(mn)
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

  /**
   * . DFS function
   *
   * @param board char[][]
   * @param x     int
   * @param y     int
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
      board[x][y] = (char) (count + '0');
    } else {
      board[x][y] = 'B';
      //if there is a mine surrounded.
      for (int i = 0; i < 8; i++) {
        int tx = x + dx[i];
        int ty = y + dy[i];
        if (tx >= 0 && tx < board.length && ty >= 0 && ty < board[0].length
            && board[tx][ty] == 'E') {
          dfs(board, tx, ty);
        }
      }
    }
  }


  private static final int M = 1000000007;

  /**
   * Leetcode 552: Student Attendance Record II.
   *
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   */

  public int checkRecord(int n) {
    int[][][] memo = new int[n + 1][3][2];
    return dfs552(n, 0, 0, 0, memo);
  }

  /**
   * memoization dfs.
   *
   * @param n      int
   * @param index  int
   * @param late   int
   * @param absent int
   * @param memo   int[][][]
   * @return int
   */

  public int dfs552(int n, int index, int late, int absent, int[][][] memo) {
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
      ans = (ans + dfs552(n, index + 1, late + 1, absent, memo)) % M;
    }
    memo[index][late][absent] = ans;
    return ans;
  }

  /**
   * Leetcode 559:  Maximum Depth of N-ary Tree.
   *
   * @param root Node
   * @return int
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(height) Recursion will use stack space, yet stack space is
   * determined by the depth of recursion.
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
   *
   * @param m           int
   * @param n           int
   * @param maxMove     int
   * @param startRow    int
   * @param startColumn int
   * @return int
   * @Difficulty: Medium
   * @OptimalComplexity: O(maxMove * m * n) & O(m*n)
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
   *
   * @param index       int
   * @param maxMove     int
   * @param m           int
   * @param n           int
   * @param startRow    int
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
    ans = (ans + dfs576(index + 1, maxMove, m, n, startRow + 1, startColumn)) % mod;
    ans = (ans + dfs576(index + 1, maxMove, m, n, startRow - 1, startColumn)) % mod;
    ans = (ans + dfs576(index + 1, maxMove, m, n, startRow, startColumn + 1)) % mod;
    ans = (ans + dfs576(index + 1, maxMove, m, n, startRow, startColumn - 1)) % mod;
    memo[startRow][startColumn][index] = ans;
    return ans;
  }

  List<Integer> res = new ArrayList<Integer>();

  /**
   * Leetcoee 589: N-ary Tree Preorder Traversal.
   *
   * @param root Node
   * @return list
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   */
  public List<Integer> preorder(Node root) {
    //?????????

    if (root == null)
      return res;
    res.add(root.val);
    for (Node child : root.children) {
      preorder(child);
    }

    return res;
  }


  List<Integer> list = new ArrayList<>();

  /**
   * Leetcode 590 : N-ary Tree Postorder Traversal.
   *
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   */
  public List<Integer> postorder(Node root) {
    dfs590(root);
    return list;
  }

  /**
   * dfs method.
   *
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
   *
   * @param n int
   * @return int
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   */
  public int findIntegers(int n) {
    max_num = n;
    dfs600(1);
    return res600;
  }

  /**
   * dfs method.
   *
   * @param n int
   */
  public void dfs600(int n) {
    if (n > max_num)
      return;
    res600++;
    if (n % 2 == 1) {
      dfs600(n << 1);
    } else {
      dfs600(n << 1);
      dfs600((n << 1) + 1);
    }
  }

  StringBuilder sb606 = new StringBuilder();

  /**
   * Leetcode 606 : Construct String from Binary Tree.
   *
   * @param root TreeNode
   * @return String
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   */
  public String tree2str606(TreeNode root) {
    dfs606(root);
    return sb606.toString();
  }

  /**
   * dfs method.
   *
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
   *
   * @param root TreeNode
   * @return list of list of string
   * @Difficulty: Medium
   * @OptimalComplexity: O(height * pow ( 2, height)) & O(height)
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
   *
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
   *
   * @param res    list of list of string
   * @param root   TreeNode
   * @param r      int
   * @param c      int
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
   *
   * @param root TreeNode
   * @return int
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
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
   *
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

  static class Employee {

    public int id;
    public int importance;
    public List<Integer> subordinates;
  }

  private int sum690 = 0;

  /**
   * Leetcode 690 : Employee Importance.
   *
   * @param employees list of employees
   * @param id        int
   * @return int
   */
  public int getImportance(List<Employee> employees, int id) {
    Map<Integer, Employee> map = new HashMap<>();
    for (Employee e : employees) {
      map.put(e.id, e);
    }
    return dfs690(id, map);
  }

  /**
   * dfs method. (using hashmap to optimize)
   *
   * @param id  int
   * @param map map
   * @return int
   */
  public int dfs690(int id, Map<Integer, Employee> map) {
    sum690 += map.get(id).importance;
    for (int a : map.get(id).subordinates) {
      dfs690(a, map);
    }
    return sum690;
  }

  int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

  /**
   * Leetcode 695 : Max Area of Island.
   *
   * @param grid int[][]
   * @return int
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1)
   */
  public int maxAreaOfIsland(int[][] grid) {
    int max = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        cnt = 0;
        if (grid[i][j] == 1) {
          dfs695(grid, i, j);
        }
        if (cnt > max) {
          max = cnt;
        }
      }
    }
    return max;
  }

  int cnt = 1;

  /**
   * dfs method.
   * @param grid int[][]
   * @param x int
   * @param y int
   */
  public void dfs695(int[][] grid, int x, int y) {
    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
      return;
    }
    grid[x][y] = 2;
    cnt++;
    for (int[] direction : directions) {
      int newX = direction[0] + x;
      int newY = direction[1] + y;
      if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length
          && grid[newX][newY] == 1) {
        dfs695(grid, newX, newY);
      }
    }
  }

  int ori;

  /**
   * Leetcode 733 : Flood Fill.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param image int[][]
   * @param sr int
   * @param sc int
   * @param color int
   * @return int[][]
   */
  public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    ori = image[sr][sc];
    boolean[][] visited = new boolean[image.length][image[0].length];
    dfs733(image, sr, sc, color, visited);
    return image;
  }

  /**
   * dfs method.
   * @param image int[][]
   * @param i int
   * @param j int
   * @param color int
   * @param visited boolean[][]
   */
  public void dfs733(int[][] image, int i, int j, int color, boolean[][] visited) {
    if ((i < 0 || i >= image.length || j < 0 || j >= image[0].length) || image[i][j] != ori || visited[i][j] == true) {
      return;
    }
    image[i][j] = color;
    visited[i][j] = true;
    dfs733(image, i + 1, j, color, visited);
    dfs733(image, i - 1, j, color, visited);
    dfs733(image, i, j + 1, color, visited);
    dfs733(image, i, j - 1, color, visited);
  }

  List<List<String>> ans = new ArrayList<>();
  LinkedList<String> path = new LinkedList<>();
  boolean[] used = new boolean[301];
  boolean find = false;

  /**
   * Leetcode 322 : Reconstruct Itinerary.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n2) TLE
   * @param tickets list of list of strings
   * @return list of string
   */
  public List<String> findItinerary(List<List<String>> tickets) {
    int n = tickets.size();
    String[][] t = new String[n][2];
    for (int i = 0; i < n; i++) {
      t[i][0] = tickets.get(i).get(0);
      t[i][1] = tickets.get(i).get(1);
    }
    Arrays.sort(t, (a , b) -> (a[1].compareTo(b[1])));
    path.offer("JFK");
    backtracking(t, "JFK");
    return ans.get(0);
  }

  /**
   * dfs method for 322.
   * @param t String[][]
   * @param now String
   */
  public void backtracking(String[][] t, String now) {
    if (find)
      return;
    if (path.size() == t.length + 1) {
      find = true;
      ans.add(new ArrayList<>(path));
      return;
    }
    for (int i = 0; i < t.length; i++) {
      if (t[i][0].equals(now) && !used[i]) {
        used[i] = true;
        path.offer(t[i][1]);
        backtracking(t, t[i][1]);
        used[i] = false;
        path.removeLast();
      }
    }
  }

  Map<String, List<String>> map = new HashMap<>();

  /**
   * Leetcode 756 ??? Pyramid Transition Matrix.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m * n * level) * O(m)
   * @param bottom String
   * @param allowed list of String
   * @return boolean
   */
  public boolean pyramidTransition(String bottom, List<String> allowed) {
    for (String str : allowed) {
      String key = str.substring(0, 2);
      if (!map.containsKey(key)) {
        map.put(key, new ArrayList<>());
      }
      map.get(key).add(String.valueOf(str.charAt(2)));
    }
    return dfs(bottom);
  }

  /**
   * Dfs method for leetcode 756.
   * @param bottom String
   * @return boolean
   */
  public boolean dfs(String bottom) {
    if (bottom.length() < 2) {
      return true;
    }
    List<String> res = new ArrayList<>();
    String first = bottom.substring(0, 2);
    if (!map.containsKey(first)) {
      return false;
    }
    res = map.get(first);
    for (int i = 1; i < bottom.length() - 1; i++) {
      String curr = bottom.substring(i, i + 2);
      if (!map.containsKey(curr)) {
        return false;
      }
      List<String> now = map.get(curr);
      List<String> newStr = new ArrayList<>();
      for (String re : res) {
        for (String s : now) {
          String t = re + s;
          newStr.add(t);
        }
      }
      res = newStr;
    }
    for (String str : res) {
      if (dfs(str)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Leetcode 764 : Largest Plus Sign.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n)
   * @param n int
   * @param mines int[][]
   * @return int
   */
  public int orderOfLargestPlusSign(int n, int[][] mines) {
    int[][] board = new int[n][n];
    for (int[] ints : board) {
      Arrays.fill(ints, 1);
    }
    for (int[] mine : mines) {
      board[mine[0]][mine[1]] = 0;
    }
    int ans = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int temp = dfs(board, i, j);
        ans = Math.max(temp, ans);
      }
    }
    return ans;
  }

  /**
   * dfs method for leetcode 764.
   * @param board int[][]
   * @param i int
   * @param j int
   * @return int
   */
  public int dfs(int[][] board, int i, int j) {
    int up = 0;
    int down = 0;
    int left = 0;
    int right = 0;
    int curi = i;
    int curj = j;
    while (curi >= 0 && board[curi][curj] == 1) {
      curi--;
      up++;
    }
    curi = i;
    curj = j;
    while (curi < board.length && board[curi][curj] == 1) {
      curi++;
      down++;
    }
    curi = i;
    curj = j;
    while (curj >= 0 && board[curi][curj] == 1) {
      curj--;
      left++;
    }
    curi = i;
    curj = j;
    while (curj < board.length && board[curi][curj] == 1) {
      curj++;
      right++;
    }
    return min(left, right, up, down);
  }

  /**
   * minimum number of the four numbers.
   * @param a int
   * @param b int
   * @param c int
   * @param d int
   * @return int
   */
  public int min(int a, int b, int c, int d) {
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    queue.offer(a);
    queue.offer(b);
    queue.offer(c);
    queue.offer(d);
    return queue.peek();
  }

  boolean flag = true;
  boolean[][] visited;

  /**
   * Leetcode 766 : Toeplitz Matrix.
   * @Difficulty: Easy
   * @OptimalComplexity: O(mn) & O(mn)
   * @param matrix int[][]
   * @return boolean
   */
  public boolean isToeplitzMatrix(int[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    visited = new boolean[n][m];
    for (int i = 0; i < n; i++) {
      flag = true;
      for (int j = 0; j < m; j++) {
        int val = matrix[i][j];
        if (!visited[i][j]) {
          dfs(matrix, i, j, val);
          if (!flag) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Dfs method for leetcode 766.
   * @param matrix int[][]
   * @param i int
   * @param j int
   * @param val int
   */
  public void dfs(int[][] matrix, int i, int j, int val) {
    if (i < 0 || i >= matrix.length || j < 0 || j > matrix[0].length) {
      return;
    }
    visited[i][j] = true;
    int temp = matrix[i][j];
    if (temp == val) {
      if ( i + 1 < matrix.length  && j + 1 < matrix[0].length) {
        dfs(matrix, i + 1, j + 1, val);
      } else {
      }
    } else {
      flag = false;
    }
  }

  /**
   * Leetcode 778 : Swim in Rising Water.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2logn) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int swimInWater(int[][] grid) {
    int low = grid[0][0];
    int high = 2500;
    while (low < high) {
      int mid = low + (high - low) / 2;
      if (dfs(grid, 0, 0, new boolean[grid.length][grid[0].length], mid)) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    return low;
  }

  /**
   * dfs method for leetcode 778.
   * @param grid int[][]
   * @param i int
   * @param j int
   * @param visited boolean[][]
   * @param tar int
   * @return boolean
   */
  public boolean dfs(int[][] grid, int i, int j, boolean[][] visited, int tar) {
    if ((i == grid.length - 1 && j == grid[0].length - 1)) {
      return true;
    }
    visited[i][j] = true;
    for (int[] dir : directions) {
      int x = dir[0] + i;
      int y = dir[1] + j;
      if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && !visited[x][y] && grid[x][y] <= tar) {
        if (dfs(grid, x, y, visited, tar)) {
          return true;
        }
      }

    }
    return false;
  }

  /**
   * Leetcode 1663 : Path With Minimum Effort.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2logn) & O(n2)
   * @param heights int[][]
   * @return int
   */
  public int minimumEffortPath(int[][] heights) {
    int low = 0;
    int high = 1000000;
    while (low < high) {
      int mid = low + (high - low) / 2;
      if (dfs1663(heights, 0, 0, new boolean[heights.length][heights[0].length], mid)) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }
    return low;
  }

  public boolean dfs1663(int[][] heights, int i, int j, boolean[][] v, int tar) {
    if (i == heights.length - 1 && j == heights[0].length - 1) {
      return true;
    }
    v[i][j] = true;
    for (int[] dir : directions) {
      int x = dir[0] + i;
      int y = dir[1] + j;
      if (x >= 0 && y >= 0 && x < heights.length && y < heights[0].length && !v[x][y] && Math.abs(heights[x][y] - heights[i][j]) <= tar) {
        if (dfs1663(heights, x, y, v, tar)) {
          return true;
        }
      }
    }
    return false;
  }

  List<String> list784 = new ArrayList<>();

  /**
   * Leetcode 784 : Letter Case Permutation.
   * @Difficulty: Medium
   * @OptimalComplexity: O(2^n * n) & O(2 ^ n)
   * @param s String
   * @return list of string
   */
  public List<String> letterCasePermutation(String s) {
    char[] store = s.toCharArray();
    dfs(0, store);
    return list784;
  }

  /**
   * Dfs method for leetcode 784.
   * @param idx int
   * @param store char[]
   */
  public void dfs(int idx, char[] store) {
    list784.add(String.valueOf(store));
    for (int i = idx; i < store.length; i++) {
      char c = store[i];
      if (Character.isDigit(c)) {
        continue;
      } else if (Character.isUpperCase(c)) {
        store[i] = Character.toLowerCase(c);
        dfs(i + 1, store);
        store[i] = c;
      } else {
        store[i] = Character.toUpperCase(c);
        dfs(i + 1, store);
        store[i] = c;
      }
    }
  }

  int[] col;

  /**
   * Leetcode 785 : Is Graph Bipartite?.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m * n) & O(n)
   * @param graph int[][]
   * @return boolean
   */
  public boolean isBipartite(int[][] graph) {
    boolean[] visited = new boolean[graph.length];
    col = new int[graph.length];
    for (int i = 0; i < graph.length; i++) {
      if (!visited[i]) {
        if (!dfs(graph, 0, i, visited)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * dfs method for leetcode 785.
   * @param graph int[][]
   * @param color int[]
   * @param idx int[]
   * @param visited boolean[]
   * @return boolean
   */
  public boolean dfs(int[][] graph, int color, int idx, boolean[] visited) {
    visited[idx] = true;
    col[idx] = color;
    for (int w : graph[idx]) {
      if (!visited[w]) {
        if (!dfs(graph, 1 - color, w, visited)) {
          return false;
        }
      } else if (col[w] == col[idx]) {
        return false;
      }
    }
    return true;
  }
}