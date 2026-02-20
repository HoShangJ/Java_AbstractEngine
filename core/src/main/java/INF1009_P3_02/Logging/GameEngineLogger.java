package INF1009_P3_02.Logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameEngineLogger {

    public enum LogLevel {
        INFO, WARNING, ERROR
    }

    private PrintWriter writer;
    private final String logFilePath;
    private boolean enabled;

    public GameEngineLogger(String logFilePath) {
        this.logFilePath = logFilePath;
        this.enabled = true;
        open();
    }

    private void open() {
        try {
            writer = new PrintWriter(new FileWriter(logFilePath, true)); // append mode
        } catch (IOException e) {
            System.err.println("[EngineLogger] Failed to open log file: " + logFilePath);
            enabled = false;
        }
    }

    //Log a message at a given level.
    public void log(LogLevel level, String message) {
        if (!enabled || writer == null) return;

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String entry = String.format("[%s] [%s] %s", timestamp, level.name(), message);

        writer.println(entry);
        writer.flush();
        System.out.println(entry);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void close() {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }
}
