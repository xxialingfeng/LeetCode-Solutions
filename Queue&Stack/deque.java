import java.util.ArrayDeque;
import java.util.Deque;

public class deque {

  /**
   * Leetcode 862 : Shortest Subarray with Sum at Least K.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int shortestSubarray(int[] nums, int k) {
    if (nums.length == 1) {
      return nums[0] >= k ? 1 : - 1;
    }
    long[] prefix = new long[nums.length + 1];
    for (int i = 1; i < prefix.length; i++) {
      prefix[i] = prefix[i - 1] + nums[i - 1];
    }
    long min = Long.MAX_VALUE;
    Deque<Integer> deque = new ArrayDeque<Integer>();
    for (int i = 0; i <= nums.length; i++) {
      long currSum = prefix[i];
      while (!deque.isEmpty() && currSum - prefix[deque.peekFirst()] >= k) {
        min = Math.min(min, i - deque.pollFirst());
      }
      while (!deque.isEmpty() && prefix[deque.peekLast()] >= currSum) {
        deque.pollLast();
      }
      deque.offerLast(i);
    }
    return min == Long.MAX_VALUE ? -1 : (int)min;
  }

  class RLEIterator {
    Deque<int[]> queue = new ArrayDeque<>();

    /**
     * Leetcode 900 : RLE Iterator.
     * @Difficulty: Medium
     * @OptimalComplexity: O(n) & O(n)
     * @param encoding int[]
     */
    public RLEIterator(int[] encoding) {
      for (int i = 1; i < encoding.length; i += 2) {
        if (encoding[i - 1] == 0) {
          continue;
        }
        queue.offer(new int[]{encoding[i], encoding[i - 1]});
      }
    }

    public int next(int n) {
      while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int currNum = curr[0];
        int currCount = curr[1];
        if (currCount > n) {
          currCount -= n;
          queue.offerFirst(new int[]{currNum, currCount});
          return currNum;
        } else if (currCount == n) {
          return currNum;
        } else {
          int offset = n - currCount;
          return next(offset);
        }
      }
      return -1;
    }
  }

}
