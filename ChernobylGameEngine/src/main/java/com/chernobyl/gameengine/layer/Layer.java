package com.chernobyl.gameengine.layer;

import com.chernobyl.gameengine.event.Event;

public abstract class Layer {
    protected String m_DebugName;

    public Layer() {
        m_DebugName = "Layer";
    }

    public Layer(String name) {
        m_DebugName = name;
    }

    public abstract void OnAttach();

    public abstract void OnDetach();

    public abstract void OnUpdate();

    public abstract void OnEvent(Event event);

    String GetName() {
        return m_DebugName;
    }

}
