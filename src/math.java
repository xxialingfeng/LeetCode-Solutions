/**.
 * A collection of leetcode problems related to math
 */
public class math {
  /**.
  * Leetcode 507: perfect number
  * @Difficulty: Easy
  * @OptimalComplexity: O(D)&O(1)
  */
  public boolean checkPerfectNumber(int num) {
    double mid = Math.sqrt(num);
    if (num < 4) {
      return false;
    }
    int sum = 1;
    for (int i = 2; i <= (int)mid; i++) {
      if (num % i != 0) {
        continue;
      }
      sum += i;
      sum += (num / i);
    }
    return sum == num;
  }

  /**
   * Leetcode 672 : Bulb Switcher II.
   * @param n int
   * @param presses int
   * @return int
   */
  public int flipLights(int n, int presses) {
    if (presses == 0) return 1;
    if (n == 1) return 2;
    if (n == 2) return (presses == 1) ? 3 : 4;
    return (presses > 2) ? 8 : (presses == 1) ? 4 : 7;
  }
}
