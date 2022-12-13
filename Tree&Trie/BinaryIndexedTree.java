import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a collection for leetcode problems related to binary indexed tree.
 */
public class BinaryIndexedTree {

  /**
   * Leetcode 493 : Reverse Pairs.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param nums int[]
   * @return int
   */
  public int reversePairs(int[] nums) {
    Set<Long> set = new TreeSet<>();
    for (int num : nums) {
      set.add((long) num);
      set.add((long) num * 2);
    }
    Map<Long, Integer> map = new HashMap<>();
    int idx = 0;
    for (Long val : set) {
      map.put(val, idx++);
    }
    int[] tree = new int[map.size() + 1];
    int res = 0;
    for (int num : nums) {
      int l = map.get((long) num * 2);
      int r = map.size() - 1;
      res += query(tree, r + 1) - query(tree, l + 1);
      update(tree, map.get((long) num) + 1, 1);
    }
    return res;
  }

  /**
   * low bit with 1.
   * @param x int
   * @return int
   */
  public int lowbit(int x) {
    return x & -x;
  }

  /**
   * query sum.
   * @param tree int[]
   * @param x int
   * @return int
   */
  public int query(int[] tree, int x) {
    int ans = 0;
    while (x != 0) {
      ans += tree[x];
      x -= lowbit(x);
    }
    return ans;
  }

  /**
   * update val.
   * @param tree int[]
   * @param x int
   * @param d int
   */
  public void update(int[] tree, int x, int d) {
    while (x < tree.length) {
      tree[x] += d;
      x += lowbit(x);
    }
  }

  /**
   * Leetcode 327 : Count of Range Sum.
   * @Difficulty: Hard
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param nums int[]
   * @param lower int
   * @param upper int
   * @return int
   */
  public int countRangeSum(int[] nums, int lower, int upper) {
    long sum = 0;
    long[] preSum = new long[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      preSum[i + 1] = sum;
    }
    Set<Long> set = new TreeSet<>();
    for (long num : preSum) {
      set.add(num);
      set.add(num - upper);
      set.add(num - lower);
    }
    int idx = 0;
    Map<Long, Integer> map = new HashMap<>();
    for (Long num : set) {
      map.put(num, idx++);
    }
    int res = 0;
    int[] tree = new int[map.size() + 1];
    for (long value : preSum) {
      int l = map.get(value - upper);
      int r = map.get(value - lower);
      res += query(tree, r + 1) - query(tree, l);
      update(tree, map.get(value) + 1, 1);
    }
    return res;
  }

  /**
   * Leetcode 775 : lobal and Local Inversions.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param nums int[]
   * @return boolean
   */
  public boolean isIdealPermutation(int[] nums) {
    int local = 0;
    for (int i = 0; i < nums.length - 1; i ++ ) {
      if (nums[i] > nums[i + 1]) {
        local++;
      }
    }
    int global = 0;
    Set<Integer> set = new TreeSet<>();
    for (int num : nums) {
      set.add(num);
    }
    int idx = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : set) {
      map.put(num, idx++);
    }
    int[] tree = new int[map.size() + 1];
    for (int i = 0; i < nums.length; i++) {
      int l = map.get(nums[i]);
      int r = map.get(map.size() - 1);
      global += query(tree, r + 1) - query(tree, l + 1);
      update(tree, map.get(nums[i]) + 1, 1);
    }
    return global == local;
  }
}
