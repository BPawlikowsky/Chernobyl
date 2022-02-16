package com.chernobyl.gameengine.core;

public class Timestep {
   private static float m_Time;

   public Timestep() {
      m_Time = 0.0f;
   }

   public Timestep(float time) {
      m_Time = time;
   }

   public float GetSeconds() { return m_Time; }
   public float GetMilliseconds() { return m_Time * 1000.0f; }
}
