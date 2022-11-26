import java.util.Arrays;

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
}
