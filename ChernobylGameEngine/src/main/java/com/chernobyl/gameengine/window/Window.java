package com.chernobyl.gameengine.window;

import lombok.Getter;

public abstract class Window {
    @Getter
    protected long nativeWindow;
    @Getter
    protected String title;
    @Getter
    protected int width, height;
    protected static Window m_Window = null;
    public abstract void OnUpdate();
    public abstract void Shutdown();

    // Window attributes
    public abstract void SetEventCallback(IEventCallback callback);
    public abstract void SetVSync(boolean enabled);
    public abstract boolean IsVSync();
}
