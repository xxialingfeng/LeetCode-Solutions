import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
