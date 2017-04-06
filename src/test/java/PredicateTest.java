import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ivan on 06.04.17.
 */

public class PredicateTest {
    private static Predicate<Boolean> IS_TRUE = (t) -> t;
    private static Predicate<Boolean> IS_FALSE = (t) -> !t;


    @Test
    public void testAnd() {
        assertTrue(IS_TRUE.and(IS_TRUE).apply(true));
        assertFalse(IS_FALSE.and(IS_TRUE).apply(true));
        assertFalse(IS_FALSE.and(IS_FALSE).apply(true));
    }

    @Test
    public void testOr() {
        assertTrue(IS_TRUE.or(IS_TRUE).apply(true));
        assertTrue(IS_TRUE.or(IS_FALSE).apply(true));
        assertTrue(IS_FALSE.or(IS_TRUE).apply(true));
        assertFalse(IS_FALSE.or(IS_FALSE).apply(true));
    }

    @Test
    public void testNot() {
        assertFalse(IS_TRUE.not().apply(true));
        assertTrue(IS_FALSE.not().apply(true));
    }

    @Test
    public void testAlwaysTrue() {
        assertTrue(Predicate.ALWAYS_TRUE.apply(null));
    }

    @Test
    public void testAlwaysFalse() {
        assertFalse(Predicate.ALWAYS_FALSE.apply(null));
    }
}
