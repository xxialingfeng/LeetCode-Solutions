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

  /**
   * Leetcode 868 : Binary Gap.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param n int
   * @return int
   */
  public int binaryGap(int n) {
    int ans = 0;
    int count = 0;
    int pre = -1;
    int curr = -1;
    while (n != 0) {
      int bit = n % 2;
      if (bit == 1) {
        if (pre == -1) {
          pre = count;
        } else {
          curr = count;
          int dis = curr - pre;
          if (dis > ans) {
            ans = dis;
          }
          pre = curr;
        }
      }
      count++;
      n /= 2;
    }
    return ans;
  }
}
