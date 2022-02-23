package com.chernobyl.gameengine.core.layer;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.core.Timestep;

public abstract class Layer {
    final protected String m_DebugName;

    public Layer() {
        m_DebugName = "Layer";
    }

    public Layer(String name) {
        m_DebugName = name;
    }

    public abstract void OnAttach();

    public abstract void OnDetach();

    public abstract void OnUpdate(Timestep ts);

    public abstract void OnImGuiRender();

    public abstract void OnEvent(Event event);

    String GetName() {
        return m_DebugName;
    }

}
