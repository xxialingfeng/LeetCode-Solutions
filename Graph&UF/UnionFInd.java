import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a collection for leetcode problems related to union find.
 */
public class UnionFInd {

  /**
   * Leetcode 684 : Redundant Connection.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param edges int[][]
   * @return int[]
   */
  public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      parent[i] = i;
    }
    for (int[] edge : edges) {
      int node1 = edge[0];
      int node2 = edge[1];
      if (find(parent, node1) != find(parent, node2)) {
        union(parent, node1, node2);
      } else {
        return edge;
      }
    }
    return new int[0];
  }

  /**
   * join two nodes.
   * @param parent int[]
   * @param index1 int
   * @param index2 int
   */
  public void union(int[] parent, int index1, int index2) {
    parent[find(parent, index1)] = find(parent, index2);
  }

  /**
   * union two nodes.
   * @param x int
   * @param y int
   */
  void union(int x, int y) {
    if (find(x) != find(y)) parent[find(y)] = parent[x];
  }

  /**
   * Find the root.
   * @param parent int[]
   * @param index int
   * @return int
   */
  public int find(int[] parent, int index) {
    if (parent[index] != index) {
      parent[index] = find(parent, parent[index]);
    }
    return parent[index];
  }

  int[] parent;

  /**
   * find the root.
   * @param x int
   * @return int
   */
  int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]);
    return parent[x];
  }

  /**
   * Leetcode 685 : Redundant Connection II.
   * @param edges int[][]
   * @return int[]
   */
  public int[] findRedundantDirectedConnection(int[][] edges) {
    parent = new int[1001];
    int[] in = new int[1001];
    int[] res = {};
    for (int[] edge : edges) {
      if (++in[edge[1]] == 2) {
        res = edge;
      }
    }
    if (res.length != 0) {
      if (check(edges, res)) {
        return res;
      } else {
        for (int[] edge : edges) {
          if (edge[1] == res[1]) {
            return edge;
          }
        }
      }
    }

    for (int i = 0; i < 1001; i++) {
      parent[i] = i;
    }

    for (int[] edge : edges) {
      if (find(edge[0]) == find(edge[1])) {
        return edge;
      }
      else {
        union(edge[0], edge[1]);
      }
    }
    return new int[]{0};
  }

  /**
   * Check if can be a tree.
   * @param edges int[][]
   * @param remove int[]
   * @return boolean
   */
  boolean check(int[][] edges, int[] remove) {
    // 初始化并查集
    for (int i = 0; i < 1001; i++) {
      parent[i] = i;
    }
    for (int[] e : edges) {
      // 跳过要删除的边
      if (Arrays.equals(e, remove)) continue;
      // 删除之后构成的图案不为树
      if (find(e[0]) == find(e[1])) return false;
      else union(e[0], e[1]);
    }
    return true;
  }

  /**
   * Leetcode 721 : Accounts Merge.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * m) & O(n * m)
   * @param accounts list of list of String
   * @return list of list of string
   */
  public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> emailToIndex = new HashMap<>();
    Map<String, String> emailToName = new HashMap<>();
    int emailNumber = 0;
    for (List<String> account : accounts) {
      String name = account.get(0);
      for (int i = 1; i < account.size(); i++) {
        String email = account.get(i);
        if (!emailToIndex.containsKey(email)) {
          emailToIndex.put(email, emailNumber++);
          emailToName.put(email, name);
        }
      }
    }
    UnionFind uf = new UnionFind(emailNumber);
    for (List<String> account : accounts) {
      String firstEmail = account.get(1);
      int firstIndex = emailToIndex.get(firstEmail);
      for (int i = 2; i < account.size(); i++) {
        String nextEmail = account.get(i);
        int nextId = emailToIndex.get(nextEmail);
        uf.union(firstIndex, nextId);
      }
    }
    Map<Integer, List<String>> IndexToEmails = new HashMap<>();
    for (String email : emailToIndex.keySet()) {
      int index = uf.find(emailToIndex.get(email));
      List<String> account = IndexToEmails.getOrDefault(index, new ArrayList<>());
      account.add(email);
      IndexToEmails.put(index, account);
    }
    List<List<String>> ans = new ArrayList<>();
    for (List<String> list : IndexToEmails.values()) {
      Collections.sort(list);
      String name = emailToName.get(list.get(0));
      List<String> account = new ArrayList<String>();
      account.add(name);
      account.addAll(list);
      ans.add(account);
    }
    return ans;
  }

  /**
   * Leetcode 785 : Is Graph Bipartite?.
   * @OptimalComplexity: O(n x m) & O(n)
   * @Difficulty: Medium
   * @param graph int[][]
   * @return boolean
   */
  public boolean isBipartite(int[][] graph) {
    UnionFind uf = new UnionFind(graph.length);
    for (int i = 0; i < graph.length; i++) {
      int[] temp = graph[i];
      for (int w : temp) {
        if (uf.isConnected(w, i)) {
          return false;
        } else {
          uf.union(w, temp[0]);
        }
      }
    }
    return true;
  }

  /**
   * union find class.
   */
  static class UnionFind {
    int[] parent;

    public UnionFind(int n) {
      parent = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
      }
    }

    public void union(int index1, int index2) {
      parent[find(index2)] = find(index1);
    }

    public int find(int index) {
      if (parent[index] != index) {
        parent[index] = find(parent[index]);
      }
      return parent[index];
    }

    public boolean isConnected(int x, int v) {
      return find(x) == find(v);
    }
  }

  int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
  int cols;
  int rows;

  /**
   * Leetcode 803 : Bricks Falling When Hit.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n2)
   * @param grid int[][]
   * @param hits int[][]
   * @return int[]
   */
  public int[] hitBricks(int[][] grid, int[][] hits) {
    this.rows = grid.length;
    this.cols = grid[0].length;
    int[][] copy = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      if (cols >= 0)
        System.arraycopy(grid[i], 0, copy[i], 0, cols);
    }
    for (int[] hit : hits) {
      copy[hit[0]][hit[1]] = 0;
    }
    int size = rows * cols;
    UF uf = new UF(size + 1);
    for (int i = 0; i < grid[0].length; i++) {
      if (copy[0][i] == 1) {
        uf.union(size, i);
      }
    }

    for (int i = 1; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (copy[i][j] == 1) {
          if (copy[i - 1][j] == 1) {
            uf.union(getIndex(i - 1, j), getIndex(i, j));
          }
          if (j > 0 && copy[i][j - 1] == 1) {
            uf.union(getIndex(i, j - 1), getIndex(i, j));
          }
        }
      }
    }

    // 第 3 步：按照 hits 的逆序，在 copy 中补回砖块，把每一次因为补回砖块而与屋顶相连的砖块的增量记录到 res 数组中
    int hitsLen = hits.length;
    int[] res = new int[hitsLen];
    for (int i = hitsLen - 1; i >= 0; i--) {
      int x = hits[i][0];
      int y = hits[i][1];

      // 注意：这里不能用 copy，语义上表示，如果原来在 grid 中，这一块是空白，这一步不会产生任何砖块掉落
      // 逆向补回的时候，与屋顶相连的砖块数量也肯定不会增加
      if (grid[x][y] == 0) {
        continue;
      }

      // 补回之前与屋顶相连的砖块数
      int origin = uf.getSize(rows * cols);

      // 注意：如果补回的这个结点在第 1 行，要告诉并查集它与屋顶相连（逻辑同第 2 步）
      if (x == 0) {
        uf.union(y, rows * cols);
      }

      // 在 4 个方向上看一下，如果相邻的 4 个方向有砖块，合并它们
      for (int[] direction : directions) {
        int newX = x + direction[0];
        int newY = y + direction[1];

        if (newX >= 0 && newY >= 0 && newX < grid.length && newY < grid[0].length && copy[newX][newY] == 1) {
          uf.union(getIndex(x, y), getIndex(newX, newY));
        }
      }

      // 补回之后与屋顶相连的砖块数
      int current = uf.getSize(rows * cols);
      // 减去的 1 是逆向补回的砖块（正向移除的砖块），与 0 比较大小，是因为存在一种情况，添加当前砖块，不会使得与屋顶连接的砖块数更多
      res[i] = Math.max(0, current - origin - 1);

      // 真正补上这个砖块
      copy[x][y] = 1;
    }
    return res;
  }

  /**
   * 2D -> 1D.
   * @param x int
   * @param y int
   * @return int
   */
  public int getIndex(int x, int y) {
    return x * cols + y;
  }

  /**
   * uf.
   */
  public static class UF {
    int[] parents;
    int[] size;

    /**
     * constructor.
     * @param n int
     */
    public UF(int n) {
      parents = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parents[i] = i;
        size[i] = 1;
      }
    }

    public int find(int x) {
      if (x != parents[x]) {
        parents[x] = find(parents[x]);
      }
      return parents[x];
    }

    public void union(int x, int y) {
      int rootx = find(x);
      int rooty = find(y);
      if (rootx == rooty) {
        return;
      }
      parents[rootx] =  rooty;
      size[rooty] += size[rootx];
    }

    public int getSize(int x) {
      return size[find(x)];
    }
  }
}
