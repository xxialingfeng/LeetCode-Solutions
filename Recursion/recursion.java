/**
 * A collection of leetcode problems related to recursion.
 */
public class recursion {

  /**
   * Leetcode 1003 : Check If Word Is Valid After Substitutions.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param s string
   * @return boolean
   */
  public boolean isValid(String s) {
    if (s.equals("abc")) {
      return true;
    }
    if (!s.contains("abc")) {
      return false;
    }
    int idx = s.indexOf("abc");
    return isValid(s.substring(0, idx) + s.substring(idx + 3));
  }

}
