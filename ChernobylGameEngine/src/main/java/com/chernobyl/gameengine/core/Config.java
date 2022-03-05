package com.chernobyl.gameengine.core;

public class Config {
    public enum EnvConfiguration {
        Prod, Dev, Debug
    }

    public static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
        getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

    public static boolean isProfiler = true;
    public static EnvConfiguration env = (isDebug) ? EnvConfiguration.Debug : EnvConfiguration.Dev;
}
