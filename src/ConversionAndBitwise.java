/**
 * A collection of leetcode problems related to Conversion and Bitwise
 */
public class ConversionAndBitwise {

  /**.
   * LeetCode 504: Base 7
   * @Difficulty: Easy
   * @OptimalComplexity: O(log(num)) & O(1)
   * @param num int
   * @return String
   */
  public String convertToBase7(int num) {
    StringBuilder sb = new StringBuilder();
    int temp = Math.abs(num);
    while (temp / 7 != 0) {
      sb.append(temp % 7);
      temp = temp / 7;
    }
    sb.append(temp % 7);
    String ans = "";
    String add = sb.reverse().toString();
    if (num < 0) {
      ans += "-";
    }
    return ans + add;
  }
}
