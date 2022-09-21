import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection for leetcode problems related to strings
 */
public class Strings {

  /**.
   * Leetcode 520 : Detect Capital
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(1)
   * @param word String
   * @return boolean
   */
  public boolean detectCapitalUse(String word) {
    if (word.toUpperCase().equals(word) || word.toLowerCase().equals(word)) {
      return true;
    }
    for (int i = 1; i < word.length(); i++) {
      if (Character.isUpperCase(word.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**.
   * Leecode 521: longest uncommon subsequence I
   * @Difficulty: Easy
   * @OptimalComplexity: O(1) & O(1)
   */
  public int findLUSlength(String a, String b) {
    if (a.equals(b)) {
      return -1;
    }
    return Math.max(a.length(), b.length());
  }

  /**.
   * Leetcode 522: longest uncommon subsequence II
   * @Difficulty: Medium
   * @OptimalComplexity: O(n2) & O(1)
   * @param strs String[]
   * @return int
   */
  public int findLUSlength(String[] strs) {
    int res = -1;
    for (int i = 0; i < strs.length; i++) {
      boolean flag = false;
      for (int j = 0; j < strs.length; j++) {
        if (i == j) {
          continue;
        }
        flag = flag || isSub(strs[i], strs[j]);
      }
      if (!flag) {
        res = Math.max(res, strs[i].length());
      }
    }
    return res;
  }

  /**
   * Find out if string a is a subsequence of string b.
   * @param a String
   * @param b String
   * @return boolean
   */
  public boolean isSub(String a, String b) {
    int ps = 0;
    int pb = 0;
    while (ps < a.length() && pb < b.length()) {
      if (a.charAt(ps) == b.charAt(pb)) {
        ps++;
      }
      pb++;
    }
    return ps == a.length();
  }

  Map<String, String> map535 = new HashMap<>();
  static final String INDEX = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static final String PREFIX = "http://tinyurl.com/";
  /**
   * Encodes a URL to a shortened URL.
   * Leetcode 535: Encode and Decode TinyURL.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param longUrl String
   * @return String
   */
  public String encode(String longUrl) {
    char[] chr = new char[6];
    while (true) {
      for (int i = 0; i < 6; i++) {
        chr[i] = INDEX.charAt((int)(Math.random()*62));
        String shortUrl = PREFIX + new String(chr);
        if (!map535.containsKey(shortUrl)) {
          map535.put(shortUrl, longUrl);
          return shortUrl;
        }
      }
    }
  }

  /**Decodes a shortened URL to its original URL.
   * @param shortUrl String
   * @return String
   */
  public String decode(String shortUrl) {
    return map535.get(shortUrl);
  }

  /**
   * Leetcode 537: Complex Number Multiplication.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param num1 String
   * @param num2 String
   * @return String
   */
  public String complexNumberMultiply(String num1, String num2) {
    List<Character> list1 = new ArrayList<>();
    List<Character> list2 = new ArrayList<>();
    List<Character> list3 = new ArrayList<>();
    List<Character> list4 = new ArrayList<>();
    for (int i = 0; i < num1.length(); i++) {
      if (num1.charAt(i) == '+') {
        break;
      }
      list1.add(num1.charAt(i));
    }
    int left2 = 0;
    while (num1.charAt(left2) != '+') {
      left2++;
    }
    left2++;
    while (left2 < num1.length()-1) {
      list2.add(num1.charAt(left2));
      left2++;
    }
    for (int i = 0; i < num2.length(); i++) {
      if (num2.charAt(i) == '+') {
        break;
      }
      list3.add(num2.charAt(i));
    }
    int left4 = 0;
    while (num2.charAt(left4) != '+') {
      left4++;
    }
    left4++;
    while (left4 < num2.length()-1) {
      list4.add(num2.charAt(left4));
      left4++;
    }
    String ans = "";
    ans += (getNum(list1) * getNum(list3) - getNum(list2) * getNum(list4)) +  "+" + (getNum(list1) * getNum(list4) + getNum(list2) * getNum(list3)) + "i";
    return ans;
  }

  /**
   * get int value. (Integer.parseInt)
   * @param list List<Character>
   * @return int
   */
  public int getNum(List<Character> list) {
    int count = 1;
    int sum = 0;
    int left = 0;
    if (list.get(0) == '-') {
      count *= -1;
      left++;
    }
    for (int i = list.size() - 1; i >= left; i--) {
      sum += (list.get(i) - '0') * count;
      count *= 10;
    }
    return sum;
  }

  /**
   * Optimal Solution for leetcode 537.
   */
  public String complexNumberMultiplyOptimal(String num1, String num2) {
    int a1 = Integer.parseInt(num1.substring(0,num1.indexOf('+')));
    int a2 = Integer.parseInt(num1.substring(num1.indexOf('+') + 1,num1.length() - 1));
    int b1 = Integer.parseInt(num2.substring(0,num2.indexOf('+')));
    int b2 = Integer.parseInt(num2.substring(num2.indexOf('+') + 1,num2.length() - 1));

    int aa = a1 * b1 - a2 * b2;
    int bb = a2 * b1 + a1 * b2;

    return aa + "+" + bb + "i" ;
  }
}
