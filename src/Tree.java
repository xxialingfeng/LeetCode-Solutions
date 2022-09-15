import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode() {}

  TreeNode(int val) {
    this.val = val;
  }

  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

/**.
 * A collection for leetcode problems related to Tree
 */
public class Tree {

  /**.
   * @leetcode 501 : Find Mode in Binary Search Tree
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   */
  //Intuition
  Map<Integer, Integer> map = new HashMap<>();

  /**.
   * find the max value in the keySet
   * @param root TreeNode
   * @return int[]
   */
  public int[] findMode(TreeNode root) {
    find(root);
    int max = 0;
    List<Integer> list = new ArrayList<>();
    for (int temp : map.keySet()) {
      if (map.get(temp) > max) {
        max = map.get(temp);
      }
    }
    for (int temp : map.keySet()) {
      if (map.get(temp) == max) {
        list.add(temp);
      }
    }
    int[] ans = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ans[i] = list.get(i);
    }
    return ans;
  }

  /**.
   * dfs all the nodes
   * @param root TreeNode
   */
  public void find(TreeNode root) {
    if (root == null) {
      return;
    }
    map.put(root.val, map.getOrDefault(root.val, 0) + 1);
    find(root.left);
    find(root.right);
  }

  List<Integer> answer = new ArrayList<Integer>();
  int base;
  int count;
  int maxCount;

  /**.
   * Optimal Solution1
   * @param root TreeNode
   * @return int[]
   */
  public int[] OptimalFindMode(TreeNode root) {
    dfs(root);
    int[] mode = new int[answer.size()];
    for (int i = 0; i < answer.size(); ++i) {
      mode[i] = answer.get(i);
    }
    return mode;
  }

  /**.
   * Mid Order Traverse
   * @param o TreeNode
   */
  public void dfs(TreeNode o) {
    if (o == null) {
      return;
    }
    dfs(o.left);
    update(o.val);
    dfs(o.right);
  }

  /**.
   * update max count, which is very important
   * @param x int
   */
  public void update(int x) {
    if (x == base) {
      ++count;
    } else {
      count = 1;
      base = x;
    }
    if (count == maxCount) {
      answer.add(base);
    }
    if (count > maxCount) {
      maxCount = count;
      answer.clear();
      answer.add(base);
    }
  }

  /**.
   * LeetCode 508: Most Frequent Subtree Sum
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   */
  int max508 = 0;
  Map<Integer, Integer> map508 = new HashMap<>();

  /**.
   * Return int[]
   * @param root TreeNode
   * @return int[]
   */
  public int[] findFrequentTreeSum(TreeNode root) {
    if (root == null) {
      return new int[0];
    }
    dfs(root);
    List<Integer> list = new ArrayList<>();
    for (int key : map508.keySet()) {
      if (map508.get(key) == max508) {
        list.add(key);
      }
    }
    int[] ans = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ans[i] = list.get(i);
    }
    return ans;

  }

  /**.
   * dfs method for getting all the sum values
   * @param root TreeNode
   * @return a int value
   */
  public int dfs508(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int left = dfs508(root.left);
    int right = dfs508(root.right);
    int val = root.val + left + right; //If you need get sum from bottom to top, you can code this way.
    map508.put(val, map508.getOrDefault(val, 0) + 1);
    max508 = Math.max(max508, map.get(val));
    return val;
  }

  /**.
   * Leetcode 513: Find Bottom Left Tree Value
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   */
  public int findBottomLeftValue(TreeNode root) {
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      root = queue.poll();
      if (root.right != null) { //If we want to get the left bottom value, we need to add right child to
        //the queue first.
        queue.offer(root.right);
      }
      if (root.left != null) {
        queue.offer(root.left);
      }
    }
    return root.val;
  }

}
