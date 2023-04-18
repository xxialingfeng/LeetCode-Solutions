import java.util.HashMap;
import java.util.Map;

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

  /**
   * Leetcode 1915 : Number of Wonderful Substrings.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n * 10) & Omin(n, 2^10)
   * @param word string
   * @return long
   */
  public long wonderfulSubstrings(String word) {
    int[] map = new int[1 << 10];
    long ret = 0;
    map[0] = 1;
    int cur = 0;
    for (char c : word.toCharArray()) {
      cur ^= (1 << c - 'a');
      for (int i = 0; i < 10; i++) {
        ret += map[cur ^ (1 << i)];
      }
      ret += map[cur];
      map[cur]++;
    }
    return ret;
  }

  /**
   * Leetcode 982 : Triples with Bitwise AND Equal To Zero.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n2) & O(n2)
   * @param nums int[]
   * @return int
   */
  public int countTriplets(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int k : nums) {
      for (int num : nums) {
        map.put(k & num, map.getOrDefault(k & num, 0) + 1);
      }
    }
    int res = 0;
    for (int and : map.keySet()) {
      for (int num : nums) {
        if ((and & num) == 0) {
          res += map.get(and);
        }
      }
    }
    return res;
  }

  /**
   * Leetcode 995 : Minimum Number of K Consecutive Bit Flips.'
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int minKBitFlips(int[] nums, int k) {
    int times = 0;
    int[] hint = new int[nums.length];
    int flip = 0;
    for (int i = 0; i < nums.length; i++) {
      flip ^= hint[i];
      if (nums[i] == flip) {
        times++;
        if (i + k > nums.length) {
          return -1;
        }
        flip ^= 1;
        if (i + k < nums.length) {
          hint[i + k] ^= 1;
        }
      }
    }
    return times;
  }
}
