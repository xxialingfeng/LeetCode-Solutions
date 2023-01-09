import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * A collection for leetcode problems related to strings.
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
        chr[i] = INDEX.charAt((int)(Math.random() * 62));
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
    while (left2 < num1.length() - 1) {
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
    while (left4 < num2.length() - 1) {
      list4.add(num2.charAt(left4));
      left4++;
    }
    String ans = "";
    ans += (getNum(list1) * getNum(list3) - getNum(list2) * getNum(list4))
        +  "+" + (getNum(list1) * getNum(list4) + getNum(list2) * getNum(list3)) + "i";
    return ans;
  }

  /**
   * get int value. (Integer.parseInt)
   * @param list a list of characters
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

  /**
   * Leetcode 539 : Minimum Time Difference.
   * @Difficulty: Medium
   * @OptimalComplexity: O(nlogn) & O(n)
   * @param timePoints a list of string
   * @return int
   */
  public int findMinDifference(List<String> timePoints) {
    if (timePoints.size() > 1440) {
      return 0;
    }
    int min = Integer.MAX_VALUE;
    List<Integer> list = new ArrayList<>();
    for (String timePoint : timePoints) {
      if (timePoint.substring(0, 2).equals("00")) {
        list.add(getMin("24" + ":" + timePoint.substring(3, 5)));
      }
      if (timePoint.substring(0, 2).equals("01")) {
        list.add(getMin("25" + ":" + timePoint.substring(3, 5)));
      }
      list.add(getMin(timePoint));
    }
    list.sort((a, b) -> (a - b));
    for (int i = 1; i < list.size(); i++) {
      min = Math.min(min, list.get(i) - list.get(i - 1));
    }
    return min;
  }

  /**
   * Get the time in minute format.
   * @param str String
   * @return int
   */
  public int getMin(String str) {
    int sum = 0;
    sum += (str.charAt(0) - '0') * 10 * 60;
    sum += (str.charAt(1) - '0') * 60;
    sum += (str.charAt(3) - '0') * 10;
    sum += (str.charAt(4) - '0');
    return sum;
  }

  /**
   * Leetcode 541: Reverse String II.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param s String
   * @param k k
   * @return String
   */
  public String reverseStr(String s, int k) {
    StringBuilder sb = new StringBuilder();
    if (k - 1 >= s.length()) {
      return new StringBuffer(s).reverse().toString();
    }
    if (s.length() < 2 * k && s.length() >= k) {
      return new StringBuffer(s.substring(0,k)).reverse().toString() + s.substring(k, s.length());
    }
    sb.append(s.substring(0,k));
    return sb.reverse().toString() + s.substring(k, 2 * k) + reverseStr(s.substring(2 * k), k);
  }

  /**
   * Leetcode 551: Student Attendance Record I.
   * @param s String
   * @return boolean
   */
  public boolean checkRecord(String s) {
    int count = 0;
    int cnt = 1;
    if (s.charAt(0) == 'A') {
      count++;
    }
    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i) == 'A') {
        count++;
        if (count >= 2) {
          return false;
        }
      }
      if (s.charAt(i) == 'L' && s.charAt(i) == s.charAt(i - 1)) {
        cnt++;
        if (cnt >= 3) {
          return false;
        }
      } else {
        cnt = 1;
      }
    }
    return true;
  }

  /**
   * Leetcode 553: Optimal Division.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param nums int[]
   * @return String
   */
  public String optimalDivision(int[] nums) {
    StringBuilder arr = new StringBuilder();
    if (nums.length == 1) {
      return String.valueOf(nums[0]); //int->string
    } else if (nums.length == 2) {
      return nums[0] + "/" + nums[1];
    } else {
      for (int i = 0; i < nums.length; i++) {
        if (i == 1) {
          arr.append("(").append(nums[i]).append("/");
        } else if (i == nums.length - 1) {
          arr.append(nums[i]).append(")");
        } else {
          arr.append(nums[i]).append("/");
        }
      }
    }
    return arr.toString();
  }

  /**
   * Leetcode 557: Reverse Words in a String III.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n) & O(n)
   * @param s String
   * @return String
   */
  public String reverseWords(String s) {
    String[] col = s.split(" ");
    StringBuilder str = new StringBuilder();
    for (String value : col) {
      str.append(reverse(value)).append(" ");
    }
    return str.substring(0, str.length() - 1);
  }

  /**
   * Reverse string.
   * @param s string
   * @return String
   */
  public String reverse(String s) {
    return new StringBuffer(s).reverse().toString();
  }

  /**
   * Leetcode 564:Find the Closest Palindrome.
   * @Difficulty: Hard
   * @OptimalComplexity:
   * @param n O(n) & O(n)
   * @return String
   */
  public String nearestPalindromic(String n) {
    if (n.length() == 1) {
      return (Integer.parseInt(n) - 1) + "";
    }
    long org = Long.parseLong(n);
    long thousand = (long)(Math.pow(10, n.length() - 1));
    if (org == thousand || org == thousand + 1) {
      return String.valueOf(thousand - 1);
    }
    thousand = thousand * 10L - 1L;
    if (org == thousand) {
      return String.valueOf(thousand + 2L);
    }
    int left = Integer.parseInt(n.substring(0, (n.length() + 1)/2));
    long ans = Long.MAX_VALUE;
    long diff = Long.MAX_VALUE;
    for (int a : new int[]{-1, 0, 1}) {
      String lf = (left + a) + "";
      String full = new StringBuilder(n.length() % 2 == 0 ? lf : lf.substring(0, lf.length()-1)).reverse().insert(0, lf).toString();
      if (n.equals(full)) {
        continue;
      }
      long _full = Long.parseLong(full);
      if (Math.abs(_full - org) < diff) {
        diff = Math.abs(_full - org);
        ans = _full;
      }
    }
    return ans + "";
  }

  /**
   * Leetcode 591 : Tag Validator.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param code String
   * @return boolean
   */
  public boolean isValid(String code) {
    int n = code.length();
    Stack<String> stack = new Stack<>();

    int i = 0;
    while (i < n) {
      if (code.charAt(i) == '<') {
        if (i == n - 1) {
          return false;
        }
        if (code.charAt(i + 1) == '/') {
          int j = code.indexOf('>', i);
          if (j < 0) {
            return false;
          }
          String tagname = code.substring(i + 2, j);
          if (stack.isEmpty() || !stack.peek().equals(tagname)) {
            return false;
          }
          stack.pop();
          i = j + 1;
          if (stack.isEmpty() && i != n) {
            return false;
          }
        } else if (code.charAt(i + 1) == '!') {
          if (stack.isEmpty()) {
            return false;
          }
          if (i + 9 > n) {
            return false;
          }
          String cdata = code.substring(i + 2, i + 9);
          if (!"[CDATA[".equals(cdata)) {
            return false;
          }
          int j = code.indexOf("]]>", i);
          if (j < 0) {
            return false;
          }
          i = j + 3;
        } else {
          int j = code.indexOf('>' , i);
          if (j < 0) {
            return false;
          }
          String tagname = code.substring(i + 1, j);
          if (tagname.length() < 1 || tagname.length() > 9) {
            return false;
          }

          for (int k = 0; k < tagname.length(); k++) {
            if (!Character.isUpperCase(tagname.charAt(k))) {
              return false;
            }
          }
          stack.push(tagname);
          i = j + 1;
        }
      } else {
        if (stack.isEmpty()) {
          return false;
        }
        ++i;
      }
    }
    return stack.isEmpty();
  }

  /**
   * Leetcode 592: Fraction addition and subtraction.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param expression string
   * @return string
   */
  public String fractionAddition(String expression) {
    long x = 0;
    long y = 1;
    int sign = 1;
    int index = 0;
    while (index < expression.length()) {
      if (expression.charAt(index) == '-' || expression.charAt(index) == '+') {
        sign = expression.charAt(index) == '-' ? -1 : 1;
        index++;
      }
      long x1 = 0;
      while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
        x1 = x1 * 10 + expression.charAt(index) - '0';
        index++;
      }
      x1 = sign * x1;
      index++;
      long y1 = 0;
      while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
        y1 = y1 * 10 + expression.charAt(index) - '0';
        index++;
      }
      x = x * y1 + x1 * y;
      y *= y1;
    }
    if (x == 0) {
      return "0/1";
    }
    long g = gcd(Math.abs(x), y);
    return Long.toString(x / g) + "/" + Long.toString(y / g);
  }

  /**
   * Greatest common divisor.
   * @param a long
   * @param b long
   * @return long
   */
  public long gcd(long a, long b) {
    long remainder = a % b;
    while (remainder != 0) {
      a = b;
      b = remainder;
      remainder = a % b;
    }
    return b;
  }

  /**
   * Leetcode 609 : Find Duplicate File in System.
   * @param paths list of string
   * @Difficulty: Medium
   * @OptimalComplexity: O(n*l) & O(n*l)
   * @return list of list of string
   */
  public List<List<String>> findDuplicate(String[] paths) {
    Map<String, List<String>> map = new HashMap<>();
    for (String str : paths) {
      String[] files = str.split(" ");
      for (int i = 1; i < files.length; i++) {
        StringBuilder filePath = new StringBuilder();
        String content = files[i].substring(files[i].indexOf("("));
        files[i] = files[i].substring(0, files[i].indexOf("("));
        filePath.append(files[0]).append('/').append(files[i]);
        map.computeIfAbsent(content, k -> new ArrayList<>());
        List<String> tmp = map.get(content);
        tmp.add(filePath.toString());
      }
    }
    return map.values().stream().filter(item -> item.size() > 1).collect(Collectors.toList());
  }

  /**
   * LeetCode 640 : Solve the Equation.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param equation String
   * @return String
   */
  public String solveEquation(String equation) {
    // 把x移到左边，把其他移到右边
    String[] arr1 = equation.split("=");
    int left = 0;
    int right = 0;

    String[] arr2 = arr1[0].replace("-", "+-").split("\\+");
    String[] arr3 = arr1[1].replace("-", "+-").split("\\+");

    // 等式左边的处理
    for (String s : arr2) {
      if (s.equals("x")) {
        left += 1;
      } else if (s.equals("-x")) {
        left += -1;
      } else if (s.contains("x")) {
        left += Integer.parseInt(s.substring(0, s.length() - 1));
      } else if (!s.equals("")) {
        right -= Integer.parseInt(s);
      }
    }

    // 等式右边的处理
    for (String s : arr3) {
      if (s.equals("x")) {
        left -= 1;
      } else if (s.equals("-x")) {
        left -= -1;
      } else if (s.contains("x")) {
        left -= Integer.parseInt(s.substring(0, s.length() - 1));
      } else if (!s.equals("")) {
        right += Integer.parseInt(s);
      }
    }

    if (left == 0) {
      if (right == 0) {
        return "Infinite solutions";
      } else {
        return "No solution";
      }
    } else {
      return "x=" + right/left;
    }
  }

  /**
   * Leetcode 686 : Repeated String Match.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param a String
   * @param b String
   * @return int
   */
  public int repeatedStringMatch(String a, String b) {
    int count = 1;
    int max = 2 * a.length() + b.length();
    StringBuilder temp = new StringBuilder(a);
    while (temp.length() < max) {
      if (temp.toString().contains(b)) {
        return count;
      } else {
        temp.append(a);
        count++;
      }
    }
    return -1;
  }

  /**
   * Leetcode 720 : Longest Word in Dictionary.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n x m) & O(n)
   * @param words
   * @return
   */
  public String longestWord(String[] words) {
    Set<String> set = new HashSet<String>();
    for (String str : words) {
      set.add(str);
    }
    set.add("");
    Arrays.sort(words);
    String ans = "";
    for (int i = words.length - 1; i >= 0; i--) {
      boolean flag = true;
      String temp = words[i];
      for (int j = words[i].length() - 1; j >= 0; j--) {
        if (!set.contains(temp.substring(0, j))) {
          flag = false;
          break;
        }
      }
      if (flag) {
        if (temp.length() >= ans.length()) {
          ans = temp;
        }
      }
    }
    return ans;
  }

  /**
   * Leetcode 722 : Remove Comments.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m x n) & O(n)
   * @param source list of string
   * @return list of string
   */
  public List<String> removeComments(String[] source) {
    boolean inBlock = false;
    StringBuilder newline = new StringBuilder();
    List<String> ans = new ArrayList<>();
    for (String line: source) {
      int i = 0;
      char[] chars = line.toCharArray();
      if (!inBlock) newline = new StringBuilder();
      while (i < line.length()) {
        if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i+1] == '*') {
          inBlock = true;
          i++;
        } else if (inBlock && i + 1 < line.length() && chars[i] == '*' && chars[i+1] == '/') {
          inBlock = false;
          i++;
        } else if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i+1] == '/') {
          break;
        } else if (!inBlock) {
          newline.append(chars[i]);
        }
        i++;
      }
      if (!inBlock && newline.length() > 0) {
        ans.add(new String(newline));
      }
    }
    return ans;
  }

  /**
   * Leetcode 748 : Shortest Completing Word.
   * @Difficulty: Easy
   * @OptimalComplexity: O(m * n) & O(26)
   * @param licensePlate String
   * @param words list of string
   * @return string
   */
  public String shortestCompletingWord(String licensePlate, String[] words) {
    char[] tar = new char[26];
    licensePlate = licensePlate.toLowerCase();
    for (int i = 0; i < licensePlate.length(); i++) {
      if (Character.isLetter(licensePlate.charAt(i))) {
        tar[licensePlate.charAt(i) - 'a']++;
      }
    }
    String ans = "xxxxxxxxxxxxxxxxxxxxx";
    for (String str : words) {
      char[] src = new char[26];
      for (int i = 0; i < str.length(); i++) {
        src[str.charAt(i) - 'a']++;
      }
      if (isMore(tar, src)) {
        if (str.length() < ans.length()) {
          ans = str;
        }
      }
    }
    return ans;
  }

  /**
   * char[] b has more elements than any element in char[] a
   * @param a char[]
   * @param b char[]
   * @return boolean
   */
  public boolean isMore(char[] a, char[] b) {
    for (int i = 0; i < a.length; i++) {
      if (a[i] > b[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 761 : Special Binary String.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n) & O(n)
   * @param s String
   * @return String
   */
  public String makeLargestSpecial(String s) {
    int last = 0;
    int count = 0;
    List<String> list = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '1') {
        count++;
      } else {
        count--;
      }
      if (count == 0) {
        list.add("1" + makeLargestSpecial(s.substring(last + 1, i)) + "0");
        last = i + 1;
      }
    }
    list.sort(Comparator.reverseOrder());
    StringBuilder res = new StringBuilder();
    for (String str : list) {
      res.append(str);
    }
    return res.toString();
  }

  /**
   * Leetcode 777 : Swap Adjacent in LR String.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param start String
   * @param end String
   * @return boolean
   */
  public boolean canTransform(String start, String end) {
    int i = 0;
    int j = 0;
    int n = start.length();
    while (i < n && j < n) {
      while (i < n && start.charAt(i) == 'X') {
        i++;
      }
      while (j < n && end.charAt(j) == 'X') {
        j++;
      }
      if (i < n && j < n) {
        if (start.charAt(i) != end.charAt(j)) {
          return false;
        }
        char c = start.charAt(i);
        if ((c == 'L' && i < j) || (c == 'R' && i > j)) {
          return false;
        }
        i++;
        j++;
      }
    }
    while (i < n) {
      if (start.charAt(i) != 'X') {
        return false;
      }
      i++;
    }
    while (j < n) {
      if (end.charAt(j) != 'X') {
        return false;
      }
      j++;
    }
    return true;
  }

  /**
   * Leetcode 791 : Custom Sort String.
   * @Difficulty: Medium.
   * @OptimalComplexity: O(n) & O(n)
   * @param order String
   * @param s String
   * @return String
   */
  public String customSortString(String order, String s) {
    int[] store = new int[26];
    for (int i = 0; i < s.length(); i++) {
      store[s.charAt(i) - 'a']++;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < order.length(); i++) {
      int temp = store[order.charAt(i) - 'a'];
      if (temp == 0) {
        continue;
      }
      while (temp != 0) {
        sb.append(order.charAt(i));
        temp--;
      }
    }
    for (int i = 0; i < store.length; i++) {
      while (store[i] != 0) {
        char toAdd = (char)('a' + i);
        sb.append(toAdd);
        store[i]--;
      }
    }
    return sb.toString();
  }
}
