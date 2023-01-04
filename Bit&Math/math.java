import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**.
 * A collection of leetcode problems related to math
 */
public class
math {
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

  static final int TARGET = 24;
  static final double EPSILON = 1e-6;
  static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

  public boolean judgePoint24(int[] nums) {
    List<Double> list = new ArrayList<Double>();
    for (int num : nums) {
      list.add((double) num);
    }
    return solve(list);
  }

  /**
   * Leetcode 679 : 4 Game.
   * @Difficulty: Hard
   * @OptimalComplexity: O(1) & O(1)
   * @param list list of double
   * @return boolean
   */
  public boolean solve(List<Double> list) {
    if (list.size() == 0) {
      return false;
    }
    if (list.size() == 1) {
      return Math.abs(list.get(0) - TARGET) < EPSILON;
    }
    int size = list.size();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i != j) {
          List<Double> list2 = new ArrayList<Double>();
          for (int k = 0; k < size; k++) {
            if (k != i && k != j) {
              list2.add(list.get(k));
            }
          }
          for (int k = 0; k < 4; k++) {
            if (k < 2 && i > j) {
              continue;
            }
            if (k == ADD) {
              list2.add(list.get(i) + list.get(j));
            } else if (k == MULTIPLY) {
              list2.add(list.get(i) * list.get(j));
            } else if (k == SUBTRACT) {
              list2.add(list.get(i) - list.get(j));
            } else {
              if (Math.abs(list.get(j)) < EPSILON) {
                continue;
              } else {
                list2.add(list.get(i) / list.get(j));
              }
            }
            if (solve(list2)) {
              return true;
            }
            list2.remove(list2.size() - 1);
          }
        }
      }
    }
    return false;
  }

  List<Integer> list = new ArrayList<>();

  /**
   * Leetcode 728 : Self Dividing Numbers.
   * @Difficulty: Easy
   * @OptimalComplexity: O(n * m) & O(n)
   * @param left int
   * @param right right
   * @return list of integer
   */
  public List<Integer> selfDividingNumbers(int left, int right) {
    for (int i = left; i <= right; i++) {
      if (isValid(i)) {
        list.add(i);
      }
    }
    return list;
  }

  public boolean isValid(int n) {
    int s = n;
    int least = n % 10;
    int last = n;
    while (n > 0) {
      if (least == 0  || (s % least) != 0) {
        return false;
      }
      n /= 10;
      least = n % 10;
    }
    return true;
  }

  /**
   * Leetcode 775 : Global and Local Inversions.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param nums
   * @return
   */
  public boolean isIdealPermutation(int[] nums) {
    int local = 0;
    for (int i = 0; i < nums.length; i ++ ) {
      if (Math.abs(nums[i] - i) > 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Leetcode 780 : Reaching Points.
   * @Difficulty: Hard
   * @OptimalComplexity: O(logn) & O(1)
   * @param sx int
   * @param sy int
   * @param tx int
   * @param ty int
   * @return boolean
   */
  public boolean reachingPoints(int sx, int sy, int tx, int ty) {
    while (tx >= sx && ty >= sy) {
      if (tx == sx && ty == sy) {
        return true;
      }
      if (tx < ty) {
        ty -= Math.max((ty - sy) / tx, 1) * tx;
      } else {
        tx -= Math.max((tx - sx) / ty, 1) * ty;
      }
    }
    return false;
  }

  /**
   * Leetcode 781 : Rabbits in Forest.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(1)
   * @param answers int[]
   * @return int
   */
  public int numRabbits(int[] answers) {
    Map<Integer,Integer> map = new HashMap<>();
    int num = 0;
    for (int answer : answers) {
      if (map.containsKey(answer) && map.get(answer) > 0) {
        map.put(answer,map.get(answer) - 1);
      } else {
        num += answer + 1;
        map.put(answer,answer);
      }
    }
    return num;
  }
}
