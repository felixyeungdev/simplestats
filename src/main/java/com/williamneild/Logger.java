package com.williamneild;

public class Logger {
    private static System.Logger logger;
    private static Logger instance;

    private Logger() {
        logger = System.getLogger("SimpleStats");
    }

    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public System.Logger getLogger() {
        return logger;
    }

    public static void log(System.Logger.Level level, String message) {
        Logger.getInstance().getLogger().log(level, message);
    }

    public static void info(String message) {
        Logger.log(System.Logger.Level.INFO, message);
    }

    public static void error(String message) {
        Logger.log(System.Logger.Level.ERROR, message);
    }
}