import java.util.List;

/**
 * A collection of leetcode problems related to TrieNode
 */
public class TrieNode {
  static class Trie {
    boolean isWord;
    Trie[] next = new Trie[26];
  }

  Trie root = new Trie();

  /**
   * Leetcode 648 : Replace Words.
   * @Difficulty: Medium
   * @OptimalComplexity: O(n) & O(n)
   * @param dictionary list of string
   * @param sentence string
   * @return string
   */
  public String replaceWords(List<String> dictionary, String sentence) {
    StringBuilder sb = new StringBuilder();
    String[] s = sentence.split(" ");
    add(dictionary);
    for (String value : s) {
      int idx = search(value);
      String temp = idx == 0 ? value : value.substring(0, idx);
      sb.append(temp);
      sb.append(" ");
    }
    return sb.toString().trim();
  }

  /**
   * Add elements to Trie.
   * @param dictionary list of string
   */
  public void add(List<String> dictionary) {
    for (String str : dictionary) {
      Trie node = root;
      for (char c : str.toCharArray()) {
        if (node.next[c - 'a'] == null) {
          node.next[c - 'a'] = new Trie();
        }
        node = node.next[c - 'a'];
      }
      node.isWord = true;
    }
  }

  /**
   * Search the index of the word.
   * @param s String
   * @return int
   */
  public int search(String s) {
    Trie node = root;
    int idx = 0;
    for (char c : s.toCharArray()) {
      if (node.next[c - 'a'] == null && !node.isWord) {
        return 0;
      }
      if (node.isWord) {
        return idx;
      }
      idx++;
      node = node.next[c - 'a'];
    }
    return idx;
  }

  class WordFilter {
    Trie745 root;

    /**
     * Leetcode 745 : Prefix and Suffix Search.
     * @Difficulty: Hard
     * @OptimalComplexity: O(n * m) & O(n * m)
     * @param words list of string
     */
    public WordFilter(String[] words) {
      root = new Trie745();
      int idx = 0;
      for (String str : words) {
        for (int i = 0; i < str.length(); i++) {
          insert(str.substring(i, str.length()) + "{" + str, idx);
        }
        idx++;
      }
    }

    public int f(String pref, String suff) {
      return search(suff + "{" + pref);
    }

    public void insert(String str, int idx) {
      Trie745 curr = root;
      for (int i = 0; i < str.length(); i++) {
        int temp = str.charAt(i) - 'a';
        if (curr.next[temp] == null) {
          curr.next[temp] = new Trie745();
        }
        curr = curr.next[temp];
        curr.idx = idx;
      }
      curr.isWord = true;
    }

    public int search(String str) {
      Trie745 curr = root;
      for (int i = 0; i < str.length(); i++) {
        int temp = str.charAt(i) - 'a';
        if (curr.next[temp] == null) {
          return -1;
        }
        curr = curr.next[temp];
      }
      return curr.idx;
    }
  }

  class Trie745 {
    boolean isWord;
    Trie745[] next;
    int idx;

    Trie745() {
      idx = -1;
      next = new Trie745[27];
      isWord = false;
    }
  }

}
