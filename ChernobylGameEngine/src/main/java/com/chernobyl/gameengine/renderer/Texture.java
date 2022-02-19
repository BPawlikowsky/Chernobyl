package com.chernobyl.gameengine.renderer;

public abstract class Texture {
    public abstract void destroy();

    public abstract int GetWidth();
    public abstract int GetHeight();

    public abstract void Bind();
    public abstract void Bind(int slot);
}
