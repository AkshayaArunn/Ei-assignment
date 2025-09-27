package main.java.org.example.patterns.singleton;

class Logger {
    private static Logger instance;
    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
}

public class LoggerDemo {
    public static void main(String[] args) {
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();

        l1.log("System started.");
        l2.log("Another module logged this.");

        System.out.println("Same instance? " + (l1 == l2));
    }
}
