import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

  /**
   * Leetcode 659 : Split Array into Consecutive Subsequences.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return boolean
   */
  public boolean isPossible(int[] nums) {
    if (nums.length < 3) {
      return false;
    }
    Map<Integer, Integer> countMap = new HashMap<>();
    Map<Integer, Integer> endMap = new HashMap<>();
    for (int i : nums) {
      countMap.put(i, countMap.getOrDefault(i, 0) + 1);
    }
    for (int i : nums) {
      int count = countMap.getOrDefault(i, 0);
      if (count > 0) {
        int endWith = endMap.getOrDefault(i - 1, 0);
        if (endWith > 0) {
          endMap.put(i - 1, endWith - 1);
          endMap.put(i, endMap.getOrDefault(i, 0) + 1);
          countMap.put(i, count - 1);
        } else {
          int count1 = countMap.getOrDefault(i + 1, 0);
          int count2 = countMap.getOrDefault(i + 2, 0);
          if (count1 > 0 && count2 > 0) {
            countMap.put(i, count - 1);
            countMap.put(i + 1, count1 - 1);
            countMap.put(i + 2, count2 - 1);
            endMap.put(i + 2, endMap.getOrDefault(i + 2 ,0) + 1);
          } else {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Leetcode 767 : Reorganize String.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m + 26) & O(26)
   * @param s String
   * @return String
   */
  public String reorganizeString(String s) {
    int[] counts = new int[26];
    for (char c : s.toCharArray()) {
      counts[c - 'a']++;
    }
    int max = Integer.MIN_VALUE;
    int index = -1;
    for (int i = 0; i < 26; i++) {
      if (counts[i] > (s.length() + 1) / 2) return "";
      if (counts[i] > max) {
        max = counts[i];
        index = i;
      }
    }
    char[] chars = new char[s.length()];
    int cur = -2;
    while (counts[index] > 0) {
      cur += 2;
      chars[cur] = (char) ('a' + index);
      counts[index]--;
    }
    for (int i = 0; i < 26; i++) {
      while (counts[i] > 0) {
        cur += 2;
        if (cur >= s.length()) cur = 1;
        chars[cur] = (char) ('a' + i);
        counts[i]--;
      }
    }
    return String.valueOf(chars);
  }

  /**
   * Leetcode 948 : Bag of Tokens.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(1)
   * @param tokens int[]
   * @param power int
   * @return int
   */
  public int bagOfTokensScore(int[] tokens, int power) {
    Arrays.sort(tokens);
    int currP = power;
    int ans = 0;
    int i = 0;
    int maxP = tokens.length - 1;
    while (i <= maxP) {
      if (currP >= tokens[i]) {
        ans++;
        currP -= tokens[i];
        i++;
      } else {
        if (ans >= 1) {
          currP += tokens[maxP] - tokens[i];
          maxP--;
          i++;
        } else {
          return ans;
        }
      }
    }
    return ans;
  }
}
