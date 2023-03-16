import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

  List<Double> list637 = new ArrayList<>();

  /**
   * Leetcode 637 : Average of Levels in Binary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return list of doubles
   */
  public List<Double> averageOfLevels(TreeNode root) {
    if (root == null) {
      return list637;
    }
    if (root.left == null && root.right == null) {
      list637.add(root.val * 1.0);
      return list637;
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      int temp = size;
      double sum = 0;
      while (temp > 0) {
        TreeNode node = queue.poll();
        sum += node.val;
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
        temp--;
      }
      list637.add(sum / size);
    }
    return list637;
  }

  private int maxW = 1;

  /**
   * Leetcode 662 : Maximum Width of Binary Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return int
   */
  public int widthOfBinaryTree(TreeNode root) {
    if (root == null) {
      return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    LinkedList<Integer> list = new LinkedList<>();
    list.add(1);
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        TreeNode node = queue.poll();
        int index = list.removeFirst();
        assert node != null;
        if (node.left != null) {
          queue.offer(node.left);
          list.add(2 * index);
        }
        if (node.right != null) {
          queue.offer(node.right);
          list.add(2 * index + 1);
        }
      }
      if (list.size() >= 2) {
        maxW = Math.max(maxW, list.getLast() - list.getFirst() + 1);
      }
    }
    return maxW;
  }

  /**
   * Leetcode 675 : Cut Off Trees for Golf Event.
   * @param forest list of list of integer
   * @return int
   */
  public int cutOffTree(List<List<Integer>> forest) {
    List<int[]> list = new ArrayList<>();
    for (int i = 0; i < forest.size(); i++) {
      for (int j = 0; j < forest.get(0).size(); j++) {
        if (forest.get(i).get(j) > 1) {
          list.add(new int[]{i, j});
        }
      }
    }
    list.sort(Comparator.comparingInt(a -> forest.get(a[0]).get(a[1])));
    int cx = 0;
    int cy = 0;
    int ans = 0;
    for (int[] ints : list) {
      int step = bfs(forest, cx, cy, ints[0], ints[1]);
      if (step == -1) {
        return -1;
      } else {
        ans += step;
        cx = ints[0];
        cy = ints[1];
      }
    }
    return ans;
  }

  /**
   * bfs method.
   * @param forest list
   * @param cx current x
   * @param cy current y
   * @param tx target x
   * @param ty target y
   * @return int
   */
  public int bfs(List<List<Integer>> forest, int cx, int cy, int tx, int ty) {
    if (cx == tx && cy == ty) {
      return 0;
    }
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{cx, cy});
    int row = forest.size();
    int col = forest.get(0).size();
    boolean[][] visited = new boolean[row][col];
    int step = 0;
    int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    while (!queue.isEmpty()) {
      step++;
      int size = queue.size();
      while (size-- > 0) {
        int[] temp = queue.poll();
        for (int[] dir : directions) {
          assert temp != null;
          int newR = temp[0] + dir[0];
          int newC = temp[1] + dir[1];
          if (newR >= 0 && newR < row && newC >= 0 && newC < col) {
            if (!visited[newR][newC] && forest.get(newR).get(newC) > 0) {
              if (newR == tx && newC == ty) {
                return step;
              } else {
                queue.offer(new int[]{newR, newC});
                visited[newR][newC] = true;
              }
            }
          }
        }
      }
    }
    return -1;
  }

  /**
   * Leetcode 691 : Stickers to Spell Word.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param stickers list of string
   * @param target string
   * @return int
   */
  public int minStickers(String[] stickers, String target) {
    int n = target.length();
    int[] dp = new int[1 << n];
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(0);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        int base = queue.poll();
        for (String sticker : stickers) {
          int state = base;
          System.out.println(base);
          int[] chr = new int[26];
          for (char c : sticker.toCharArray()) {
            chr[c - 'a']++;
          }
          for (int i = 0; i < target.length(); i++) {
            char temp = target.charAt(i);
            if (chr[temp - 'a'] != 0 && (state >> i) % 2 == 0) {
              chr[temp - 'a']--;
              state |= 1 << i;//填充
            }
          }
          if (dp[state] != 0 || state == 0) continue;
          queue.offer(state);
          dp[state] = dp[base] + 1;
          if (state == (1 << n) - 1) return dp[state];
        }
      }
    }
    return -1;
  }

  /**
   * Leetcode 749 : Contain Virus.
   * @Difficulty: Hard
   * @OptimalComplexity: O(mn(m+n)) & O(mn)
   * @param isInfected int[][]
   * @return int
   */
  public int containVirus(int[][] isInfected) {
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int m = isInfected.length;
    int n = isInfected[0].length;
    int res = 0;
    List<Set<Integer>> neighbors;
    do {
      neighbors = new ArrayList<>();
      int maxFireWallCount = 0;
      int maxInfectIndex = -1;
      int maxNeighborSize = 0;
      //BFS for every element in the matrix
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (isInfected[i][j] == 1) {
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{i, j});
            Set<Integer> neighbor = new HashSet<>();
            int index = neighbors.size() + 1;
            int firewall = 0;
            isInfected[i][j] = -index;
            while (!queue.isEmpty()) {
              int[] point = queue.poll();
              for (int[] dir : dirs) {
                int nx = point[0] + dir[0];
                int ny = point[1] + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                  if (isInfected[nx][ny] == 1) {
                    queue.offer(new int[]{nx, ny});
                    isInfected[nx][ny] = -index;
                  } else if (isInfected[nx][ny] == 0) {
                    firewall++;
                    neighbor.add((nx << 16) ^ ny);
                  }
                }
              }
            }
            neighbors.add(neighbor);

            if (neighbor.size() > maxNeighborSize) {
              maxFireWallCount = firewall;
              maxInfectIndex = index;
              maxNeighborSize = neighbor.size();
            }
          }
        }
      }

      if (neighbors.isEmpty()) {
        break;
      }
      res += maxFireWallCount;
      maxInfectIndex--;
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (isInfected[i][j] < 0) {
            if (isInfected[i][j] != -(maxInfectIndex + 1)) {
              isInfected[i][j] = 1;
            } else {
              isInfected[i][j] = 2;
            }
          }
        }
      }
      int neighborsSize = neighbors.size();
      for (int i = 0; i < neighborsSize; i++) {
        if (i != maxInfectIndex) {
          for (int val : neighbors.get(i)) {
            int x = val >> 16;
            int y = val & ((1 << 16) - 1);
            isInfected[x][y] = 1;
          }
        }
      }
    }
    while (neighbors.size() > 1);
    return res;
  }

  /**
   * Leetcode 752 : Open the Lock.
   * @Difficulty: Medium
   * @OptimalComplexity: O(Math.pow(10, 4) * 4 * 4 + m * 4) & O(Math.pow(10, 4) + m)
   * @param deadends String[]
   * @param target String
   * @return int
   */
  public int openLock(String[] deadends, String target) {
    if (target.equals("0000")) {
      return 0;
    }
    Set<String> set = new HashSet<>();
    Collections.addAll(set, deadends);
    if (set.contains("0000")) {
      return -1;
    }
    Queue<String> queue = new LinkedList<>();
    queue.offer("0000");
    Set<String> seen = new HashSet<String>();
    seen.add("0000");
    int step = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      step++;
      while (size-- > 0) {
        String str = queue.poll();
        assert str != null;
        for (String nextStatus : get(str)) {
          if (!set.contains(nextStatus) && !seen.contains(nextStatus)) {
            if (nextStatus.equals(target)) {
              return step;
            }
            queue.offer(nextStatus);
            seen.add(nextStatus);
          }
        }
      }
    }
    return -1;
  }

  public char numPrev(char x) {
    return x == '0' ? '9' : (char) (x - 1);
  }

  public char numSucc(char x) {
    return x == '9' ? '0' : (char) (x + 1);
  }

  public List<String> get(String str) {
    List<String> res = new ArrayList<>();
    char[] temp = str.toCharArray();
    for (int i = 0; i < 4; i++) {
      char t = temp[i];
      temp[i] = numPrev(t);
      res.add(new String(temp));
      temp[i] = numSucc(t);
      res.add(new String(temp));
      temp[i] = t;
    }
    return res;
  }

  int[][] neighbors = new int[][]{{1, 3}, {0, 2, 4}, {1, 5}, {0 ,4}, {1, 3, 5}, {2, 4}};

  /**
   * Leetcode 773 : Sliding Puzzle.
   * @Difficulty: Hard
   * @OptimalComplexity: O(mn!mn) & O(mn!mn)
   * @param board int[][]
   * @return int
   */
  public int slidingPuzzle(int[][] board) {
    StringBuilder sb = new StringBuilder();
    for (int[] ints : board) {
      for (int j = 0; j < board[0].length; j++) {
        sb.append(ints[j]);
      }
    }
    String start = sb.toString();
    if (start.equals("123450")) {
      return 0;
    }
    Queue<String> queue = new LinkedList<>();
    Set<String> set = new HashSet<>();
    queue.offer(start);
    int ans = 0;
    while (!queue.isEmpty()) {
      ans++;
      int size = queue.size();
      while (size-- > 0) {
        String temp = queue.poll();
        assert temp != null;
        for (String str : getString(temp)) {
          if (!set.contains(str)) {
            if (str.equals("123450")) {
              return ans;
            }
            queue.offer(str);
            set.add(str);
          }
        }
      }
    }
    return -1;
  }

  /**
   * get the list of strings that will be added to queue.
   * @param str String
   * @return list of string
   */
  public List<String> getString(String str) {
    List<String> res = new ArrayList<>();
    char[] crr = str.toCharArray();
    int x = str.indexOf('0');
    for (int y : neighbors[x]) {
      swap(crr, x, y);
      res.add(new String(crr));
      swap(crr, x, y);
    }
    return res;
  }

  /**
   * swap elements in array.
   * @param array char[]
   * @param x int
   * @param y int
   */
  public void swap(char[] array, int x, int y) {
    char temp = array[x];
    array[x] = array[y];
    array[y] = temp;
  }

  /**
   * Leetcode 815 : Bus Routes.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2 + n*m) & O(n2 + n*m)
   * @param routes int[][]
   * @param source int
   * @param target int
   * @return int
   */
  public int numBusesToDestination(int[][] routes, int source, int target) {
    if (source == target) {
      return 0;
    }
    Map<Integer, List<Integer>> map = new HashMap<>();
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[505];
    for (int i = 0; i < routes.length; i++) {
      for (int j = 0; j < routes[i].length; j++) {
        if (!map.containsKey(routes[i][j])) {
          map.put(routes[i][j], new ArrayList<>());
        }
        map.get(routes[i][j]).add(i);
      }
    }
    List<Integer> set = map.get(source);
    for (int node : set) {
      queue.add(node);
      visited[node] = true;
    }
    int ans = 0;
    while (!queue.isEmpty()) {
      ans++;
      int size = queue.size();
      while (size-- > 0) {
        int curr = queue.poll();
        for (int i = 0; i < routes[curr].length; i++) {
          if (routes[curr][i] == target) {
            return ans;
          }
          List<Integer> nextNode = map.get(routes[curr][i]);
          for (int node : nextNode) {
            if (!visited[node]) {
              queue.offer(node);
              visited[node] = true;
            }
          }
        }
      }
    }
    return -1;
  }

  /**
   * Leetcode 818 : Race Car.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param target int
   * @return int
   */
  public int racecar(int target) {
    if (target == 0) {
      return 0;
    }
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{0, 1, 0});
    Set<String> set = new HashSet<>();
    set.add("0@1");
    while(!queue.isEmpty()) {
      int[] curr = queue.poll();
      int[] opt1 = new int[]{curr[0] + curr[1], curr[1] * 2, curr[2] + 1};
      if (opt1[0] == target) {
        return opt1[2];
      }
      if (set.add(opt1[0] + "@" + opt1[1]) && opt1[0] > 0) {
        queue.offer(opt1);
      }
      int[] opt2 = new int[]{curr[0], curr[1], curr[2] + 1};
      if (opt2[1] > 0) {
        opt2[1] = -1;
      } else {
        opt2[1] = 1;
      }
      if (set.add(opt2[0] + "@" + opt2[1]) && opt2[0] > 0) {
        queue.offer(opt2);
      }
    }
    return -1;
  }

  /**
   * Leetcode 841 : Keys and Rooms.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m + n) * O(n)
   * @param rooms list of list of integers
   * @return boolean
   */
  public boolean canVisitAllRooms(List<List<Integer>> rooms) {
    boolean[] visited = new boolean[rooms.size()];
    Queue<Integer> queue = new LinkedList<>();
    for (int key : rooms.get(0)) {
      queue.offer(key);
    }
    visited[0] = true;
    while (!queue.isEmpty()) {
      int key = queue.poll();
      visited[key] = true;
      for (int nextRoom : rooms.get(key)) {
        if (!visited[nextRoom]) {
          queue.offer(nextRoom);
        }
      }
    }
    for (boolean isVisited : visited) {
      if (!isVisited) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 847 : Shortest Path Visiting All Nodes.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * 2^n) & O(n * 2^n)
   * @param graph int[][]
   * @return int
   */
  public int shortestPathLength(int[][] graph) {
    int n = graph.length;
    int ans = 0;
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] seen = new boolean[n][1 << n];
    for (int i = 0; i < n; i++) {
      queue.offer(new int[]{i, 1 << i, 0});
      seen[i][1 << i] = true;
    }

    while (!queue.isEmpty()) {
      int[] curr = queue.poll();
      int val = curr[0];
      int mask = curr[1];
      int dst = curr[2];
      if (mask == (1 << n) - 1) {
        ans = dst;
        break;
      }

      for (int next : graph[val]) {
        int maskNext = mask | (1 << next);
        if (!seen[next][maskNext]) {
          queue.offer(new int[]{next, maskNext, dst + 1});
          seen[next][maskNext] = true;
        }
      }
    }
    return ans;
  }
  int[][] directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

  /**
   * Leetcode 864 : Shortest Path to Get All Keys.
   * @Difficulty: hard
   * @OptimalComplexity: O(mn * 2 ^ k) & O(mn * 2 ^ k)
   * @param grid list of string
   * @return int
   */
  public int shortestPathAllKeys(String[] grid) {
    int m = grid.length;
    int n = grid[0].length();
    int startX = -1;
    int startY = -1;
    int count = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        char c = grid[i].charAt(j);
        if (c == '@') {
          startX = i;
          startY = j;
        } else if (c >= 'a' && c < 'g') {
          count = Math.max(count, c - 'a' + 1);
        }
      }
    }
    int max = 1 << count;
    int[][][] step = new int[m][n][max];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        Arrays.fill(step[i][j], (int)1e9);
      }
    }
    step[startX][startY][0] = 0;
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{startX, startY, 0, 0});
    while (!queue.isEmpty()) {
      int[] curr = queue.poll();
      for (int[] dir : directions) {
        int x = curr[0] + dir[0];
        int y = curr[1] + dir[1];
        if (x < 0 || x == m || y < 0 || y == n) {
          continue;
        }
        char c = grid[x].charAt(y);
        if (c == '#') {
          continue;
        }
        if (c == '.' || c == '@') {
          //empty spaces or start point
          if (step[x][y][curr[2]] > curr[3] + 1) {
            step[x][y][curr[2]] = curr[3] + 1;
            queue.offer(new int[]{x, y, curr[2], curr[3] + 1});
          }
        }
        else if (Character.isUpperCase(c)) {
          //no keys or less distance from start to here
          if ((curr[2] & (1 << (c - 'A'))) == 0 || step[x][y][curr[2]] <= curr[3] + 1) {
            continue;
          }
          //update
          step[x][y][curr[2]] = curr[3] + 1;
          queue.offer(new int[]{x, y, curr[2], curr[3] + 1});
        } else {
          // lower case
          // already has better path, continue
          if (step[x][y][curr[2] | (1 << (c - 'a'))] <= curr[3] + 1) {
            continue;
          }
          // find all keys
          if (max - 1 == (curr[2] | (1 << (c - 'a')))) {
            return curr[3] + 1;
          }
          // else , update
          step[x][y][curr[2] | (1 << (c - 'a'))] = curr[3] + 1;
          queue.offer(new int[]{x, y, curr[2] | (1 << (c - 'a')), curr[3] + 1});
        }
      }
    }
    return -1;
  }

  /**
   * Leetcode 909 : Snakes and Ladders.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) * O(n2)
   * @param board int[][]
   * @return int
   */
  public int snakesAndLadders(int[][] board) {
    Map<Integer,Integer> transfer = new HashMap<>();
    int n = board.length;
    for (int i=0;i<n;++i) {
      for (int j=0;j<n;++j) {
        if (board[i][j] != -1) {
          transfer.put(posToNum(i,j,n), board[i][j]);
        }
      }
    }
    Queue<Integer> queue = new LinkedList<>();
    boolean[] vis = new boolean[n * n + 1];
    queue.offer(1);
    vis[1] = true;
    int ans = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        int idx = queue.poll();
        if (idx == n * n) {
          return ans;
        }
        for (int i = 1; i <= 6; i++) {
          int next = idx + i;
          if (next <= n * n && !vis[next]) {
            vis[next] = true;
            if (transfer.containsKey(next)) {
              queue.offer(transfer.get(next));
            } else {
              queue.offer(next);
            }
          }
        }
      }
      ans++;
    }
    return -1;
  }

  /**
   * reduce dimension of the array to one dimension.
   * @param x int
   * @param y int
   * @param n int
   * @return int
   */
  public int posToNum(int x, int y, int n) {
    int group = n - 1 - x;
    int res = 0;
    if ((group & 1) == 0) {
      res = n * group + y + 1;
    } else {
      res = n * group + n - y;
    }
    return res;
  }

  /**
   * Leetcode 909 : Snakes and Ladders.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param board int[][]
   * @return int
   */
  public int snakesAndLaddersTwo(int[][] board) {
    Queue<int[]> queue = new LinkedList<>();
    int n = board.length;
    queue.offer(new int[]{1, 0});
    boolean[] vis = new boolean[n * n + 1];
    while (!queue.isEmpty()) {
      int[] curr = queue.poll();
      for (int i = 1; i <= 6; i++) {
        int nextIdx = curr[0] + i;
        if (nextIdx > n * n) {
          break;
        }
        int[] next = id2rc(nextIdx, n);
        if (board[next[0]][next[1]] != -1) {
          nextIdx = board[next[0]][next[1]];
        }
        if (nextIdx == n * n) {
          return curr[1] + 1;
        }
        if (!vis[nextIdx]) {
          queue.offer(new int[]{nextIdx, curr[1] + 1});
          vis[nextIdx] = true;
        }
      }
    }
    return -1;
  }

  /**
   * idx to row and column.
   * @param id int
   * @param n int
   * @return int[]
   */
  public int[] id2rc(int id, int n) {
    int r = (id - 1) / n;
    int c = (id - 1) % n;
    if (r % 2 == 1) {
      c = n - c - 1;
    }
    return new int[]{n - 1 - r, c};
  }
}
