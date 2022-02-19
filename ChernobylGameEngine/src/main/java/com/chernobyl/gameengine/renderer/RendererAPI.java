package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Vec4;

public abstract class RendererAPI {
    public abstract void Init();

    public enum API {
        None, OpenGL
    }

    private static final API s_API = API.OpenGL;

    public abstract void SetClearColor(Vec4 color);
    public abstract void Clear();

    public abstract void DrawIndexed(VertexArray vertexArray);

    public static API GetAPI() { return s_API; }
}