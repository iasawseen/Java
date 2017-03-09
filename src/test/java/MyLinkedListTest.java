import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Created by ivan on 03.03.17.
 * @author Ivan Sosin
 */

public class MyLinkedListTest {
    @Test
    public void testContains() {
        MyLinkedList list = new MyLinkedList();
        list.put("abc", "howdy");

        assertTrue(list.contains("abc"));
        assertFalse(list.contains("ge"));

        list.put("abc", "hi");
        assertEquals(1, list.getSize());
        assertTrue(list.contains("abc"));

        list.put("bed", "hyper");
        assertTrue(list.contains("bed"));

        list.remove("abc");
        assertFalse(list.contains("abc"));
        assertEquals(1, list.getSize());
    }

    @Test
    public void testGet() {
        MyLinkedList list = new MyLinkedList();
        list.put("abc", "howdy");

        String value = list.get("abc");
        assertTrue(value.equals("howdy"));
        assertNotNull(value);

        String value1 = list.get("cd");
        assertNull(value1);

        list.put("cd", "sidekick");
        list.put("gh", "blablabla");

        String value2 = list.get("abc");
        assertTrue(value2.equals("howdy"));

        String value3 = list.get("cd");
        assertTrue(value3.equals("sidekick"));

        list.remove("abc");
        String value4 = list.get("abc");
        assertNull(value4);
    }

    @Test
    public void testRemove() {
        MyLinkedList list = new MyLinkedList();
        String value1 = list.remove("hi");
        assertNull(value1);
        assertEquals(0, list.getSize());

        list.put("abc", "howdy");
        assertEquals( 1, list.getSize());
        String value2 = list.remove("abc");
        assertEquals(value2, "howdy");
        assertEquals(0, list.getSize());

        list.put("abc", "howdy");
        list.put("cd", "sidekick");
        list.put("gh", "blablabla");
        list.put("bi", "jeor");

        assertEquals(4, list.getSize());

        list.remove("abc");
        assertFalse(list.contains("abc"));
        assertEquals(3, list.getSize());

        list.remove("bi");
        assertFalse(list.contains("bi"));
        assertTrue(list.contains("cd"));
        assertTrue(list.contains("gh"));
        assertEquals(2, list.getSize());

        list.put("gt", "GCAG");
        list.remove("cd");
        assertTrue(list.contains("gt"));
    }

    @Test
    public void testPut() {
        MyLinkedList list = new MyLinkedList();

        list.put("abc", "howdy");
        assertTrue(list.contains("abc"));

        list.put("cd", "sidekick");
        assertTrue(list.contains("abc"));
        assertTrue(list.contains("cd"));

        list.put("gh", "blablabla");
        assertTrue(list.contains("abc"));
        assertTrue(list.contains("cd"));
        assertTrue(list.contains("gh"));

        list.put("bi", "jeor");
        assertTrue(list.contains("abc"));
        assertTrue(list.contains("cd"));
        assertTrue(list.contains("gh"));
        assertTrue(list.contains("bi"));

        list.remove("bi");
        assertFalse(list.contains("bi"));

        list.remove("abc");
        assertFalse(list.contains("abc"));
    }

    @Test
    public void testGetSize() {
        MyLinkedList list = new MyLinkedList();
        assertEquals(0, list.getSize());

        list.put("abc", "how");
        assertEquals(1, list.getSize());

        list.put("cd", "sid");
        assertEquals(2, list.getSize());

        list.put("gh", "blab");
        assertEquals(3, list.getSize());

        list.put("bi", "jeo");
        assertEquals(4, list.getSize());
        assertTrue(list.contains("bi"));

        list.remove("gh");
        assertTrue(list.contains("bi"));
        assertEquals(3, list.getSize());
        list.remove("bi");
        assertFalse(list.contains("bi"));
        assertEquals(2, list.getSize());
        list.remove("cd");
        assertEquals(1, list.getSize());
        list.remove("abc");
        assertEquals(0, list.getSize());
    }

    @Test
    public void testIterable() {
        MyLinkedList list = new MyLinkedList();

        list.put("1", "hi");
        list.put("2", "howdy");
        list.put("3", "hello");
        list.put("4", "wow");
        list.put("5", "gege");

        Iterator iterator = list.iterator();
        assertEquals("hi", list.get((String) iterator.next()));
        assertEquals("howdy", list.get((String) iterator.next()));
        assertEquals("hello", list.get((String) iterator.next()));
        assertEquals("wow", list.get((String) iterator.next()));
        assertEquals("gege", list.get((String) iterator.next()));
    }
}
