import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

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

  int ans530;
  int pre530;

  /**
   * Leetcode 530: Minimum Absolute Difference in BST.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return int
   */
  public int getMinimumDifference(TreeNode root) {
    ans530 = Integer.MAX_VALUE;
    pre530 = -1;
    dfs530(root);
    return ans530;
  }

  /**
   * mid-order traverse dfs which get the increasing sequence directly.
   * @param root TreeNode
   */
  public void dfs530(TreeNode root) {
    if (root == null) {
      return;
    }
    dfs(root.left);
    if (pre530 != -1) {
      ans530 = Math.min(ans530, root.val - pre530);
    }
    pre530 = root.val;
    dfs(root.right);
  }

  /**
   * Leetcode 563: Binary Tree Tilt.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return int
   */
  public int findTilt(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return Math.abs(sum(root.left) - sum(root.right))
        + findTilt(root.left) + findTilt(root.right);
  }

  /**
   * sum of the nodes.
   * @param root TreeNode
   * @return int
   */
  public int sum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return root.val + sum(root.left) + sum(root.right);
  }

  /**
   * Leetcode 623 : Add One Row to Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @param val int
   * @param depth int
   * @return TreeNode
   */
  public TreeNode addOneRow(TreeNode root, int val, int depth) {
    if (root == null) {
      return null;
    }
    if (depth == 1) {
      TreeNode node = new TreeNode(val);
      node.left = root;
      return node;
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    int count = 1;
    queue.offer(root);
    while (count < depth - 1) {
      int size = queue.size();
      while (size > 0) {
        TreeNode node = queue.poll();
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
        size--;
      }
      count++;
    }
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      node.left = new TreeNode(val, node.left, null);
      node.right = new TreeNode(val, null, node.right);
    }
    return root;
  }

  /**
   * Leetcode 617 : Merge Two Binary Trees.
   * @param root1 TreeNode
   * @param root2 TreeNode
   * @return TreeNode
   */
  public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
    if (root1 == null) {
      return root2;
    }
    if (root2 == null) {
      return root1;
    }
    TreeNode node = new TreeNode(root1.val + root2.val);
    node.left = mergeTrees(root1.left, root2.left);
    node.right = mergeTrees(root1.right, root2.right);
    return node;
  }

  /**
   * Leetcode 652 : Find Duplicate Subtrees.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return list of tree nodes
   */
  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
    List<TreeNode> res = new ArrayList<>();
    dfs(root);
    return res;
  }

  Map<String,Integer> map653 = new HashMap<>();

  /**
   * dfs method.
   * @param root TreeNode
   * @param res list of tree nodes
   * @return String
   */
  private String dfs652(TreeNode root, List<TreeNode> res) {
    if (root == null) return "#";
    String s = root.val + " " + dfs652(root.left, res) + " " + dfs652(root.right, res);
    if (map653.containsKey(s) && map653.get(s) == 1) {
      res.add(root);
    }
    map653.put(s, map653.getOrDefault(s,0) + 1);
    return s;
  }

  /**
   * Leetcode 669 : Trim a Binary Search Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @param low int
   * @param high int
   * @return TreeNode
   */
  public TreeNode trimBST(TreeNode root, int low, int high) {
    if (root == null) {
      return root;
    }

    if (root.val < low) {
      return trimBST(root.right, low, high);
    }
    if (root.val > high) {
      return trimBST(root.left, low, high);
    }
    root.left = trimBST(root.left, low, high);
    root.right = trimBST(root.right, low, high);
    return root;
  }

  /**
   * Leetcode 670 : Maximum Swap.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1) while n is the length of the string.
   * @param num int
   * @return int
   */
  public int maximumSwap(int num) {
    String str = String.valueOf(num);
    char[] temp = str.toCharArray();
    int max = num;
    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < temp.length; j++) {
        if (i != j) {
          swap(temp, i, j);
          max = Math.max(max, Integer.parseInt(new String(temp)));
        }
        temp = str.toCharArray();
      }
    }
    return max;
  }

  /**
   * swap method.
   * @param temp char[]
   * @param i int
   * @param j int
   */
  public void swap(char[] temp, int i, int j) {
    char t = temp[i];
    temp[i] = temp[j] ;
    temp[j] = t;
  }

  private int max687 = 0;

  /**
   * Leetcode 687 : Longest Univalue Path.
   * @param root TreeNode
   * @return int
   */
  public int longestUnivaluePath(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (root.left == null && root.right == null) {
      return 0;
    }
    getMax(root, root.val);
    return max687;
  }

  /**
   * get the max length.
   * @param root TreeNode
   * @param val int
   * @return int
   */
  public int getMax(TreeNode root, int val) {
    if (root == null) {
      return 0;
    }
    int l = getMax(root.left, root.val);
    int r = getMax(root.right, root.val);
    max687 = Math.max(max687, l + r);
    if (root.val == val) {
      return Math.max(l, r) + 1;
    } else {
      return 0;
    }
  }

  /**
   * Leetcode 700 : Search in a Binary Search Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root Tree node
   * @param val int
   * @return tree node
   */
  public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
      return null;
    }
    if (root.val == val) {
      return root;
    }
    if (root.val > val) {
      return searchBST(root.left, val);
    }
    return searchBST(root.right, val);
  }

  /**
   * Leetcode 701 : Insert into a Binary Search Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param root tree node
   * @param val int
   * @return tree node
   */
  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) {
      return new TreeNode(val);
    }
    if (root.val > val) {
      root.left = insertIntoBST(root.left, val);
    } else {
      root.right = insertIntoBST(root.right, val);
    }
    return root;
  }

  /**
   * Leetcode 715 : Range Module.
   * @Difficulty: Hard
   *     TreeMap lowerKey() and floorKey()
   */
  class RangeModule {
    TreeMap<Integer, Integer> map;
    public RangeModule() {
      map = new TreeMap<>();
    }

    public void addRange(int left, int right) {
      while (true) {
        Integer a = map.floorKey(right);
        if (a == null || map.get(a) < left) {
          break;
        }
        left = Math.min(left, a);
        right = Math.max(map.get(a), right);
        map.remove(a);
      }
      map.put(left, right);
    }

    public boolean queryRange(int left, int right) {
      Integer a = map.floorKey(left);
      return a != null && map.get(a) >= right;
    }

    public void removeRange(int left, int right) {
      while (true) {
        Integer a = map.lowerKey(right);
        if (a == null || map.get(a) <= left) {
          break;
        }
        if (map.get(a) > right) {
          map.put(right, map.get(a));
        }
        if (a >= left) {
          map.remove(a);
        } else {
          map.put(a, left);
        }
      }
    }
  }

  /**
   * Leetcode 814 : Binary Tree Pruning.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root tree node
   * @return tree node
   */
  public TreeNode pruneTree(TreeNode root) {
    if (!containsOne(root)) {
      return null;
    }
    if (root == null) {
      return root;
    }
    if (!containsOne(root.left)) {
      root.left = null;
    } else {
      root.left = pruneTree(root.left);
    }
    if (!containsOne(root.right)) {
      root.right = null;
    } else {
      root.right = pruneTree(root.right);
    }
    return root;
  }

  /**
   * if the tree contains one.
   * @param root tree node
   * @return boolean
   */
  public boolean containsOne(TreeNode root) {
    if (root == null) {
      return false;
    }
    if (root.val == 1) {
      return true;
    }
    return containsOne(root.left) || containsOne(root.right);
  }

  /**
   * Leetcode 865 : Smallest Subtree with all the Deepest Nodes.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param root treenode
   * @return treenode
   */
  public TreeNode subtreeWithAllDeepest(TreeNode root) {
    if (root == null) {
      return null;
    } else {
      int ldep = maxD(root.left);
      int rdep = maxD(root.right);
      if (ldep == rdep) {
        return root;
      } else if (ldep > rdep) {
        return subtreeWithAllDeepest(root.left);
      } else {
        return subtreeWithAllDeepest(root.right);
      }
    }
  }

  public int maxD(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return Math.max(maxD(root.left), maxD(root.right)) + 1;
  }

  List<Integer> list = new ArrayList<>();

  /**
   * Leetcode 897 : Increasing Order Search Tree.
   * @Difficulty: O(n) & O(n)
   * @param root treenode
   * @return treenode
   */
  public TreeNode increasingBST(TreeNode root) {
    TreeNode head = new TreeNode(-1);
    TreeNode curr = head;
    dfs897(root);
    for (int i : list) {
      curr.left = null;
      curr.right = new TreeNode(i);
      curr = curr.right;
    }
    return head.right;
  }

  public void dfs897(TreeNode node) {
    if (node == null) {
      return;
    }
    dfs897(node.left);
    list.add(node.val);
    dfs897(node.right);
  }

  /**
   * Leetcode 919 : Complete Binary Tree Inserter.
   * @Difficulty: Medium
   */
  class CBTInserter {
    Map<Integer, TreeNode> map;
    int size;

    public CBTInserter(TreeNode root) {
      map = new HashMap<>();
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);
      int index = 0;
      while (!queue.isEmpty()) {
        TreeNode curr = queue.poll();
        map.put(++index, curr);
        if (curr.left != null) {
          queue.offer(curr.left);
        }
        if (curr.right != null) {
          queue.offer(curr.right);
        }
      }
      size = index;
    }

    public int insert(int val) {
      TreeNode node = new TreeNode(val);
      map.put(++size, node);
      TreeNode parent = map.get(size / 2);
      if (size % 2 == 0) {
        parent.left = node;
      } else {
        parent.right = node;
      }
      return parent.val;
    }

    public TreeNode get_root() {
      return map.get(1);
    }
  }
}
