package infraestructure;

import domain.Logger;
import io.github.cdimascio.dotenv.Dotenv;

public class StdoutLogger implements Logger {
    // Singleton instance
    private static StdoutLogger instance;
    private boolean showDebugLogs;

    private StdoutLogger() {
        String debug =  Dotenv.load().get("ENVIRONMENT");
        this.showDebugLogs = debug != null && debug.trim().equalsIgnoreCase("True") ? true : false;
    }

    public static StdoutLogger getInstance() {
        if (instance == null) instance = new StdoutLogger();
        return instance;
    }

    @Override
    public void logInfo(String from, String message, Boolean isDebug) {
        if(isDebug && !this.showDebugLogs) return;
        System.out.println(String.format("[%s] \uD83D\uDFE6 %s", from, message));
    }

    @Override
    public void logError(String from, String message, Boolean isDebug) {
        if(isDebug && !this.showDebugLogs) return;
        System.out.println(String.format("[%s] \uD83D\uDFE5 %s", from, message));
    }

    @Override
    public void logSuccess(String from, String message, Boolean isDebug) {
        if(isDebug && !this.showDebugLogs) return;
        System.out.println(String.format("[%s] \uD83D\uDFE9 %s", from, message));
    }
}
