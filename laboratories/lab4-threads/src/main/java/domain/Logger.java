package domain;

public interface Logger {
    void logInfo(String from, String message, Boolean isDebug);
    void logError(String from, String message, Boolean isDebug);
    void logSuccess(String from, String message, Boolean isDebug);
}
