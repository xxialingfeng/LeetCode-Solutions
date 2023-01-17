import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {

  /**
   * Leetcode 743 : Network Delay Time.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(n2)
   * @param times int[][]
   * @param n int
   * @param k int
   * @return int
   */
  public int networkDelayTime(int[][] times, int n, int k) {
    int[][] graph = new int[n][n];
    for (int i = 0; i < n; i++) {
      Arrays.fill(graph[i], Integer.MAX_VALUE / 2);
    }
    for (int[] time : times) {
      int src = time[0] - 1;
      int tar = time[1] - 1;
      int weight = time[2];
      graph[src][tar] = weight;
    }
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE / 2);
    dist[k - 1] = 0;
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      int curr = -1;
      for (int j = 0; j < n; j++) {
        if (!visited[j] && (curr == -1 || dist[j] < dist[curr])) {
          curr = j;
        }
      }
      visited[curr] = true;
      for (int j = 0; j < n; j++) {
        dist[j] = Math.min(dist[j], dist[curr] + graph[curr][j]);
      }
    }
    int ans = 0;
    for (int i = 0; i < n; i++) {
      ans = Math.max(ans, dist[i]);
    }
    return ans >= Integer.MAX_VALUE / 2 ? -1 : ans;
  }

  List<String> ans = new ArrayList<>();
  PriorityQueue<String> queue = new PriorityQueue<>();//保证每次取出的值是最小的

  /**
   * Leetcode 332 : Reconstruct Itinerary.
   * @Difficulty: Hard
   * @OptimalComplexity: O(mn + m) & O(mn + m)
   * @param tickets list of list of strings
   * @return list of string
   */
  public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> map = new HashMap<>();
    for (List<String> ticket : tickets) {
      String src = ticket.get(0);
      String tar = ticket.get(1);
      if (!map.containsKey(src)) {
        map.put(src, new PriorityQueue<>());
      }
      PriorityQueue toAdd = map.get(src);
      toAdd.offer(tar);
      map.put(src, toAdd);
    }
    dfs("JFK", tickets, map);
    Collections.reverse(ans);
    return ans;
  }

  /**
   * dfs for leetcode 322.
   * @param start String
   * @param tickets list of list of string
   * @param map Priority queue.
   */
  public void dfs(String start, List<List<String>> tickets, Map<String, PriorityQueue<String>> map) {
    while (map.containsKey(start) && map.get(start).size() > 0) {
      String next = map.get(start).poll();
      dfs(next, tickets, map);
    }
    ans.add(start);
  }

  /**
   * Leetcode 802 : Find Eventual Safe States.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m + n) & O(n + m)
   * @param graph int[][]
   * @return list of integer
   */
  public List<Integer> eventualSafeNodes(int[][] graph) {
    List<List<Integer>> reverseG = new ArrayList<>();
    for (int i = 0; i < graph.length; i++) {
      reverseG.add(new ArrayList<>());
    }
    int[] inDeg = new int[graph.length];
    for (int x = 0; x < graph.length; x++) {
      for (int y : graph[x]) {
        reverseG.get(y).add(x);
      }
      inDeg[x] = graph[x].length;
    }
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < inDeg.length; i++) {
      if (inDeg[i] == 0) {
        queue.offer(i);
      }
    }
    while (!queue.isEmpty()) {
      int idx = queue.poll();
      for (int i : reverseG.get(idx)) {
        if (--inDeg[i] == 0) {
          queue.offer(i);
        }
      }
    }
    List<Integer> ans = new ArrayList<>();
    for (int i = 0; i < inDeg.length; i++) {
      if (inDeg[i] == 0) {
        ans.add(i);
      }
    }
    return ans;
  }

}
