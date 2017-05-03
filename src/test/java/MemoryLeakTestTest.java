import org.junit.Rule;
import org.junit.Test;

/**
 * Created by ivan on 03.05.17.
 */

public class MemoryLeakTestTest {
    @Rule
    public final MemoryLeakTest memLeak = new MemoryLeakTest();

    private MemoryLeak memoryLeak = null;

    @Test
    public void testNoLeaks() {
        memLeak.limit(10);
    }

    @Test
    public void testLeaks() throws AssertionError {
        memLeak.limit(10);
        memoryLeak = new MemoryLeak(100);
    }
}