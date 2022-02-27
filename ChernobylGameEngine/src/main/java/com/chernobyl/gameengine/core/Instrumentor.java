package com.chernobyl.gameengine.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Vector;

import static com.chernobyl.gameengine.core.Log.HB_CORE_ERROR;

public class Instrumentor {
    private enum Profile {
        On, Off
    }

    private static class InstrumentationSession {
        String Name;

        public InstrumentationSession(String name) {
            Name = name;
        }
    }

    public static class ProfileResult
    {
        public String Name;
        public float Time;
        long Start, End;
        long ThreadID;

        public ProfileResult(String name, long start, long end, long threadID) {
            Name = name;
            Start = start;
            End = end;
            ThreadID = threadID;
        }
    }

    private boolean m_Stopped;
    public static Vector<ProfileResult> ProfileResults = new Vector<>(50);
    private static final HashMap<String, InstrumentationTimer> timers = new HashMap<>();

    private InstrumentationSession m_CurrentSession;
    private FileOutputStream m_OutputStream;
    int m_ProfileCount;

    private static Instrumentor instance;

    public Instrumentor() {
        m_CurrentSession = null;
        m_ProfileCount = 0;
    }

    void BeginSession(String name) {
        String filepath = "results.json";
        BeginSession(name, filepath);
    }

    void BeginSession(String name, String filepath) {
        try {
            m_OutputStream = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            HB_CORE_ERROR("Could not create file {0}, error: {1}", filepath, e);
        }
        WriteHeader();
        m_CurrentSession = new InstrumentationSession(name);
    }

    void EndSession() {
        WriteFooter();
        try {
            m_OutputStream.close();
        } catch (IOException e) {
            HB_CORE_ERROR("Could not close file, error: {0}", e);
        }
        m_CurrentSession = null;
        m_ProfileCount = 0;
    }

    void WriteProfile(ProfileResult result) {
        if (m_ProfileCount++ > 0) {
            try {
                m_OutputStream.write(",".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                HB_CORE_ERROR("Could not write to file, error: {0}", e);
            }
        }

        String name = result.Name;
        name = name.replace('"', '\'');

        try {
            m_OutputStream.write("{".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write("\"cat\":\"function\",".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write(("\"dur\":" + (result.End - result.Start) + ',').getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write(("\"name\":\"" + name + "\",").getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write("\"ph\":\"X\",".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write("\"pid\":0,".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write(("\"tid\":" + result.ThreadID + ",").getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write(("\"ts\":" + result.Start).getBytes(StandardCharsets.UTF_8));
            m_OutputStream.write("}".getBytes(StandardCharsets.UTF_8));

            m_OutputStream.flush();
        } catch (IOException e) {
            HB_CORE_ERROR("Could not write to file, error: {0}", e);
        }
    }

    void WriteHeader() {
        try {
            m_OutputStream.write("{\"otherData\": {},\"traceEvents\":[".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.flush();
        } catch (IOException e) {
            HB_CORE_ERROR("Could not write to header, error: {0}", e);
        }
    }

    void WriteFooter() {
        try {
            m_OutputStream.write("]}".getBytes(StandardCharsets.UTF_8));
            m_OutputStream.flush();
        } catch (IOException e) {
            HB_CORE_ERROR("Could not write to footer, error: {0}", e);
        }
    }

    public static Instrumentor Get() {
        if(instance == null) instance = new Instrumentor();
        return instance;
    }

    public static void HB_PROFILE_BEGIN_SESSION(String name, String filepath) {
        Instrumentor.Get().BeginSession(name, filepath);
    }

    public static void HB_PROFILE_END_SESSION() {
        Instrumentor.Get().EndSession();
    }

    public static void HB_PROFILE_SCOPE(String name) {
        InstrumentationTimer timer = new InstrumentationTimer(
                "Line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + ": " + name,
                (ProfileResult profileResult) -> ProfileResults.addElement(profileResult)
        );
        timers.put(name, timer);
    }

    public static void HB_PROFILE_SCOPE_STOP(String name)
    {
        var last = timers.remove(name);
        if (!last.m_Stopped)
            last.Stop();
    }


    public static void HB_PROFILE_FUNCTION() {
        var stack = Thread.currentThread().getStackTrace()[2];
        HB_PROFILE_SCOPE(stack.getClassName() + "::" + stack.getMethodName());
    }

    public static void HB_PROFILE_FUNCTION_STOP() {
        var stack = Thread.currentThread().getStackTrace()[2];
        HB_PROFILE_SCOPE_STOP(stack.getClassName() + "::" + stack.getMethodName());
    }
}

