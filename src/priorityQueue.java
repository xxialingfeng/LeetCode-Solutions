import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This is a new Node class.
 */
class newNode {
  int i;
  int j;
  int val;

  public newNode(int i, int j, int val) {
    this.i = i;
    this.j = j;
    this.val = val;
  }
}

/**
 * This is a collection of leetcode problems related to priority Queue.
 */
public class priorityQueue {

  /**
   * Leetcode 632 : Smallest Range Covering Elements from K Lists.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nklogk) & O(k)
   * @param nums a list of list of inteegrs.
   * @return int[]
   */
  public int[] smallestRange(List<List<Integer>> nums) {
    int n = nums.size();
    int inf = 0x3f3f3f;
    int start = -inf;
    int end = inf;
    int max = -inf;

    PriorityQueue<newNode> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));

    for (int i = 0; i < n; i++) {
      int val = nums.get(i).get(0);
      pq.offer(new newNode(i, 0, val));
      max = Math.max(max, val);
    }

    while (pq.size() == n) {
      newNode node = pq.poll();
      int i = node.i;
      int j = node.j;
      int val = node.val;

      if (max - val < end - start) {
        start = val;
        end = max;
      }

      if (j + 1 < nums.get(i).size()) {
        int nval = nums.get(i).get(j + 1);
        pq.offer(new newNode(i, j + 1, nval));
        max = Math.max(max, nval);
      }
    }
    return new int[]{start, end};
  }
}
