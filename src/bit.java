/**
 * This is a collection of leetcode problems related to bits.
 */
public class bit {

  /**
   * Leetcode 693 :Binary Number with Alternating Bits.
   * @Difficulty: Easy
   * @OptimalComplexity: O(1) & O(1)
   * @param n int
   * @return boolean
   */
  public boolean hasAlternatingBits(int n) {
    int a = n ^ (n >> 1);
    return (a & (a + 1)) == 0;
  }
}
