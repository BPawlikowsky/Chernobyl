package com.chernobyl.gameengine.window;

import lombok.Getter;

public abstract class Window {
    @Getter
    protected String title;
    @Getter
    protected int width, height;
    protected static Window window = null;
    public abstract void OnUpdate();
    public abstract void Shutdown();

    // Window attributes
    public abstract void SetEventCallback(IEventCallback callback);
    abstract void SetVSync(boolean enabled);
    abstract boolean IsVSync();
}
