package com.chernobyl.gameengine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger coreLogger = LoggerFactory.getLogger("CHERNOBYL");
    private static final Logger clientLogger = LoggerFactory.getLogger("APP");

    static private Logger getCoreLogger() {
        return coreLogger;
    }

    static private Logger getClientLogger() {
        return clientLogger;
    }

    static private String formatString (String s, Object...args) {
        String formattedString = s;
        for (int i = 0; i < args.length; i++) {
            StringBuilder replaceString = new StringBuilder("{" + i + "}");
            formattedString = formattedString.replace(replaceString, new StringBuilder(args[i].toString()));
        }
        return formattedString;
    }

    static public void HB_CORE_CRITICAL(Object arg) {
        getCoreLogger().error("(CRITICAL) " + arg.toString());
    }
    static public void HB_CORE_CRITICAL(String arg, Object...args) {
        getCoreLogger().error("(CRITICAL) " + formatString(arg, args));
    }

    static public void HB_CORE_ERROR(Object arg) { getCoreLogger().error(arg.toString()); }
    static public void HB_CORE_ERROR(String arg, Object...args) { getCoreLogger().error(formatString(arg, args)); }

    static public void HB_CORE_WARN(Object arg) { getCoreLogger().warn(arg.toString()); }
    static public void HB_CORE_WARN(String arg, Object...args) { getCoreLogger().warn(formatString(arg, args)); }

    static public void HB_CORE_INFO(Object arg) { getCoreLogger().info(arg.toString()); }
    static public void HB_CORE_INFO(String arg, Object...args) { getCoreLogger().info(formatString(arg, args)); }

    static public void HB_CORE_TRACE(Object arg) { getCoreLogger().trace(arg.toString()); }
    static public void HB_CORE_TRACE(String arg, Object...args) { getCoreLogger().trace(formatString(arg, args)); }

    static public void HB_ERROR(Object arg) { getClientLogger().error(arg.toString()); }
    static public void HB_ERROR(String arg, Object...args) { getClientLogger().error(formatString(arg, args)); }

    static public void HB_WARN(Object arg) { getClientLogger().warn(arg.toString()); }
    static public void HB_WARN(String arg, Object...args) { getClientLogger().warn(formatString(arg, args)); }

    static public void HB_INFO(Object arg) { getClientLogger().info(arg.toString()); }
    static public void HB_INFO(String arg, Object...args) { getClientLogger().info(formatString(formatString(arg, args))); }

    static public void HB_TRACE(Object arg) { getClientLogger().trace(arg.toString()); }
    static public void HB_TRACE(String arg, Object...args) { getClientLogger().trace(formatString(arg, args)); }
}
