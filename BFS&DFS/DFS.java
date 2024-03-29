
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
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
    //递归版

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
   * Leetcode 756 ： Pyramid Transition Matrix.
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

  List<List<Integer>> ans797 = new ArrayList<>();
  List<Integer> path797 = new ArrayList<>();

  /**
   * Leetcode 797 : All Paths From Source to Target.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * 2n) & O(n)
   * @param graph int[][]
   * @return list of list of integer
   */
  public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    path797.add(0);
    dfs(graph, 0);
    return ans797;
  }

  /**
   * dfs method  for leetcode 797.
   * @param graph int[][]
   * @param idx int
   */
  public void dfs(int[][] graph, int idx) {
    if (idx == graph.length - 1) {
      ans797.add(new ArrayList<>(path797));
      return;
    }
    for (int i = 0; i < graph[idx].length; i++) {
      path797.add(graph[idx][i]);
      dfs(graph, graph[idx][i]);
      path797.remove(path797.size() - 1);
    }
  }

  int min = 100001;

  /**
   * leetcode 801 : Minimum Swaps To Make Sequences Increasing.
   * @Difficulty: Hard
   * @OptimalComplexity: O(2n) & O(1) (time limit elapsed)
   * @param nums1 int[]
   * @param nums2 int[]
   * @return int
   */
  public int minSwap(int[] nums1, int[] nums2) {
    dfs(nums1, nums2, 0, 0);
    return min;
  }

  /**
   * swap func
   * @param array1 int[]
   * @param array2 int[]
   * @param i int
   */
  public void swap(int[] array1, int[] array2, int i) {
    int temp = array1[i];
    array1[i] = array2[i];
    array2[i] = temp;
  }

  /**
   * dfs method for leetcode 801.
   * @param nums1 int[]
   * @param nums2 int[]
   * @param idx int
   * @param count int
   */
  public void dfs(int[] nums1, int[] nums2, int idx, int count) {
    if (idx >= nums1.length) {
      min = Math.min(min, count);
      return;
    }
    if (count >= min) {
      return;
    }
    if (idx > 1 && (nums1[idx - 2] >= nums1[idx - 1] || nums2[idx - 2] >= nums2[idx - 1])) {
      return;
    }
    if (idx >= 1 && (nums1[idx - 1] >= nums1[idx] || nums2[idx - 1] >= nums2[idx])) {
      //must swap
      swap(nums1, nums2, idx);
      dfs(nums1, nums2, idx + 1, count + 1);
      swap(nums1, nums2, idx);
    } else {
      //no swap
      dfs(nums1, nums2, idx + 1, count);
      //swap
      swap(nums1, nums2, idx);
      dfs(nums1, nums2, idx + 1, count + 1);
      //trace back
      swap(nums1, nums2, idx);
    }
  }

  /**
   * Leetcode 802 : Find Eventual Safe States.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n + m) & O(n)
   * @param graph int[][]
   * @return list of integer
   */
  public List<Integer> eventualSafeNodes(int[][] graph) {
    List<Integer> list = new ArrayList<>();
    int[] color = new int[graph.length];
    for (int i = 0; i < graph.length; i++) {
      if (safe(graph, color, i)) {
        list.add(i);
      }
    }
    Collections.sort(list);
    return list;
  }

  /**
   * Marker, see if there exists a circle.
   * @param graph int[][]
   * @param color int[]
   * @param idx int
   * @return boolean
   */
  public boolean safe(int[][] graph, int[] color, int idx) {
    if (color[idx] > 0) {
      return color[idx] == 2;
    }
    color[idx] = 1;
    for (int i : graph[idx]) {
      if (!safe(graph, color, i)) {
        return false;
      }
    }
    color[idx] = 2;
    return true;
  }

  double p = 0;
  double co = 1.0;
  Map<String, Double> memo808 = new HashMap<>();

  /**
   * leetcode 808 : Soup Servings.
   * @param n int n
   * @return double
   */
  public double soupServings(int n) {
    if (n > 5000) {
      return 1;
    }
    return dfs(n, n);
  }

  /**
   * leetcode 808 : Soup Servings.
   * @Difficulty: Medium
   * @OptimalComplexity: O(4^m) & O(m)
   * @param currA int
   * @param currB int
   * @return double
   */
  public double dfs(int currA, int currB) {

    if (currA > 0 && currB <= 0) {
      return 0;
    }
    if (currA <= 0 && currB > 0) {
      return 1;
    }
    if (currA <= 0 && currB <= 0) {
      return 0.5;
    }
    String encode = String.valueOf(currA) + " " + String.valueOf(currB);
    if (memo808.containsKey(encode)) {
      return memo808.get(encode);
    }
    double p = 0;

    p += dfs(currA - 100, currB);
    p += dfs(currA - 75, currB - 25);
    p += dfs(currA - 50, currB - 50);
    p += dfs(currA - 25, currB - 75);

    p *= 0.25;

    memo808.put(encode, p);
    return p;
  }

  /**
   * Leetcode 813 : Largest Sum of Averages.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2 * m) & O(n2)
   * @param nums int[]
   * @param k int
   * @return double
   */
  public double largestSumOfAverages(int[] nums, int k) {
    int n = nums.length;
    double[][] dp = new double[n + 1][k + 1];
    int[] prefix = new int[nums.length + 1];
    for (int i = 1; i <= nums.length; i++) {
      prefix[i] = nums[i - 1] + prefix[i - 1];
    }
    return dfs(nums, 0, k, dp, prefix);
  }

  /**
   * dfs for leetcode 813.
   * @param nums int[]
   * @param idx int
   * @param k int
   * @param dp double
   * @param prefix int
   * @return double
   */
  public double dfs(int[] nums, int idx, int k, double[][] dp, int[] prefix) {
    if (idx == nums.length) {
      return 0;
    }
    if (k == 1) {
      return (prefix[nums.length] - prefix[idx]) * 1.0 / (nums.length - idx);
    }
    //memoization
    if (dp[idx][k] != 0) {
      return dp[idx][k];
    }
    double sum = 0;
    for (int j = idx; j < nums.length; j++) {
      sum = Math.max(sum, (prefix[j + 1] - prefix[idx]) * 1.0 / (j - idx + 1) + dfs(nums, j + 1, k - 1, dp, prefix));
    }
    dp[idx][k] = sum;
    return sum;
  }

  /**
   * Leetcode 818 : Race Car.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param target int
   * @return int
   */
  public int racecar(int target) {
    return dfs(target, new HashMap<>());
  }

  /**
   * dfs method for leetcode 818.
   * @param target int
   * @param map map
   * @return int
   */
  public int dfs(int target, Map<Integer, Integer> map) {
    if (0 == target) {
      return 0;
    }
    if (map.containsKey(target)) {
      return map.get(target);
    }
    int speed = 1;
    int cnt  = 0;
    int currPos = 0;
    while (currPos + speed < target) {
      currPos += speed;
      speed *= 2;
      cnt++;
    }
    if (target == currPos + speed) {
      map.put(target, cnt + 1);
      return cnt + 1;
    }
    int min = dfs(currPos + speed - target, map) + 2;
    int back = 0;
    for (int i = cnt; i > 0; i--) {
      min = Math.min(min, dfs(target - (currPos - back), map) + 2 + cnt - i);
      back += 1 << (cnt - i);
    }

    cnt += min;
    map.put(target, cnt);

    return cnt;

  }

  Map<Integer, Integer> map823;
  Map<Integer, Long> memo823 = new HashMap<>();

  /**
   * Leetcode 823 : Binary Trees With Factors。
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlongn) & O(n)
   * @param arr int[]
   * @return int
   */
  public int numFactoredBinaryTrees(int[] arr) {
    map = new HashMap<>();
    Arrays.sort(arr);
    for (int i = 0; i < arr.length; i++) {
      map823.put(arr[i], i);
    }
    long res = 0;
    for (int i = arr.length - 1; i >= 0; i--) {
      res = res + dfs(arr, i);
    }
    return (int)(res % 1000000007);
  }

  /**
   * dfs method for leetcode 823.
   * @param arr int[]
   * @param idx int
   * @return long
   */
  public long dfs(int[] arr, int idx) {
    if (memo823.containsKey(idx)) {
      return memo823.get(idx);
    }
    long ans = 1;
    for (int i = idx; i >= 0; i--) {
      if (arr[idx] % arr[i] == 0 && map823.containsKey(arr[idx] / arr[i])) {
        ans = (ans + dfs(arr, i) * dfs(arr, map823.get(arr[idx] / arr[i])));
      }
    }

    memo823.put(idx, ans);

    return ans;
  }

  int count;

  /**
   * Leetcode 827 : Making A Large Island.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int largestIsland(int[][] grid) {
    int mark = 2;
    boolean isGrid = true;
    boolean[][] visited = new boolean[grid.length][grid.length];
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == 0) {
          isGrid = false;
          continue;
        }
        if (!visited[i][j] && grid[i][j] == 1) {
          count = 0;
          dfs(grid, visited, i, j, mark);
          map.put(mark, count);
          mark++;
        }
      }
    }
    if (isGrid) {
      return grid.length * grid.length;
    }

    int ans = 0;
    Set<Integer> set;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        int cnt = 1;
        set = new HashSet<>();
        if (grid[i][j] == 0) {
          for (int[] direction : directions) {
            int nx = i + direction[0];
            int ny = j + direction[1];
            if (nx < 0 || ny < 0 || nx >= grid.length || ny >= grid.length || set.contains(grid[nx][ny])) {
              continue;
            }
            cnt += map.getOrDefault(grid[nx][ny], 0);
            set.add(grid[nx][ny]);
          }
        }
        ans = Math.max(ans, cnt);
      }
    }
    return ans;
  }

  /**
   * dfs method for leetcode 827.
   * @param grid int[][]
   * @param visited boolean[][]
   * @param i int
   * @param j int
   * @param mark int
   */
  public void dfs(int[][] grid, boolean[][] visited, int i, int j, int mark) {
    if (visited[i][j] || grid[i][j] == 0) {
      return;
    }
    count++;
    grid[i][j] = mark;
    visited[i][j] = true;
    for (int[] direction : directions) {
      int x = i + direction[0];
      int y = j + direction[1];
      if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
        dfs(grid, visited, x, y, mark);
      }
    }
  }


  private List<List<Integer>> graph = new ArrayList<>();
  int[] distSum;
  int[] nodeNum;

  /**
   * Leetcode 834 : Sum of Distances in Tree.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param N int
   * @param edges int[][]
   * @return int[]
   */
  public int[] sumOfDistancesInTree(int N, int[][] edges) {
    for (int i = 0; i < N; i++) {
      graph.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
      int src = edge[0];
      int tar = edge[1];
      graph.get(src).add(tar);
      graph.get(tar).add(src);
    }
    distSum = new int[N];
    nodeNum = new int[N];
    Arrays.fill(nodeNum, 1);
    postOrder(0, -1);
    preOrder(0, -1);
    return distSum;
  }

  //distSum within the child tree
  private void postOrder(int root, int parent) {
    List<Integer> neighbors = graph.get(root);
    for (int neighbor : neighbors) {
      if (neighbor == parent) {
        continue;
      }
      postOrder(neighbor, root);
      nodeNum[root] += nodeNum[neighbor];
      distSum[root] += nodeNum[neighbor] + distSum[neighbor];
    }
  }

  //distSum outside of the child tree
  private void preOrder(int root, int parent) {
    List<Integer> neighbors = graph.get(root);
    for (int neighbor : neighbors) {
      if (neighbor == parent) {
        continue;
      }
      distSum[neighbor] = distSum[root] - nodeNum[neighbor] + (graph.size() - nodeNum[neighbor]);
      preOrder(neighbor, root);
    }
  }

  /**
   * Leetcode 841 : Split Array into Fibonacci Sequence
   * @param num String
   * @return list of integer.
   */
  public List<Integer> splitIntoFibonacci(String num) {
    List<Integer> ans = new ArrayList<>();
    dfs(num, ans, 0);
    return ans;
  }

  /**
   * dfs method for leetcode 841/
   * @param str string
   * @param ans list of integer
   * @param idx int
   * @return boolean
   */
  public boolean dfs(String str, List<Integer> ans, int idx) {
    if (idx == str.length()) {
      return ans.size() >= 3;
    }
    for (int i = idx + 1; i <= str.length(); i++) {
      String curr = str.substring(idx, i);
      if (str.charAt(idx) == '0' && i > idx + 1 || curr.length() > 10 || Long.valueOf(curr) > Integer.MAX_VALUE) {
        break;
      }
      int val = Integer.valueOf(curr);
      int prevOne = ans.size() >= 2 ? ans.get(ans.size() - 1) : -1;
      int prevTwo = ans.size() >= 2 ? ans.get(ans.size() - 2) : -1;
      ans.add(val);
      if ((prevOne == -1 || prevTwo == -1 || prevOne + prevTwo == val) && dfs(str, ans, i)) {
        return true;
      }
      ans.remove(ans.size() - 1);
    }
    return false;
  }

  int min854 = Integer.MAX_VALUE;

  /**
   * Leetcode 854 : K-Similar Strings.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(1)
   * @param s1 string
   * @param s2 string
   * @return
   */
  public int kSimilarity(String s1, String s2) {
    dfs(s1.toCharArray(), s2.toCharArray(), 0, 0);
    return min854;

  }

  public void dfs(char[] crr1, char[] crr2, int curr, int k) {
    if (curr == crr1.length) {
      min854 = Math.min(min854, k);
      return;
    }
    if (k >= min) {
      return;
    }
    if (crr1[curr] != crr2[curr]) {
      for (int i = curr + 1; i < crr1.length; i++) {
        if (crr1[curr] == crr2[i]) {
          swap(crr2, curr, i);
          dfs(crr1, crr2, curr + 1, k + 1);
          swap(crr2, curr, i);
        }
      }
    } else {
      dfs(crr1, crr2, curr + 1, k);
    }
  }

  public void swap(char[] crr, int a, int b) {
    char temp = crr[a];
    crr[a] = crr[b];
    crr[b] = temp;
  }

  Map<TreeNode, TreeNode> map863 = new HashMap<>();
  List<Integer> ans863 = new ArrayList<>();

  /**
   * Leetcode 863 : All Nodes Distance K in Binary Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root treenode
   * @param target treenode
   * @param k int
   * @return list of integer
   */
  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    findParent(root, null);
    findAns(target, null, 0, k);
    return ans863;
  }

  public void findParent(TreeNode root, TreeNode parent) {
    if (root == null) {
      return;
    }
    map863.put(root, parent);
    findParent(root.left, root);
    findParent(root.right, root);
  }

  public void findAns(TreeNode root, TreeNode from, int distance, int k) {
    if (root == null) {
      return;
    }
    if (distance == k) {
      ans863.add(root.val);
      return;
    }
    if (root.left != from) {
      findAns(root.left, root, distance + 1, k);
    }
    if (root.right != from) {
      findAns(root.right, root, distance + 1, k);
    }
    if (map863.get(root) != from) {
      findAns(map863.get(root), root, distance + 1, k);
    }
  }

  boolean[] visited869;

  /**
   * Leetcode 869 : Reordered Power of 2.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m!) & O(m)
   * @param n int
   * @return boolean
   */
  public boolean reorderedPowerOf2(int n) {
    String str = String.valueOf(n);
    char[] crr = str.toCharArray();
    Arrays.sort(crr);
    visited869 = new boolean[crr.length];
    return dfs(crr, 0, 0);
  }



  public boolean dfs(char[] crr, int idx, int num) {
    if (idx == crr.length) {
      return (num & (num - 1)) == 0;
    }
    for (int i = 0; i < crr.length; i++) {
      if ((num == 0 && crr[i] == '0') || visited869[i] || (i > 0 && !visited869[i - 1] && crr[i] == crr[i - 1])) {
        continue;
      }
      visited869[i] = true;
      if (dfs(crr, idx + 1, num * 10 + crr[i] - '0')) {
        return true;
      }
      visited869[i] = false;
    }
    return false;
  }

  List<Integer> list1 = new ArrayList<>();
  List<Integer> list2 = new ArrayList<>();

  /**
   * Leetcode 872 : Leaf-Similar Trees.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root1 treenode
   * @param root2 treenode
   * @return boolean
   */
  public boolean leafSimilar(TreeNode root1, TreeNode root2) {
    dfs(root1, list1);
    dfs(root2, list2);
    if (list1.size() != list2.size()) {
      return false;
    }
    for (int i = 0; i < list1.size(); i++) {
      if (!list1.get(i).equals(list2.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * dfs method for leetcode 872.
   * @param node treenode
   * @param list list of integer
   */
  public void dfs(TreeNode node, List<Integer> list) {
    if (node == null) {
      return;
    }
    if (node.left == null && node.right == null) {
      list.add(node.val);
    }
    dfs(node.left, list);
    dfs(node.right, list);
  }

  int min931 = Integer.MAX_VALUE;
  int[][] memo931;

  /**
   * Leetcode 931 : Minimum Falling Path Sum.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(n2)
   * @param matrix int[][]
   * @return int
   */
  public int minFallingPathSum(int[][] matrix) {
    memo931 = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix[0].length; i++) {
      min931 = Math.min(min931, dfs931(matrix, 0, i));
    }
    return min931;
  }

  public int dfs931(int[][] matrix, int i, int j) {
    if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) {
      return Integer.MAX_VALUE;
    }
    if (i == matrix.length - 1) {
      return matrix[i][j];
    }
    if (memo931[i][j] != 0) {
      return memo931[i][j];
    }
    int sum = matrix[i][j];
    int toAdd = Math.min(dfs931(matrix, i + 1, j - 1), Math.min(dfs931(matrix, i + 1, j), dfs931(matrix, i + 1, j + 1)));

    sum += toAdd;
    memo931[i][j] = sum;
    return sum;

  }

  boolean[][] vis;
  List<int[]> listone = new ArrayList<>();
  List<int[]> listtwo = new ArrayList<>();

  /**
   * leetcode 934 : Shortest Bridge.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int shortestBridge(int[][] grid) {
    vis = new boolean[grid.length][grid.length];
    int cx = 0;
    int cy = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] == 1) {
          cx = i;
          cy = j;
          break;
        }
      }
    }
    dfsOne(grid, cx, cy);
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] == 1 && !vis[i][j]) {
          dfsTwo(grid, i, j);
          break;
        }
      }
    }
    int min = Integer.MAX_VALUE;
    for (int[] i : listone) {
      for (int[] j : listtwo) {
        min = Math.min(dis(i, j) - 1, min);
      }
    }
    return min;
  }

  public int dis(int[] a, int[] b) {
    return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
  }

  public void dfsOne(int[][] grid, int i, int j) {
    vis[i][j] = true;
    listone.add(new int[]{i, j});
    for (int[] dir : directions) {
      int x = i + dir[0];
      int y = j + dir[1];
      if (x >= 0 && x < grid.length && y >= 0 && y < grid.length && grid[x][y] == 1 && !vis[x][y]) {
        dfsOne(grid, x, y);
      }
    }
  }

  public void dfsTwo(int[][] grid, int i, int j) {
    vis[i][j] = true;
    listtwo.add(new int[]{i, j});
    for (int[] dir : directions) {
      int x = i + dir[0];
      int y = j + dir[1];
      if (x >= 0 && x < grid.length && y >= 0 && y < grid.length && grid[x][y] == 1 && !vis[x][y]) {
        dfsTwo(grid, x, y);
      }
    }
  }

  /**
   * Leetcode 938 : Range Sum of BST.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param root tree node
   * @param low int
   * @param high int
   * @return int
   */
  public int rangeSumBST(TreeNode root, int low, int high) {
    if (root == null) {
      return 0;
    }
    int sum = 0;
    if (root.val >= low && root.val <= high) {
      sum += root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }
    if (root.val < low) {
      sum += rangeSumBST(root.right, low, high);
    }
    if (root.val > high) {
      sum += rangeSumBST(root.left, low, high);
    }
    return sum;
  }

  /**
   * Leetcode 949 : Largest Time for Given Digits.
   * @Difficulty: Medium
   * @OptimalComplexity: O(4!) & O(n)
   * @param arr int[]
   * @return string
   */
  public String largestTimeFromDigits(int[] arr) {
    Arrays.sort(arr);
    if (arr[0] > 2) {
      return "";
    }
    if (arr[0] == 2 && arr[1] > 4) {
      return "";
    }
    if (arr[3] == 0) {
      return "00:00";
    }
    vis949 = new boolean[4];
    dfs949(arr, vis949, 0, "");
    return max.equals("0000") ? "" : max.substring(0, 2) + ":" + max.substring(2, 4);
  }
  String max = "0000";
  boolean[] vis949;

  /**
   * dfs method for leetcode 949.
   * @param arr int[]
   * @param vis boolean[]
   * @param cnt  int
   * @param curr string
   */
  public void dfs949(int[] arr, boolean[] vis, int cnt, String curr) {
    if (cnt == 4) {
      if (Integer.parseInt(curr) < 2400 && Integer.parseInt(curr.substring(2, 4)) < 60) {
        if (Integer.parseInt(curr) > Integer.parseInt(max)) {
          max = curr;
        }
      }
    }
    for (int i = 0; i < arr.length; i++) {
      if (!vis[i]) {
        vis[i] = true;
        dfs949(arr, vis, cnt + 1, curr + arr[i]);
        vis[i] = false;
      }
    }
  }

  Map<Long, Long> map964 = new HashMap<>();

  /**
   * Leetcode 964 : Least Operators to Express Number.
   * @Difficulty: Hard
   * @OptimalComplexity: O(log(target)) & O(log(target))
   * @param x int
   * @param target int
   * @return int
   */
  public int leastOpsExpressTarget(int x, int target) {
    return x == target ? 0 : (int)dfs(x, target);
  }

  public long dfs(long i, long target) {
    if (map964.containsKey(target)) {
      return map964.get(target);
    }
    if (i == target) {
      return 0;
    } else if (i > target) {
      return Math.min(2 * target - 1, 2 * (i - target));
    } else {
      int p = 0;
      long xp = i;
      while (xp < target) {
        xp *= i;
        ++p;
      }
      if (target == xp) {
        return p;
      }
      if (xp - target >= target) {
        return p - 1 + 1 + dfs(i, target - xp / i);
      } else {
        map964.put(target, Math.min(p - 1 + dfs(i, target - xp / i), p + dfs(i, xp - target)) + 1);
        return map964.get(target);
      }
    }
  }

  List<Integer> list967 = new ArrayList<>();
  Set<Integer> set967 = new HashSet<>();

  /**
   * Leetcode 967 : Numbers With Same Consecutive Differences.
   * @param n int
   * @param k int
   * @return int[]
   */
  public int[] numsSameConsecDiff(int n, int k) {
    for (int j = 0; j <= 9; j++) {
      dfs967(n, k, j, 1, 0, 0);
    }
    int[] ans = new int[list967.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = list967.get(i);
    }
    return ans;
  }

  public void dfs967(int n, int k, int idx, int count, int len, int curr) {
    if (len == n) {
      if (!set967.contains(curr) && String.valueOf(curr).length() == n) {
        set967.add(curr);
        list967.add(curr);
      }
      return;
    }
    int up = idx + k;
    int down = idx - k;
    if (up <= 9) {
      dfs967(n, k, up, count * 10, len + 1, curr + count * up);
    }
    if (down >= 0) {
      dfs967(n, k, down, count * 10, len + 1, curr + count * down);
    }
  }

  int ans968 = 0;
  public int minCameraCover(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (dfs(root) == 2) {
      ans968++;
    }
    return ans968;
  }

  public int dfs(TreeNode node) {
    if (node == null) {
      return 1;
    }
    int left = dfs(node.left);
    int right = dfs(node.right);
    if (left == 2 || right == 2) {
      ans968++;
      return 0;
    } else if (left == 0 || right == 0) {
      return 1;
    } else {
      return 2;
    }
  }

  int i = 0;
  boolean flag971 = false;
  List<Integer> ans971 = new ArrayList<>();
  public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
    if (root == null) {
      return ans971;
    }
    dfs971(root, voyage);
    if (flag971) {
      List<Integer> spe = new ArrayList<>();
      spe.add(-1);
      return spe;
    }
    return ans971;
  }

  public void dfs971(TreeNode root, int[] voyage) {
    if (root == null) {
      return;
    }
    if (root.val != voyage[i]) {
      flag971 = true;
      return;
    }
    if (root.left != null && root.left.val != voyage[i + 1]) {
      flip(root);
      ans971.add(root.val);
    }
    i++;
    dfs971(root.left, voyage);
    dfs971(root.right, voyage);
  }

  public void flip(TreeNode root) {
    if (root == null) {
      return;
    }
    TreeNode temp = null;
    temp = root.left;
    root.left = root.right;
    root.right = temp;
  }

  int ans979 = 0;

  /**
   * Leetcode 979 : Distribute Coins in Binary Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(N) & O(H)
   * @param root treenode
   * @return int
   */
  public int distributeCoins(TreeNode root) {
    if (root == null) {
      return 0;
    }
    lrd(root);
    return ans979;

  }

  public int lrd(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (root.left != null) {
      root.val += lrd(root.left);
    }
    if (root.right != null) {
      root.val += lrd(root.right);
    }
    ans979 += Math.abs(root.val - 1);
    return root.val - 1;
  }

  boolean[][] vis980;
  int ans980 = 0;

  /**
   * Leetcode 980 : Unique Paths III
   * @Difficulty: Hard
   * @OptimalComplexity: O(4^(R+C)) & (R*C)
   * @param grid int[][]
   * @return int
   */
  public int uniquePathsIII(int[][] grid) {
    vis = new boolean[grid.length][grid[0].length];
    int sx = 0;
    int sy = 0;
    int tot = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == 1) {
          sx = i;
          sy = j;
          break;
        }
        if (grid[i][j] == 0) {
          tot++;
        }
      }
    }
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == -1) {
          vis980[i][j] = true;
        }
      }
    }
    vis980[sx][sy] = true;
    dfs(grid, vis980, sx, sy);
    return ans980;
  }

  public void dfs(int[][] grid, boolean[][] vis, int i, int j) {
    if (grid[i][j] == 2) {
      if (allvis(vis)) {
        ans980++;
      }
      return;
    }
    for (int[] direction : directions) {
      int x = i + direction[0];
      int y = j + direction[1];
      if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && !vis980[x][y] && grid[x][y] != -1) {
        vis980[x][y] = true;
        dfs(grid, vis, x, y);
        vis980[x][y] = false;
      }
    }
  }

  public boolean allvis(boolean[][] vis) {
    for (boolean[] v : vis) {
      for (boolean b : v) {
        if (b == false) {
          return false;
        }
      }
    }
    return true;
  }

  StringBuilder sb988;
  String res988;

  /**
   * Leetcode 988 : Smallest String Starting From Leaf.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root treenode
   * @return string
   */
  public String smallestFromLeaf(TreeNode root) {
    if (root == null) {
      return "";
    }
    sb988 = new StringBuilder();
    dfs988(root);
    return res988;
  }

  public void dfs988(TreeNode root) {
    if (root == null) {
      return;
    }
    sb988.insert(0, (char) ('a' + root.val));
    if (root.left == null && root.right == null) {
      String toCom = sb988.toString();
      if (res988 == null || toCom.compareTo(res988) < 0) {
        res988 = toCom;
      }
    }
    dfs988(root.left);
    dfs988(root.right);
    sb988.deleteCharAt(0);
  }

  TreeMap<Integer, List<int[]>> map987;

  /**
   * Leetcode 987 : Vertical Order Traversal of a Binary Tree.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param root treenode
   * @return list of list of integer
   */
  public List<List<Integer>> verticalTraversal(TreeNode root) {
    map987 = new TreeMap<>();
    dfs987(root, 0, 0);
    List<List<Integer>> ans = new ArrayList<>();
    for (int key : map987.keySet()) {
      List<int[]> temp = map987.get(key);
      temp.sort(new Comparator<int[]>() {
        @Override
        public int compare(int[] a, int[] b) {
          if (a[0] == b[0]) {
            return a[1] - b[1];
          }
          return a[0] - b[0];
        }
      });
      List<Integer> toAdd = new ArrayList<>();
      for (int[] arr : temp) {
        toAdd.add(arr[1]);
      }
      ans.add(toAdd);
    }
    return ans;
  }

  public void dfs987(TreeNode node, int idx, int level) {
    if (node == null) {
      return;
    }
    if (!map987.containsKey(idx)) {
      map987.put(idx, new ArrayList<>());
    }
    map987.get(idx).add(new int[]{level, node.val});
    dfs987(node.left, idx - 1, level + 1);
    dfs987(node.right, idx + 1, level + 1);
  }

  Map<Integer, int[]> map993 = new HashMap<>();

  /**
   * Leetcide 993 : Cousins in Binary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root treenode
   * @param x int
   * @param y int
   * @return boolean
   */
  public boolean isCousins(TreeNode root, int x, int y) {
    if (root == null) {
      return true;
    }
    dfs(root, 0, new TreeNode(-1));
    int[] xd = map993.get(x);
    int[] yd = map993.get(y);
    return xd[0] == yd[0] && xd[1] != yd[1];
  }

  public void dfs(TreeNode node, int level, TreeNode parent) {
    if (node == null) {
      return;
    }
    map993.put(node.val, new int[]{level, parent.val});
    dfs(node.left, level + 1, node);
    dfs(node.right, level + 1, node);
  }

  int ans996 = 0;

  /**
   * Leetcode 996 : Number of Squareful Arrays.
   * @Difficulty: Hard
   * @OptimalComplexity: O(N!) & O(N)
   * @param nums int[]
   * @return int
   */
  public int numSquarefulPerms(int[] nums) {
    boolean[] vis = new boolean[nums.length];
    Arrays.sort(nums);
    dfs(nums, 0, vis, -1);
    return ans996;
  }

  public void dfs(int[] nums, int curridx, boolean[] vis, int pre) {
    if (curridx == nums.length) {
      ans996++;
      return;
    }
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
      if (set.contains(nums[i])) {
        continue;
      }
      if (!vis[i]) {
        vis[i] = true;
        set.add(nums[i]);
        if (pre != -1 && check(pre, nums[i])) {
          dfs(nums, curridx + 1, vis, nums[i]);
        } else if (pre == -1) {
          dfs(nums, curridx + 1, vis, nums[i]);
        }
        vis[i] = false;
      }
    }

  }

  public boolean check(int x,int y) {
    int t = (int) Math.sqrt(x + y);
    return t * t == x + y;
  }
}