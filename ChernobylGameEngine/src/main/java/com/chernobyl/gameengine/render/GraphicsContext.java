package com.chernobyl.gameengine.render;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.OpenGLContext;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public abstract class GraphicsContext {

    public static GraphicsContext Create(long window)
    {
        switch (Renderer.GetAPI())
        {
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLContext(window);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public abstract void Init();
    public abstract void SwapBuffers();
}
