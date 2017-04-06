import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ivan on 06.04.17.
 */

public class Function1Test {
    private static Function1<String, Integer> STRING_TO_INTEGER = (s) -> Integer.valueOf(s);
    private static Function1<Integer, String> INTEGER_TO_STRING = (i) -> Integer.toString(i);


    @Test
    public void testApply() {
        String fiveStr = "5";
        Integer five = Integer.valueOf(fiveStr);

        assertEquals(five, STRING_TO_INTEGER.apply(fiveStr));
        assertEquals(fiveStr, INTEGER_TO_STRING.apply(five));
    }

    @Test
    public void testCompose() {
        String fiveStr = "5";

        assertEquals(fiveStr, STRING_TO_INTEGER.compose(INTEGER_TO_STRING).apply(fiveStr));
    }

}
