import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ivan on 07.03.17.
 * @author Ivan Sosin
 */

public class DictionaryImplTest {

    @Test
    public void testContains() {
        DictionaryImpl dict = new DictionaryImpl();

        assertFalse(dict.contains("abc"));
        dict.put("abc", "hey");
        assertTrue(dict.contains("abc"));
        dict.put("cd", "wow");
        assertTrue(dict.contains("cd"));

        DictionaryImpl dictTwo = new DictionaryImpl(2);
        dictTwo.put("abc", "how");
        dictTwo.put("ce", "dur");
        dictTwo.put("fe", "bar");
        assertTrue(dictTwo.contains("ce"));

        dictTwo.remove("abc");
        assertFalse(dictTwo.contains("abc"));
        dictTwo.put("abc", "wow");
        assertTrue(dictTwo.contains("abc"));
    }

    @Test
    public void testGet() {
        DictionaryImpl dict = new DictionaryImpl();

        dict.put("abc", "hey");
        dict.put("cd", "wow");
        assertEquals("hey", dict.get("abc"));
        assertEquals("wow", dict.get("cd"));
        assertNull(dict.get("bar"));
        dict.remove("abc");
        assertNull(dict.get("abc"));
    }

    @Test
    public void testPut() {
        DictionaryImpl dict = new DictionaryImpl();

        assertEquals(0, dict.size());
        assertNull(dict.put("abc", "hey"));
        assertEquals(1, dict.size());
        assertEquals("hey", dict.put("abc", "ai"));
        assertEquals(1, dict.size());
        assertEquals("ai", dict.get("abc"));

        assertNull(dict.put("gh", "cvag"));
        assertEquals(2, dict.size());
        assertNull(dict.put("ge", "uewg"));
        assertEquals(3, dict.size());
    }

    @Test
    public void testRemove() {
        DictionaryImpl dict = new DictionaryImpl();
        dict.put("a", "1");
        dict.put("b", "2");
        dict.put("c", "3");
        dict.put("d", "4");
        dict.put("e", "5");

        assertEquals(5, dict.size());
        assertNull(dict.remove("f"));
        assertEquals(5, dict.size());
        assertEquals("2", dict.remove("b"));
        assertEquals(4, dict.size());
        assertFalse(dict.contains("2"));

        dict.put("b", "2");

        assertTrue(dict.contains("b"));
        assertEquals(5, dict.size());
        assertEquals("1", dict.remove("a"));
        assertEquals("2", dict.remove("b"));
        assertEquals("3", dict.remove("c"));
        assertEquals("4", dict.remove("d"));
        assertEquals("5", dict.remove("e"));
        assertEquals(0, dict.size());
    }

    @Test
    public void testSize() {
        DictionaryImpl dict = new DictionaryImpl();

        assertEquals(0, dict.size());
        dict.put("a", "1");
        assertEquals(1, dict.size());
        dict.put("b", "2");
        assertEquals(2, dict.size());
        dict.put("c", "3");
        assertEquals(3, dict.size());
        dict.put("d", "4");
        assertEquals(4, dict.size());
        dict.put("e", "5");
        assertEquals(5, dict.size());

        dict.remove("e");
        assertEquals(4, dict.size());
        dict.put("f", "6");
        assertEquals(5, dict.size());
        assertEquals("4", dict.get("d"));
        assertEquals(5, dict.size());
        dict.clear();
        assertEquals(0, dict.size());

    }

    @Test
    public void testClear() {
        DictionaryImpl dict = new DictionaryImpl();
        dict.put("a", "1");
        dict.put("b", "2");
        dict.put("c", "3");

        assertEquals(3, dict.size());
        assertTrue(dict.contains("a"));
        assertTrue(dict.contains("b"));
        assertTrue(dict.contains("c"));

        dict.clear();

        assertEquals(0, dict.size());
        assertFalse(dict.contains("a"));
        assertFalse(dict.contains("b"));
        assertFalse(dict.contains("c"));
    }

    @Test
    public void testReHash() {
        DictionaryImpl dict = new DictionaryImpl(2, 3);
        dict.put("a", "1");
        dict.put("b", "2");
        dict.put("c", "3");

        assertEquals(2, dict.getTableSize());

        dict.put("d", "4");
        dict.put("e", "5");
        dict.put("f", "6");
        dict.put("g", "7");

        assertEquals(4, dict.getTableSize());

        dict.put("h", "8");
        dict.put("i", "9");
        dict.put("j", "10");
        dict.put("k", "11");
        dict.put("l", "12");
        dict.put("m", "13");
        dict.put("n", "14");
        dict.put("o", "15");
        dict.put("p", "16");
        dict.put("q", "17");

        assertEquals(8, dict.getTableSize());
    }
}