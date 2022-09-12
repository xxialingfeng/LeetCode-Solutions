import java.util.Arrays;
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
}
