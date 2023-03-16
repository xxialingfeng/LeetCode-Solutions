import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * A collection of leetcode problems related to Sorting.
 */
public class Sorting {

  /**
   * Leetcode 31 : Next Permutation.
   * @Difficulty: Medium
   * @OptimalComplexity: O(N) & O(1)
   * @param nums int[]
   */
  public void nextPermutation(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    if (i >= 0) {
      int j = nums.length - 1;
      while (j >= 0 && nums[i] >= nums[j]) {
        j--;
      }
      swap(nums, i, j);
    }
    reverse(nums, i + 1);
  }

  /**
   * Swap elements.
   * @param crr int[]
   * @param i int
   * @param j int
   */
  public void swap(int[] crr, int i, int j) {
    int temp = crr[i];
    crr[i] = crr[j];
    crr[j] = temp;
  }

  /**
   * swap elements int char[].
   * @param nums char[]
   * @param i int
   * @param j int
   */
  public void swap(char[] nums, int i, int j) {
    char temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /**
   * Reverse int[].
   * @param arr int[]
   * @param left int
   */
  public void reverse(int[] arr, int left) {
    int i = left;
    int j = arr.length - 1;
    while (i < j) {
      swap(arr, i, j);
      i++;
      j--;
    }
  }

  /**
   * Reverse char[].
   * @param nums char[]
   * @param begin int
   */
  public void reverse(char[] nums, int begin) {
    int i = begin;
    int j = nums.length - 1;
    while (i < j) {
      swap(nums, i, j);
      i++;
      j--;
    }
  }

  /**
   * Leetcode 556: Next Greater Element III.
   * @Difficulty: Medium
   * @OptimalComplexity: O(longn) & O(longn)
   * @param n int
   * @return int
   */
  public int nextGreaterElement(int n) {
    char[] nums = Integer.toString(n).toCharArray();
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    if (i < 0) {
      return -1;
    }

    int j = nums.length - 1;
    while (j >= 0 && nums[i] >= nums[j]) {
      j--;
    }
    swap(nums, i, j);
    reverse(nums, i + 1);
    long ans = Long.parseLong(new String(nums));
    return ans > Integer.MAX_VALUE ? -1 : (int) ans;
  }

  /**
   * Leetcode 851 : Loud and Rich.
   * @Difficulty: Medium
   * @OptimalComplexity: O(m * n) & O(m * n)
   * @param richer int[][]
   * @param quiet int[]
   * @return int[]
   */
  public int[] loudAndRich(int[][] richer, int[] quiet) {
    List<Integer>[] g = new List[quiet.length];
    for (int i = 0; i < quiet.length; i++) {
      g[i] = new ArrayList<>();
    }
    int[] inDeg = new int[quiet.length];
    for (int[] rich : richer) {
      int more = rich[0];
      int less = rich[1];
      g[more].add(less);
      inDeg[less]++;
    }
    int[] ans = new int[quiet.length];
    for (int i = 0; i < quiet.length; ++i) {
      ans[i] = i;
    }
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < inDeg.length; i++) {
      if (inDeg[i] == 0) {
        queue.offer(i);
      }
    }
    while (!queue.isEmpty()) {
      int x = queue.poll();
      for (int y : g[x]) {
        if (quiet[ans[y]] > quiet[ans[x]]) {
          ans[y] = ans[x];
        }
        inDeg[y]--;
        if (inDeg[y] == 0) {
          queue.offer(y);
        }
      }
    }
    return ans;
  }

  /**
   * QuickSort with random pivot.
   */
  class QuickSort {
    public int[] sortArray(int[] nums) {
      randomizedQuicksort(nums, 0, nums.length - 1);
      return nums;
    }

    private void swap(int[] nums, int i, int j) {
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }

    public void randomizedQuicksort(int[] nums, int l, int r) {
      if (l < r) {
        int pos = randomizedPartition(nums, l, r);
        randomizedQuicksort(nums, l, pos - 1);
        randomizedQuicksort(nums, pos + 1, r);
      }
    }

    public int randomizedPartition(int[] nums, int l, int r) {
      int i = new Random().nextInt(r - l + 1) + l;
      swap(nums, l, i);
      return partition(nums, l, r);
    }

    public int partition(int[] nums, int l, int r) {
      int pivot = nums[l];
      int key = l;
      while (l < r) {
        while (l < r && nums[r] >= pivot) {
          r--;
        }
        while (l < r && nums[l] <= pivot) {
          l++;
        }
        swap(nums, l, r);
      }
      swap(nums, l, key);
      return l;
    }
  }

  /**
   * Heap sort.
   */
  class HeapSort {
    public int[] sortArray(int[] nums) {
      heapSort(nums);
      return nums;
    }

    public void heapSort(int[] nums) {
      int len = nums.length - 1;
      buildMaxHeap(nums, len);
      for (int i = len; i >= 1; i--) {
        swap(nums, i, 0);
        len -= 1;
        maxHeapify(nums, 0, len);
      }
    }
    public void buildMaxHeap(int[] nums, int len) {
      //first non child node;
      for (int i = len / 2; i >= 0; i--) {
        maxHeapify(nums, i, len);
      }
    }

    public void maxHeapify(int[] nums, int i, int len) {
      for (; (i << 1) + 1 <= len; ) {
        int lson = (i << 1) + 1;
        int rson = (i << 1) + 2;
        int large;
        if (lson <= len && nums[lson] > nums[i]) {
          large = lson;
        } else {
          large = i;
        }
        if (rson <= len && nums[rson] > nums[large]) {
          large = rson;
        }
        if (large != i) {
          swap(nums, i, large);
          i = large;
        } else {
          break;
        }
      }
    }

    public void swap(int[] nums, int i, int j) {
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }
  }

  /**
   * MergeSort.
   */
  class MergeSort {
    int[] tmp;
    public int[] sortArray(int[] nums) {
      tmp = new int[nums.length];
      mergeSort(nums, 0, nums.length - 1);
      return nums;
    }

    public void mergeSort(int[] nums, int l, int r) {
      if (l >= r) {
        return;
      }
      int mid = (l + r) >> 1;
      mergeSort(nums, l, mid);
      mergeSort(nums, mid + 1, r);
      int i = l;
      int j = mid + 1;
      int cnt = 0;
      while (i <= mid && j <= r) {
        if (nums[i] <= nums[j]) {
          tmp[cnt++] = nums[i++];
        } else {
          tmp[cnt++] = nums[j++];
        }
      }
      while (i <= mid) {
        tmp[cnt++] = nums[i++];
      }
      while (j <= r) {
        tmp[cnt++] = nums[j++];
      }
      for (int k = 0; k < r - l + 1; k++) {
        nums[k + l] = tmp[k];
      }
    }

  }






}
