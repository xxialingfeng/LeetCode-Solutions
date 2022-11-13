import java.util.Arrays;
import java.util.HashMap;
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
}
