package com.chernobyl.gameengine.core;

import java.time.Instant;
import java.util.HashMap;
import java.util.Vector;

public class InstrumentationTimer {

    private final String m_Name;
    private final Fn m_Func;
    private final long m_StartTimepoint;
    boolean m_Stopped;
    public static Vector<Instrumentor.ProfileResult> ProfileResults = new Vector<>(50);
    private static final HashMap<String, InstrumentationTimer> timers = new HashMap<>();

    InstrumentationTimer(String name, Fn func)
    {
		m_Name = name;
        m_Func = func;
        m_Stopped = false;
        m_StartTimepoint = System.nanoTime();
    }

    public void Stop()
    {
        var endTimepoint = System.nanoTime();

        long start = Math.round(m_StartTimepoint * 0.001d);
        long end = Math.round(endTimepoint * 0.001d);

        long threadID = Thread.currentThread().getId();
        Instrumentor.Get().WriteProfile(new Instrumentor.ProfileResult(m_Name, start, end, threadID));

        m_Stopped = true;
    }

    public interface Fn {
        void fn(Instrumentor.ProfileResult result);
    }
}
