package com.chernobyl.gameengine.buffer;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.openglbuffer.OpenGLIndexBuffer;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public abstract class IndexBuffer {
    abstract public void destroy();
    public abstract void Bind();
    public abstract void Unbind();
    public static IndexBuffer Create(int[] indices, int count) {
        switch (Renderer.GetAPI()) {
            case None:
                HB_CORE_ASSERT(false, "RendererAPI.None is currently not supported!");
                return null;
            case OpenGL:
                return new OpenGLIndexBuffer(indices, count);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public abstract int GetCount();
}
