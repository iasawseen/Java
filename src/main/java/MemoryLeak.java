/**
 * Created by ivan on 03.05.17.
 */

public class MemoryLeak {
    byte leak[];

    MemoryLeak(int megaBytesToAcquire) {
        leak = new byte[megaBytesToAcquire * 1024 * 1024];
    }
}
