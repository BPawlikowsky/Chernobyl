package com.chernobyl.gameengine.core;

import java.time.Instant;
import java.util.HashMap;
import java.util.Vector;

public class InstrumentationTimer {

    private final String m_Name;
    private final Fn m_Func;
    private final Instant m_StartTimepoint;
    boolean m_Stopped;
    public static Vector<Instrumentor.ProfileResult> ProfileResults = new Vector<>(50);
    private static final HashMap<String, InstrumentationTimer> timers = new HashMap<>();

    InstrumentationTimer(String name, Fn func)
    {
		m_Name = name;
        m_Func = func;
        m_Stopped = false;
        m_StartTimepoint = Instant.now();
    }

    public void Stop()
    {
        var endTimepoint = Instant.now();

        long start = Math.round(m_StartTimepoint.getNano() * 0.001f);
        long end = Math.round(endTimepoint.getNano() * 0.001f);

        long threadID = Thread.currentThread().getId();
        Instrumentor.Get().WriteProfile(new Instrumentor.ProfileResult(m_Name, start, end, threadID));

        m_Stopped = true;
    }

    public interface Fn {
        void fn(Instrumentor.ProfileResult result);
    }
}
