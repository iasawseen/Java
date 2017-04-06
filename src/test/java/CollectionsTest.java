import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by ivan on 06.04.17.
 */

public class CollectionsTest {
    private static Function2<Integer, Integer, Integer> MULT = (i1, i2) -> i1 * i2;
    private static Function2<Integer, Integer, Integer> SUB = (i1, i2) -> i1 - i2;
    private static Function1<Integer, String> INTEGER_TO_STRING = (i) -> Integer.toString(i);
    private static Predicate<Integer> LESS_THAN_FIVE = (i) -> i < 5;

    @Test
    public void testMap() {
        Integer[] array = {1, 2, 3, 4, 5};
        String[] strArray = {"1", "2", "3", "4", "5"};
        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));
        List<String> mappedLst = new LinkedList<>();

        for (String mappedItem : Collections.map(INTEGER_TO_STRING, intLst)) {
            mappedLst.add(mappedItem);
        }

        String[] mapped = mappedLst.toArray(new String[mappedLst.size()]);

        assertArrayEquals(strArray, mapped);
    }

    @Test
    public void testFilter() {
        Integer[] array = {1, 6, 5, 3, 9, 0, 10, 5, 3};
        Integer[] filteredArray = {1, 3, 0, 3};

        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));
        List<Integer> filteredIntLst = new LinkedList<>();


        for (Integer filteredInt : Collections.filter(LESS_THAN_FIVE, intLst)) {
            filteredIntLst.add(filteredInt);

        }
        Integer[] filtered = filteredIntLst.toArray(new Integer[filteredIntLst.size()]);

        assertArrayEquals(filteredArray, filtered);
    }

    @Test
    public void testTakeWhile() {
        Integer[] array = {2, 1, 4, -5, 9, 0, 10, 5, 3};
        Integer[] test = {2, 1, 4, -5};

        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));
        List<Integer> takeWhileIntLst = new LinkedList<>();


        for (Integer takeWhileInt : Collections.takeWhile(LESS_THAN_FIVE, intLst)) {
            takeWhileIntLst.add(takeWhileInt);
        }
        Integer[] takeWhileArray = takeWhileIntLst.toArray(new Integer[takeWhileIntLst.size()]);

        assertArrayEquals(test, takeWhileArray);
    }

    @Test
    public void testTakeUnless() {
        Integer[] array = {9, 15, 6, 70, 9, 0, 10, 5, 3};
        Integer[] test = {9, 15, 6, 70, 9};

        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));
        List<Integer> takeUnlessIntLst = new LinkedList<>();


        for (Integer takeUnlessInt : Collections.takeUnless(LESS_THAN_FIVE, intLst)) {
            takeUnlessIntLst.add(takeUnlessInt);
        }
        Integer[] takeUnlessArray = takeUnlessIntLst.toArray(new Integer[takeUnlessIntLst.size()]);

        assertArrayEquals(test, takeUnlessArray);
    }

    @Test
    public void testFoldl() {
        Integer[] array = {2, 4, 8, 16};
        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));

        assertEquals(new Integer(1024), Collections.foldl(MULT, 1, intLst));
        assertEquals(new Integer(2048), Collections.foldl(MULT, 2, intLst));

        assertEquals(new Integer(-30), Collections.foldl(SUB, 0, intLst));
        assertEquals(new Integer(-20), Collections.foldl(SUB, 10, intLst));
    }

    @Test
    public void testFoldr() {
        Integer[] array = {2, 4, 8, 16};
        List<Integer> intLst = new LinkedList<>(Arrays.asList(array));

        assertEquals(new Integer(1024), Collections.foldr(MULT, 1, intLst));
        assertEquals(new Integer(2048), Collections.foldr(MULT, 2, intLst));
        assertEquals(new Integer(-10), Collections.foldr(SUB, 0, intLst));
        assertEquals(new Integer(0), Collections.foldr(SUB, 10, intLst));
    }
}
