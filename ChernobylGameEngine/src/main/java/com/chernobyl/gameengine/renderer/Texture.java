package com.chernobyl.gameengine.renderer;

public abstract class Texture {
    public abstract void destroy();

    public abstract int GetWidth();
    public abstract int GetHeight();

    public abstract void SetData(long data, int size);
    public abstract int GetRendererID();

    public abstract void Bind();
    public abstract void Bind(int slot);
}
