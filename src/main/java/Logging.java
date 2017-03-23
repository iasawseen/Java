import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;


/**
 * Created by ivan on 23.03.17.
 */

public class Logging {
    static private final Logger LOGGER = LogManager.getLogger(Logging.class);

    public static void main(String[] args) {
        LOGGER.trace("Trace Message!");
        LOGGER.debug("Debug Message!");
        LOGGER.info("Info Message!");
        LOGGER.warn("Warn Message!");
        LOGGER.error("Error Message!");
        LOGGER.fatal("Fatal Message!");
    }
}
