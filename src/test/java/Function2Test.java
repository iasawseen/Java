import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ivan on 06.04.17.
 */

public class Function2Test {
    private static Function2<String, Integer, Integer> SUM = (s, i) -> Integer.valueOf(s) + i;
    private static Function2<String, Integer, Integer> MULT = (s, i) -> Integer.valueOf(s) * i;
    private static Function1<Integer, String> INTEGER_TO_STRING = (i) -> i.toString();
    private static String FIVE_STR = "5";
    private static Integer FIVE = Integer.valueOf(FIVE_STR);
    private static String TEN_STR = "10";
    private static Integer TEN = Integer.valueOf(TEN_STR);
    private static Integer FIFTY = 50;
    private static Integer FIFTEEN = 15;


    @Test
    public void testApply() {
        assertEquals(FIFTEEN, SUM.apply(FIVE_STR, TEN));
        assertEquals(FIFTEEN, SUM.apply(TEN_STR, FIVE));

        assertEquals(FIFTY, MULT.apply(FIVE_STR, TEN));
        assertEquals(FIFTY, MULT.apply(TEN_STR, FIVE));
    }

    @Test
    public void testBind1() {
        assertEquals(FIFTEEN, SUM.bind1(FIVE_STR).apply(TEN));
        assertEquals(FIFTEEN, SUM.bind1(TEN_STR).apply(FIVE));

        assertEquals(FIFTY, MULT.bind1(FIVE_STR).apply(TEN));
        assertEquals(FIFTY, MULT.bind1(TEN_STR).apply(FIVE));
    }

    @Test
    public void testBind2() {
        assertEquals(FIFTEEN, SUM.bind2(TEN).apply(FIVE_STR));
        assertEquals(FIFTEEN, SUM.bind2(FIVE).apply(TEN_STR));

        assertEquals(FIFTY, MULT.bind2(TEN).apply(FIVE_STR));
        assertEquals(FIFTY, MULT.bind2(FIVE).apply(TEN_STR));
    }

    @Test
    public void testCompose() {
        String FIFTY_STR = "50";
        String FIFTEEN_STR = "15";

        assertEquals(FIFTEEN_STR, SUM.compose(INTEGER_TO_STRING).apply(FIVE_STR, TEN));
        assertEquals(FIFTEEN_STR, SUM.compose(INTEGER_TO_STRING).apply(TEN_STR, FIVE));

        assertEquals(FIFTY_STR, MULT.compose(INTEGER_TO_STRING).apply(FIVE_STR, TEN));
        assertEquals(FIFTY_STR, MULT.compose(INTEGER_TO_STRING).apply(TEN_STR, FIVE));
    }

    @Test
    public void testCurry() {
        assertEquals(FIFTEEN, SUM.curry().apply(FIVE_STR).apply(TEN));
        assertEquals(FIFTEEN, SUM.curry().apply(TEN_STR).apply(FIVE));

        assertEquals(FIFTY, MULT.curry().apply(FIVE_STR).apply(TEN));
        assertEquals(FIFTY, MULT.curry().apply(TEN_STR).apply(FIVE));
    }
}
