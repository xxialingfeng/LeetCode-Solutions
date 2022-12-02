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

  /**
   * Leetcode 762 : Prime Number of Set Bits in Binary Representation.
   * @Difficulty: EASY
   * @OptimalComplexity: O(n) & O(1)
   * @param left int
   * @param right int
   * @return
   */
  public int countPrimeSetBits(int left, int right) {
    int res = 0;
    for (int i = left; i <= right; i++) {
      if (isPrime(Integer.bitCount(i))) {
        res++;
      }
    }
    return res;
  }

  public boolean isPrime(int num) {
    if (num < 2) {
      return false;
    }
    if (num == 2) {
      return true;
    }
    for (int i = 2; i <= (int) Math.sqrt(num); i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}
