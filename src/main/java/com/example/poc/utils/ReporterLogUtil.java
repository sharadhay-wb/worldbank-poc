package com.example.poc.utils;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.testng.Reporter;

public class ReporterLogUtil {
  private static String defaultMsg = "";
  private static List<String> logHeaders = new ArrayList<>();
  private static Long startMilliSeconds;

  public ReporterLogUtil() {}

  public static void log(String log) {
    log(log, true);
  }

  public static void log(String log, boolean logToStandardOut) {
    Reporter.log(getTimeDiff() + getLogHeader() + log, logToStandardOut);
  }

  private static String getTimeDiff() {
    if (getStartMilliSeconds() != null) {
      Long timeDiff =
          System.currentTimeMillis()
              - (getStartMilliSeconds() == null ? 0 : getStartMilliSeconds());
      var duration = Duration.ofMillis(timeDiff);
      // Extract hours, minutes, seconds, and milliseconds from the Duration
      long minutes = duration.toMinutesPart();
      long seconds = duration.toSecondsPart();
      long millis = duration.toMillisPart();
      return (minutes > 0 ? minutes + "m:" : "")
          + (seconds > 0 ? seconds + "s:" : "")
          + (millis > 0 ? millis + "ms" : "")
          + "  TIMESTAMP:";
    } else return "";
  }

  private static String getLogHeader() {
    return defaultMsg;
  }

  public static void setLogHeader(String log) {
    defaultMsg = "HEADER " + log + " END HEADER";
    //    setLogHeaders(asList(defaultMsg));
  }

  public static List<String> getLogHeaders() {
    return logHeaders;
  }

  private static void setLogHeaders(List<String> logHeaders) {
    if (logHeaders.size() > 0) logHeaders.forEach(t -> ReporterLogUtil.logHeaders.add(t));
    logHeaders.forEach(
        t -> {
          System.err.println("List set the headers: " + t);
        });
  }

  public static Long getStartMilliSeconds() {
    return startMilliSeconds;
  }

  public static void setStartMilliSeconds(Long startMilliSeconds) {
    ReporterLogUtil.startMilliSeconds = startMilliSeconds;
  }
}
