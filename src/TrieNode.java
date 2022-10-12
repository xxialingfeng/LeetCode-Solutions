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
}
