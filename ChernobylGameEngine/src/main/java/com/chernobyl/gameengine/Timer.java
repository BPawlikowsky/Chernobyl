package com.chernobyl.gameengine;

import java.time.Instant;
import java.util.HashMap;
import java.util.Vector;

public class Timer {
    public static class ProfileResult
    {
		public String Name;
        public float Time;
        public ProfileResult(String name, float time) {
            Name = name;
            Time = time;
        }
    }

    private final String m_Name;
    private final Fn m_Func;
    private final Instant m_StartTimepoint;
    private boolean m_Stopped;
    public static Vector<ProfileResult> ProfileResults = new Vector<>(50);
    private static final HashMap<String, Timer> timers = new HashMap<>();

    Timer(String name, Fn func)
    {
		m_Name = name;
        m_Func = func;
        m_Stopped = false;
        m_StartTimepoint = Instant.now();
    }

	public static void PROFILE_SCOPE_STOP(String name)
    {
        var last = timers.remove(name);
        if (!last.m_Stopped)
            last.Stop();
    }

    public void Stop()
    {
        var endTimepoint = Instant.now();

        long start = m_StartTimepoint.getNano();
        long end = endTimepoint.getNano();

        m_Stopped = true;

        float duration = (end - start) * 0.000001f;
        m_Func.fn(new ProfileResult(m_Name, duration));
    }

    public static void PROFILE_SCOPE(String name) {
        timers.put(name, new Timer(name, (ProfileResult profileResult) -> ProfileResults.addElement(profileResult)));
    }
    public interface Fn {
        void fn(Timer.ProfileResult result);
    }
}
