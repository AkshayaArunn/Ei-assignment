package org.example.smartoffice.util;
import java.io.IOException;
import java.util.logging.*;

public final class LoggerUtil {
    private static final Logger logger = Logger.getLogger("SmartOfficeLogger");
    private static boolean initialized = false;

    private LoggerUtil() {}

    public static synchronized Logger getLogger() {
        if (!initialized) {
            try {
                logger.setUseParentHandlers(false);
                logger.setLevel(Level.INFO);

                ConsoleHandler ch = new ConsoleHandler();
                ch.setLevel(Level.INFO);
                ch.setFormatter(new SimpleFormatter());
                logger.addHandler(ch);

                Handler fh = new FileHandler("smartoffice.log", true);
                fh.setLevel(Level.INFO);
                fh.setFormatter(new SimpleFormatter());
                logger.addHandler(fh);

                initialized = true;
            } catch (IOException e) {
                System.err.println("Failed to initialize logging: " + e.getMessage());
            }
        }
        return logger;
    }
}
