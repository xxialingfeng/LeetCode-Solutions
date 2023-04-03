import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    int cnt;

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
      cnt--;
    }

    public int getSize(int x) {
      return size[find(x)];
    }
  }

  int count;

  /**
   * Leetcode 827 : Making A Large Island.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2 * m) & O(n2)
   * @param grid int[][]
   * @return int
   */
  public int largestIsland(int[][] grid) {
    UF uf = new UF(grid.length * grid.length);
    int ans = 1;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] == 1) {
          for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && y >= 0 && x < grid.length && y < grid.length && grid[x][y] == 1) {
              int pa = uf.find(x * grid.length + y);
              int pb = uf.find(i * grid.length + j);
              if (pa == pb) {
                continue;
              }
              uf.union(x * grid.length + y, i * grid.length + j );
              ans = Math.max(ans, uf.size[pb]);
            }
          }
        }
      }
    }

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] == 0) {
          int cnt = 1;
          Set<Integer> set = new HashSet<>();
          for (int[] dir : directions) {
            int nx = i + dir[0];
            int ny = j + dir[1];
            if (nx >= 0 && ny < grid.length && ny >= 0 && nx < grid.length && grid[nx][ny] == 1) {
              int root = uf.find(nx * grid.length + ny);
              if (!set.contains(root)) {
                cnt += uf.size[root];
                set.add(root);
              }
            }
          }
          ans = Math.max(ans, cnt);
        }
      }
    }
    return ans;
  }

  /**
   * Leetcode 839 : Similar String Groups.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2 * m + nlog(n)) & O(n)
   * @param strs
   * @return
   */
  public int numSimilarGroups(String[] strs) {
    int[] parents = new int[strs.length];
    for (int i = 0; i < parents.length; i++) {
      parents[i] = i;
    }
    int count = parents.length;
    for (int i = 0; i < strs.length - 1; i++) {
      for (int j = i + 1; j < strs.length; j++) {
        if (isSim(strs[i], strs[j])) {
          count = union(i, j, parents, count);
        }
      }
    }
    return count;
  }

  public int union(int x, int v, int[] parents, int count) {
    int rootx = find(x, parents);
    int rootv = find(v, parents);
    if (rootx == rootv) {
      return count;
    }
    parents[rootx] = parents[rootv];
    return --count;
  }

  public int find(int x, int[] parents) {
    if (x != parents[x]) {
      parents[x] = find(parents[x], parents);
    }
    return parents[x];
  }

  public boolean isSim(String str1, String str2) {
    if (str1.equals(str2)) {
      return true;
    }
    int cnt = 0;
    int index = 0;
    while (index < str1.length()) {
      if (str1.charAt(index) != str2.charAt(index)) {
        cnt++;
      }
      if (cnt > 2) {
        return false;
      }
      index++;
    }
    return true;
  }

  /**
   * Leetcode 886 : Possible Bipartition.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * m) & O(n)
   * @param n int
   * @param dislikes int[][]
   * @return boolean
   */
  public boolean possibleBipartition(int n, int[][] dislikes) {
    Arrays.sort(dislikes, (a, b) -> a[0] - b[0]);
    UnionFind uf = new UnionFind(n + 1);
    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < dislikes.length; i++) {
      if (!map.containsKey(dislikes[i][0])) {
        map.put(dislikes[i][0], new ArrayList<>());
      }
      if (!map.containsKey(dislikes[i][1])) {
        map.put(dislikes[i][1], new ArrayList<>());
      }
      map.get(dislikes[i][0]).add(dislikes[i][1]);
      map.get(dislikes[i][1]).add(dislikes[i][0]);
    }
    for (int i = 1; i <= n; i++) {
      List<Integer> list = map.get(i);
      if (list == null || list.size() == 1) {
        continue;
      }
      for (int j = 1; j < list.size(); j++) {
        uf.union(list.get(j), list.get(j - 1));
      }
    }

    for (int i = 0; i < dislikes.length; i++) {
      if (uf.isConnected(dislikes[i][0], dislikes[i][1])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 924 : Minimize Malware Spread.
   * @Difficulty: Hard
   * @OptimalComplexity: O(N2) & O(N)
   * @param graph int[][]
   * @param initial int[]
   * @return int
   */
  public int minMalwareSpread(int[][] graph, int[] initial) {
    UFwithSize uf = new UFwithSize(graph.length);
    for (int i = 0; i < graph.length; i++) {
      for (int j = i + 1; j < graph.length; j++) {
        if (graph[i][j] == 1) {
          uf.union(i, j);
        }
      }
    }

    int[] count = new int[graph.length];
    for (int node : initial) {
      count[uf.find(node)]++;
    }

    int ans = -1;
    int ansSize = -1;
    for (int node : initial) {
      int root = uf.find(node);
      if (count[root] == 1) {
        int rootSize = uf.size(root);
        if (rootSize > ansSize) {
          ansSize = rootSize;
          ans = node;
        } else if (rootSize == ansSize && node < ans) {
          ansSize = rootSize;
          ans = node;
        }
      }
    }
    if (ans == -1) {
      ans = Integer.MAX_VALUE;
      for (int node : initial) {
        ans = Math.min(ans, node);
      }
    }
    return ans;
  }


  public class UFwithSize {
    int[] parents;
    int[] size;

    public UFwithSize(int n) {
      parents = new int[n];
      for (int i = 0; i < parents.length; i++) {
        parents[i] = i;
      }
      size = new int[n];
      Arrays.fill(size, 1);
    }

    public boolean isConn(int x, int v) {
      return parents[x] == parents[v];
    }

    public int find(int x) {
      if (parents[x] != x) {
        parents[x] = find(parents[x]);
      }
      return parents[x];
    }

    public void union(int x, int v) {
      int rootx = find(x);
      int rootv = find(v);
      if (rootx == rootv) {
        return;
      }
      parents[rootx] = rootv;
      size[rootv] += size[rootx];
    }

    public int size(int x) {
      return size[find(x)];
    }
  }

  /**
   * Leetcode 928 : Minimize Malware Spread II.
   * @Difficulty: hard
   * @OptimalComplexity: O(n * n) & O(n)
   * @param graph int[][]
   * @param initial int[]
   * @return int
   */
  public int minMalwareSpreadII(int[][] graph, int[] initial) {
    int minM = graph.length;
    Arrays.sort(initial);
    int idx = initial[0];
    for (int node : initial) {
      UFwithSize uf = new UFwithSize(graph.length);
      for (int i = 0; i < graph.length; i++) {
        if (i == node) {
          continue;
        }
        for (int j = 0; j < graph.length; j++) {
          if (j == node) {
            continue;
          }
          if (graph[i][j] == 1) {
            uf.union(i, j);
          }
        }
      }
      int m = 0;
      Set<Integer> set = new HashSet<>();
      for (int i : initial) {
        if (i == node) {
          continue;
        }
        if (set.contains(uf.find(i))) {
          continue;
        }
        set.add(uf.find(i));
        m += uf.size(uf.find(i));
      }
      if (m < minM) {
        minM = m;
        idx = node;
      }
    }
    return idx;
  }

  /**
   * Leetcode 947 : Most Stones Removed with Same Row or Column.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param stones
   * @return
   */
  public int removeStones(int[][] stones) {
    UF uf = new UF(stones.length);
    for (int i = 0; i < stones.length; i++) {
      for (int j = i + 1; j < stones.length; j++) {
        if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
          uf.union(i, j);
        }
      }
    }
    return stones.length - uf.cnt;
  }

  /**
   * Leetcode 952 : Largest Component Size by Common Factor.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n * a(n) * sqrt(m)) & O(m)
   */
  public int largestComponentSize(int[] nums) {
    int m = Arrays.stream(nums).max().getAsInt();
    UF uf = new UF(m + 1);
    for (int num : nums) {
      for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) {
          uf.union(num, i);
          uf.union(num, num / i);
        }
      }
    }
    int[] count = new int[m + 1];
    int ans = 0;
    for (int num : nums) {
      int root = uf.find(num);
      count[root]++;
      ans = Math.max(ans, count[root]);
    }
    return ans;
  }

  /**
   * Leetcode 959 : Regions Cut By Slashes.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2logn) & O(n2)
   * @param grid list of strings
   * @return int
   */
  public int regionsBySlashes(String[] grid) {
    int n = grid.length;
    int total = n * n * 4;
    UF uf = new UF(total);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int pos = i * n + j;
        if (j > 0) {
          uf.union(pos * 4 + 3, (pos - 1) * 4 + 1);
        }
        if (i < n - 1) {
          uf.union(pos * 4 + 2, (pos + n) * 4);
        }
        int seat = pos * 4;
        if (grid[i].charAt(j) == '/') {
          uf.union(seat, seat + 3);
          uf.union(seat + 1, seat + 2);
        } else if (grid[i].charAt(j) == '\\') {
          uf.union(seat, seat + 1);
          uf.union(seat + 2, seat + 3);
        } else {
          uf.union(seat, seat + 1);
          uf.union(seat + 1, seat + 2);
          uf.union(seat + 2, seat + 3);
        }
      }
    }
    return uf.cnt;
  }
}
