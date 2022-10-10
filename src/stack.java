import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * A collection for leetcode problems related to stack.
 */
public class stack {

  /**.
   * LeetCode 503: Next Greater Element II
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int[]
   */
  public int[] nextGreaterElements(int[] nums) {
    int[] res = new int[nums.length];
    Stack<Integer> stack = new Stack<>();
    Arrays.fill(res, -1);
    for (int i = 0; i < 2 * nums.length - 1; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] < nums[i % nums.length]) {
        res[stack.pop()] = nums[i % nums.length]; //Monotonic stack means the numbers in the stack
        // is increasing or decreasing. In this way, we only push indexes of the array into the stack.
      }
      stack.push(i % nums.length);
    }
    return res;
  }

  /**
   * Leetcode 636 : Exclusive Time of Functions.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param n int
   * @param logs list of string
   * @return int[]
   */
  public int[] exclusiveTime(int n, List<String> logs) {
    int[] ans = new int[n];
    Stack<int[]> stack = new Stack<>();
    for (String log : logs) {
      String[] str = log.split(":");
      int id = Integer.parseInt(str[0]);
      int timestamp = Integer.parseInt(str[2]);
      if (str[1].equals("start")) {
        if (!stack.isEmpty()) {
          ans[stack.peek()[0]] += timestamp - stack.peek()[1];
          stack.peek()[1] = timestamp;
        }
        stack.push(new int[]{id, timestamp});
      } else {
        int[] temp = stack.pop();
        ans[temp[0]] += timestamp - temp[1] + 1;
        if (!stack.isEmpty()) {
          stack.peek()[1] = timestamp + 1;
        }
      }
    }
    return ans;
  }
}
