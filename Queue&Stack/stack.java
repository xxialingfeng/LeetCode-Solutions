import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

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

  /**
   * Leetcode 678 : Valid Parenthesis String.
   * @OptimalComplexity: O(n) & O(1)
   * @Difficulty: Medium
   * @param s String
   * @return boolean
   */
  public boolean checkValidString(String s) {
    if (s.length() == 0) {
      return true;
    }
    Stack<Integer> stackOne = new Stack<>();
    Stack<Integer> stackTwo = new Stack<>();
    char[] temp = s.toCharArray();
    for (int i = 0; i < temp.length; i++) {
      if (temp[i] == '(') {
        stackOne.push(i);
      } else if (temp[i] == '*') {
        stackTwo.push(i);
      } else if (temp[i] == ')') {
        if (!stackOne.isEmpty()) {
          stackOne.pop();
        } else if (!stackTwo.isEmpty()) {
          stackTwo.pop();
        } else {
          return false;
        }
      }
    }
    while (!stackOne.isEmpty() && !stackTwo.isEmpty()) {
      int leftIndex = stackOne.pop();
      int asteriskIndex = stackTwo.pop();
      if (leftIndex > asteriskIndex) {
        return false;
      }
    }
    return stackOne.isEmpty();
  }

  int i;
  int n;
  String formula;

  /**
   * Leetcode 726 : Number of Atoms.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n)
   * @param formula String
   * @return String
   */
  public String countOfAtoms(String formula) {
    this.i = 0;
    this.formula = formula;
    this.n = formula.length();
    Stack<Map<String, Integer>> stack = new Stack<>();
    stack.push(new HashMap<String, Integer>());
    while (i < n) {
      if (formula.charAt(i) == '(') {
        i++;
        stack.push(new HashMap<String, Integer>());

      } else if (formula.charAt(i) == ')') {
        i++;
        int num = parseNum();
        Map<String, Integer> popMap = stack.pop();
        Map<String, Integer> topMap = stack.peek();
        for (Map.Entry<String, Integer> entry : popMap.entrySet()) {
          String atom = entry.getKey();
          int v = entry.getValue();
          topMap.put(atom, topMap.getOrDefault(atom, 0) + v * num);
        }
      } else {
        String atom = parseAtom();
        int num = parseNum();
        Map<String, Integer> topMap = stack.peek();
        topMap.put(atom, topMap.getOrDefault(atom, 0) + num);
      }
    }

    Map<String, Integer> map = stack.pop();
    TreeMap<String, Integer> treemap = new TreeMap<String, Integer>(map);
    StringBuffer sb = new StringBuffer();
    for (Map.Entry<String, Integer> entry : treemap.entrySet()) {
      String atom = entry.getKey();
      int count = entry.getValue();
      sb.append(atom);
      if (count > 1) {
        sb.append(count);
      }
    }
    return sb.toString();
  }

  public int parseNum() {
    if (i == n || !Character.isDigit(formula.charAt(i))) {
      return 1;
    }
    int num = 0;
    while (i < n && Character.isDigit(formula.charAt(i))) {
      num = num * 10 + formula.charAt(i++) - '0';
    }
    return num;
  }

  public String parseAtom() {
    StringBuffer sb = new StringBuffer();
    sb.append(formula.charAt(i++));
    while (i < n && Character.isLowerCase(formula.charAt(i))) {
      sb.append(formula.charAt(i++));
    }
    return sb.toString();
  }

  /**
   * Leetcode 735 : Asteroid Collision.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param asteroids int[]
   * @return int[]
   */
  public int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();
    for (int asteroid : asteroids) {
      if (stack.isEmpty() || isSameDir(stack.peek(), asteroid)) {
        stack.push(asteroid);
      } else {
        boolean flag = false;
        while (!stack.isEmpty() && !isSameDir(stack.peek(), asteroid)) {
          if (stack.peek() < 0 && asteroid > 0) {
            flag = true;
            break;
          }
          if (stack.peek() == -1 * asteroid) {
            flag = false;
            stack.pop();
            break;
          } else if (stack.peek() < asteroid * -1) {
            flag = true;
            stack.pop();
          } else {
            flag = false;
            break;
          }
        }
        if (flag) {
          stack.push(asteroid);
        }
      }
    }
    int[] ans = new int[stack.size()];
    int i = stack.size() - 1;
    while (!stack.isEmpty()) {
      ans[i] = stack.pop();
      i--;
    }
    return ans;
  }

  /**
   * Return if the two asteroids are walking in the same direction.
   * @param a int
   * @param b int
   * @return boolean
   */
  public boolean isSameDir(int a, int b) {
    return (a > 0 && b > 0) || (a < 0 && b < 0);
  }

  /**
   * Leetcode 739 : Daily Temperatures.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param temperatures int[]
   * @return int[]
   */
  public int[] dailyTemperatures(int[] temperatures) {
    Stack<Integer> stack = new Stack<>();
    int i = 0;
    int[] ans = new int[temperatures.length];
    stack.push(0);
    while (i < temperatures.length) {
      while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
        int temp = stack.pop();
        ans[temp] = i - temp;
      }
      stack.push(i);
      i++;
    }
    return ans;
  }

  /**
   * Leetcode 768 : Max Chunks To Make Sorted II.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param arr int[]
   * @return int
   */
  public int maxChunksToSorted(int[] arr) {
    Stack<Integer> stack = new Stack<>();
    for (int num : arr) {
      if (stack.isEmpty() || stack.peek() <= num) {
        stack.push(num);
      } else {
        int n = stack.pop();
        while (!stack.isEmpty() && stack.peek() > num) {
          stack.pop();
        }
        stack.push(n);
      }
    }
    return stack.size();
  }

  /**
   * Leetcode 1856 : Maximum Subarray Min-Product.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return int
   */
  public int maxSumMinProduct(int[] nums) {
    long res = 0;
    long[] prefix = new long[nums.length + 1];
    for (int i = 1; i <= nums.length; i++) {
      prefix[i] = nums[i - 1] + prefix[i - 1];
    }
    int[] left = new int[nums.length];
    int[] right = new int[nums.length];
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < nums.length; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      left[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }
    stack.clear();
    for (int i = nums.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      right[i] = stack.isEmpty() ? nums.length : stack.peek();
      stack.push(i);
    }

    for (int i = 0; i < nums.length; i++) {
      res = Math.max(res, (prefix[right[i]] - prefix[left[i] + 1]) * nums[i]);
    }

    return (int) (res % 1000000007);
  }

  /**
   * Leetcode 84 : Largest Rectangle in Histogram.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param heights int[]
   * @return int
   */
  public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    int[] l = new int[heights.length];
    int[] r = new int[heights.length];
    for (int i = 0; i < heights.length; i++) {
      while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
        stack.pop();
      }
      l[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }
    stack.clear();
    for (int i = heights.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
        stack.pop();
      }
      r[i] = stack.isEmpty() ? heights.length: stack.peek();
      stack.push(i);
    }
    int res = 0;
    for (int i = 0; i < heights.length; i++) {
      res = Math.max(res, heights[i] * (r[i] - l[i] - 1));
    }

    return res;
  }

  /**
   * Leetcode 856 : Score of Parentheses.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param s string
   * @return int
   */
  public int scoreOfParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    int curr = 0;
    int ans = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        stack.push(0);
      } else {
        if (stack.peek() == 0) {
          stack.pop();
          stack.push(1);
        } else {
          while (!stack.isEmpty() && stack.peek() != 0) {
            curr += stack.pop();
          }
          stack.pop();
          stack.push(curr * 2);
          curr = 0;
        }
      }
    }

    while (!stack.isEmpty() && stack.peek() != -1) {
      ans += stack.pop();
    }

    return ans;
  }

  class StockSpanner {
    Deque<int[]> stack = new LinkedList<>();

    /**
     * Leetcode 901 Online Stock Span.
     * @Difficulty: O(n) & O(n)
     */
    public StockSpanner() {

    }

    public int next(int price) {
      int[] res = new int[]{price, 1};
      while (!stack.isEmpty() && price >= stack.peek()[0]) {
        res[1] += stack.pop()[1];
      }
      stack.push(res);
      return res[1];
    }
  }

  /**
   * Leetcode 921 : Minimum Add to Make Parentheses Valid.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param s string
   * @return int
   */
  public int minAddToMakeValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') {
        stack.push(c);
      }
      if (c == ')') {
        if (stack.isEmpty() || stack.peek() == ')') {
          stack.push(c);
        } else {
          stack.pop();
        }
      }
    }
    return stack.size();
  }

  /**
   * Leetcode 946 : Validate Stack Sequences
   * @param pushed int[]
   * @param popped int[]
   * @return boolean
   */
  public boolean validateStackSequences(int[] pushed, int[] popped) {
    Map<Integer, Integer> pushidxMap = new HashMap<>();
    List<Integer> pushlist = new ArrayList<>();
    for (int i = 0; i < popped.length; i++) {
      pushidxMap.put(pushed[i], i);

    }
    boolean[] poped = new boolean[pushed.length];
    int curridx = 0;
    for (int k : popped) {
      int idx = pushidxMap.get(k);
      for (int i = curridx; i <= idx; i++) {
        if (!poped[i]) {
          pushlist.add(pushed[i]);
        }
      }
      if (k != pushlist.get(pushlist.size() - 1)) {
        return false;
      }
      pushlist.remove(pushlist.size() - 1);
      poped[idx] = true;
      curridx = idx;
    }
    return true;
  }
}
