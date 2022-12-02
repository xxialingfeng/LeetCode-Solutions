import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A collection of leetcode problems related to binary search.
 */
public class BinarySearch {

  /**
   * Leetcode 658 : Find K Closest Elements.
   * @param arr int[]
   * @param k int
   * @param x int
   * @return list of integer
   */
  public List<Integer> findClosestElements(int[] arr, int k, int x) {
    List<Integer> list = new ArrayList<>();
    int idx = search(arr, x);
    int left = idx - 1;
    int right = idx + 1;
    list.add(arr[idx]);
    if (k == 1) {
      return list;
    }
    while (left >= 0 && right < arr.length) {
      if (x - arr[left] <= arr[right] - x) {
        list.add(arr[left]);
        left--;
        if (list.size() == k) {
          list.sort((a, b) -> (a - b));
          return list;
        }
      } else {
        list.add(arr[right]);
        right++;
        if (list.size() == k) {
          list.sort((a, b) -> (a - b));
          return list;
        }
      }
    }
    int n = list.size();
    if (left < 0) {
      for (int i = n + 1; i <= k; i++) {
        list.add(arr[right]);
        right++;
      }
    } else {
      for (int i = n + 1; i <= k; i++) {
        list.add(arr[left]);
        left--;
      }
    }
    list.sort((a, b) -> (a - b));
    return list;
  }

  /**
   * binary search.
   * @param arr int[]
   * @param x int
   * @return int
   */
  public int search(int[] arr, int x) {
    int n = arr.length;
    int l = 0;
    int r = n - 1;
    while (l < r) {
      int mid = l + r + 1 >> 1;
      if (arr[mid] <= x) l = mid;
      else r = mid - 1;
    }
    return r + 1 < n && Math.abs(arr[r + 1] - x) < Math.abs(arr[r] - x) ? r + 1 : r;
  }

  /**
   * Leetcode 719 : Find K-th Smallest Pair Distance.
   * @Difficulty: Hard
   * @OptimalComplexity: O(n x (logn + logD) & O(logn)
   * @param nums int[]
   * @param k int
   * @return int
   */
  public int smallestDistancePair(int[] nums, int k) {
    /*
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                list.add(Math.abs(nums[i] - nums[j]));
            }
        }
        list.sort((a, b) -> a - b);
        return list.get(k - 1);
    */
    Arrays.sort(nums);
    int left = 0; int right = nums[nums.length - 1] - nums[0];
    while (left <= right) {
      int mid = left + (right - left) / 2;
      int cnt = 0;
      for (int j = 0; j < nums.length; j++) {
        int i = 0;
        while (nums[j] - nums[i] > mid) {
          i++;
        }
        cnt += j - i;
      }
      if (cnt >= k) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }
}
