package com.chernobyl.gameengine;

import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger coreLogger = LoggerFactory.getLogger("CHERNOBYL");
    private static final Logger clientLogger = LoggerFactory.getLogger("APP");

    static public void init() {

    }

    static public Logger getCoreLogger() {
        return coreLogger;
    }

    static public Logger getClientLogger() {
        return clientLogger;
    }
}
