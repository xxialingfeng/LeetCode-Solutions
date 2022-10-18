import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A collection for leetcode problems related to BFS.
 */
public class BFS {

  /**
   * Leetcode 542: 01 Matrix.
   * @Difficulty: Medium
   * @OptimalComplexity: O(mn) & O(mn)
   * @param mat int[][]
   * @return int[][]
   */
  public int[][] updateMatrix(int[][] mat) {
    //A good problem! Worth doing it again!
    int[][] vector = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m = mat.length;
    int n = mat[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (mat[i][j] == 0) {
          queue.offer(new int[]{i, j});
          visited[i][j] = true;
        } else {
          mat[i][j] = m + n;
        }
      }
    }
    while (!queue.isEmpty()) {
      int[] s = queue.poll();
      for (int[] v : vector) {
        int r = s[0] + v[0];
        int c = s[1] + v[1];
        if (r >= 0 && r < m && c >= 0 && c < n && !visited[r][c]) {
          mat[r][c] = mat[s[0]][s[1]] + 1;
          queue.offer(new int[]{r, c});
          visited[r][c] = true;
        }
      }
    }
    return mat;
  }

  List<Double> list637 = new ArrayList<>();

  /**
   * Leetcode 637 : Average of Levels in Binary Tree.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return list of doubles
   */
  public List<Double> averageOfLevels(TreeNode root) {
    if (root == null) {
      return list637;
    }
    if (root.left == null && root.right == null) {
      list637.add(root.val * 1.0);
      return list637;
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      int temp = size;
      double sum = 0;
      while (temp > 0) {
        TreeNode node = queue.poll();
        sum += node.val;
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
        temp--;
      }
      list637.add(sum / size);
    }
    return list637;
  }

  private int maxW = 1;

  /**
   * Leetcode 662 : Maximum Width of Binary Tree.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param root TreeNode
   * @return int
   */
  public int widthOfBinaryTree(TreeNode root) {
    if (root == null) {
      return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    LinkedList<Integer> list = new LinkedList<>();
    list.add(1);
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        TreeNode node = queue.poll();
        int index = list.removeFirst();
        assert node != null;
        if (node.left != null) {
          queue.offer(node.left);
          list.add(2 * index);
        }
        if (node.right != null) {
          queue.offer(node.right);
          list.add(2 * index + 1);
        }
      }
      if (list.size() >= 2) {
        maxW = Math.max(maxW, list.getLast() - list.getFirst() + 1);
      }
    }
    return maxW;
  }
}
