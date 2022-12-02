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
  }
}
