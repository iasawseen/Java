import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ivan on 22.02.17.
 * @author Ivan Sosin
 */

public class TrieImplementationTest {

    @Test
    public void testContains() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("foo");
        assertTrue(trie.contains("foo"));
        assertFalse(trie.contains("fo"));
        assertFalse(trie.contains("bar"));
        assertTrue(trie.contains(""));
    }

    @Test
    public void testAdd() {
        TrieImplementation trie = new TrieImplementation();

        assertTrue(trie.add("foo"));
        assertFalse(trie.add("foo"));
        assertTrue(trie.add("foot"));
        assertTrue(trie.contains("foo"));
        assertTrue(trie.contains("foot"));
        assertFalse(trie.contains("fo"));
        assertFalse(trie.contains("bar"));
    }

    @Test
    public void testSize() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("foo");
        trie.add("foot");
        assertTrue(trie.contains("foo"));
        assertTrue(trie.contains("foot"));
        assertFalse(trie.contains("bar"));
        assertTrue(trie.size() == 2);
        trie.add("foo");
        assertTrue(trie.size() == 2);
        trie.add("big");
        assertTrue(trie.size() == 3);
    }

    @Test
    public void testMultiplePrefixes() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        trie.add("howdy");
        trie.add("hello");
        trie.add("holiday");
        trie.add("however");
        trie.add("hell");
        assertTrue(trie.howManyStartsWithPrefix("h") == 6);
        assertTrue(trie.howManyStartsWithPrefix("hel") == 2);
        assertTrue(trie.howManyStartsWithPrefix("ho") == 3);
        assertTrue(trie.howManyStartsWithPrefix("hol") == 1);
        assertTrue(trie.howManyStartsWithPrefix("") == trie.size());
    }

    @Test
    public void testRemove() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        trie.add("howdy");
        trie.add("hello");
        trie.remove("hi");
        assertTrue(trie.size() == 2);
        assertFalse(trie.contains("hi"));
        assertTrue(trie.contains("howdy"));
        assertTrue(trie.contains("hello"));
        assertTrue(trie.howManyStartsWithPrefix("h") == 2);

    }

    @Test
    public void testAll1() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        trie.contains("hi");
        assertTrue(trie.size() == 1);
        trie.remove("hi");
        assertTrue(trie.size() == 0);
        assertFalse(trie.contains("hi"));
    }

    @Test
    public void testAll2() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        trie.contains("hi");
        trie.add("hill");
        assertTrue(trie.howManyStartsWithPrefix("h") == 2);
        trie.add("histon");
        assertTrue(trie.howManyStartsWithPrefix("h") == 3);
        assertTrue(trie.howManyStartsWithPrefix("hi") == 3);
        assertTrue(trie.remove("hi"));
        assertTrue(trie.size() == 2);
        assertTrue(trie.howManyStartsWithPrefix("hi") == 2);
        assertTrue(trie.howManyStartsWithPrefix("his") == 1);
    }

    @Test
    public void testDoubleAdd() {
        TrieImplementation trie = new TrieImplementation();

        assertTrue(trie.add("hi"));
        assertFalse(trie.add("hi"));
        assertTrue(trie.size() == 1);
        assertTrue(trie.howManyStartsWithPrefix("h") == 1);
    }

    @Test
    public void testDoubleDelete() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        assertTrue(trie.remove("hi"));
        assertFalse(trie.remove("hi"));
        assertTrue(trie.size() == 0);
        assertTrue(trie.howManyStartsWithPrefix("h") == 0);
    }

    @Test
    public void testPrefixAfterDelete() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("hi");
        trie.add("hill");
        trie.remove("hill");
        assertTrue(trie.howManyStartsWithPrefix("hi") == 1);
        assertTrue(trie.contains("hi"));
    }

    @Test
    public void testUpperVsLow() {
        TrieImplementation trie = new TrieImplementation();

        trie.add("Hi");
        trie.add("hi");
        assertTrue(trie.size() == 2);
        assertTrue(trie.contains("Hi"));
        assertTrue(trie.remove("hi"));
        assertTrue(trie.size() == 1);
        assertTrue(trie.add("hi"));
        assertTrue(trie.add("hill"));
        assertTrue(trie.add("How"));
        assertTrue(trie.howManyStartsWithPrefix("h") == 2);
        assertTrue(trie.size() == 4);
        assertTrue(trie.add("hIlL"));
        assertFalse(trie.remove("Howdy"));
        assertTrue(trie.size() == 5);
    }
}
