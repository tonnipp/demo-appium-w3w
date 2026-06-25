package utils.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private static final Logger LOG = LogManager.getLogger(Log.class);

    // ===== INFO =====
    public static void info(String message, Object... params) {
        LOG.info(message, params);
    }

    public static void info(Object object) {
        LOG.info(object);
    }

    // ===== WARN =====
    public static void warn(String message, Object... params) {
        LOG.warn(message, params);
    }

    public static void warn(Object object) {
        LOG.warn(object);
    }

    // ===== ERROR =====
    public static void error(String message, Object... params) {
        LOG.error(message, params);
    }

    public static void error(Object object) {
        LOG.error(object);
    }

    // ===== DEBUG =====
    public static void debug(String message, Object... params) {
        LOG.debug(message, params);
    }

    public static void debug(Object object) {
        LOG.debug(object);
    }

    // ===== FATAL =====
    public static void fatal(String message, Object... params) {
        LOG.fatal(message, params);
    }
}
