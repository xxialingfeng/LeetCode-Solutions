import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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

  /**
   * Leetcode 649 : Dota2 Senate.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param senate String
   * @return String
   */
  public String predictPartyVictory(String senate) {
    Queue<Integer> radiant = new LinkedList<>();
    Queue<Integer> dire = new LinkedList<>();
    int n = senate.length();
    char c;
    for (int i = 0; i < n; i++) {
      c = senate.charAt(i);
      if (c == 'R') {
        radiant.offer(i);
      } else {
        dire.offer(i);
      }
    }
    while (!radiant.isEmpty() && !dire.isEmpty()) {
      int r = radiant.remove();
      int d = dire.remove();
      if (r < d) {
        radiant.offer(r + n);
      } else {
        dire.offer(d + n);
      }
    }
    return radiant.isEmpty() ? "Dire" : "Radiant";
  }

  /**
   * Leetcode 703 : Kth Largest Element in a Stream.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   */
  class KthLargest {
    PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> a - b);
    int k;
    public KthLargest(int k, int[] nums) {
      Arrays.sort(nums);
      int idx = nums.length - 1;
      while (idx >= 0 && queue.size() != k) {
        queue.offer(nums[idx--]);
      }
      this.k = k;
    }

    public int add(int val) {
      if (queue.isEmpty()) {
        queue.offer(val);
        return val;
      }
      if (queue.size() < k) {
        queue.offer(val);
        return queue.peek();
      } else if (val <= queue.peek() && queue.size() >= k) {
        return queue.peek();
      } else {
        queue.poll();
        queue.offer(val);
        return queue.peek();
      }
    }
  }

  /**
   * Leetcode 786 : K-th Smallest Prime Fraction.
   * @Difficulty: Medium
   * @OptimalComplexity: O(mlogn) & O(n)
   * @param arr int[]
   * @param k int
   * @return int[]
   */
  public int[] kthSmallestPrimeFraction(int[] arr, int k) {
    PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> arr[a[0]] * arr[b[1]] - arr[a[1]] * arr[b[0]]);
    for (int i = 1; i < arr.length; i++) {
      queue.offer(new int[]{0, i});
    }
    while (k-- > 1) {
      int[] curr = queue.poll();
      int x = curr[0];
      int y = curr[1];
      if (x + 1 < arr.length && x + 1 < y) {
        queue.offer(new int[]{x + 1, y});
      }
    }
    int[] poll = queue.poll();
    return new int[]{arr[poll[0]], arr[poll[1]]};
  }
}
