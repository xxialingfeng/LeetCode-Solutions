/**.
 * A collection for leetcode problems related to Greedy
 */
public class Greedy {

  /**.
   * Leetcode 517: Super Washing Machines
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(1)
   * @param machines int[]
   * @return min moves to balance the array
   */
  public int findMinMoves(int[] machines) {
    //For washing machine i，if we want to balance the left and right side，we need to sum of l + r moves.
    //so we can get the answer by iterating over the array and find the max(l + r)
    int n = machines.length;
    int sum = 0;
    for (int machine : machines) {
      sum += machine;
    }
    if (sum % n != 0) {
      return -1;
    }
    int avg = sum / n;
    int left = 0;
    int right = sum;
    int res = 0;
    for (int i = 0; i < n; i++) {
      right -= machines[i];
      int l = Math.max(0, avg * i - left);
      int r = Math.max(0, avg * (n - i - 1) - right);
      res = Math.max(res, l + r);
      left += machines[i];
    }
    return res;
  }
}
