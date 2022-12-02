import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
}
