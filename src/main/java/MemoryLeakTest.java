/**
 * Created by ivan on 03.05.17.
 */

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


public class MemoryLeakTest implements TestRule{
    private long limit;

    public void limit(long megaByteLimit) {
        limit = megaByteLimit * 1024 * 1024;
    }

    private long getConsumedMemory(Runtime runtime) {
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Runtime runtime = Runtime.getRuntime();

                long memoryBefore = getConsumedMemory(runtime);
                statement.evaluate();
                long memoryAfter = getConsumedMemory(runtime);

                if ((memoryAfter - memoryBefore) > limit) {
                    throw new AssertionError("you have memory leak");
                }
            }
        };
    }
}
