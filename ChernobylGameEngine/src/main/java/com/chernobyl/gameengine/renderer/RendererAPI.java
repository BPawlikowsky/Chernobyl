package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.platform.opengl.OpenGLRendererAPI;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public abstract class RendererAPI {
    RendererAPI Create()
    {
        switch (s_API)
        {
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLRendererAPI();
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }
    public abstract void Init();

    public enum API {
        None, OpenGL
    }

    private static final API s_API = API.OpenGL;

    public abstract void SetViewport(int x, int y, int width, int height);

    public abstract void SetClearColor(Vec4 color);
    public abstract void Clear();

    public abstract void DrawIndexed(VertexArray vertexArray, int indexCount);

    public static API GetAPI() { return s_API; }
}