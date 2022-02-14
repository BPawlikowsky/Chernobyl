package com.chernobyl.gameengine.buffer;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.openglbuffer.OpenGLIndexBuffer;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class IndexBuffer {
    public abstract void Bind();
    public abstract void Unbind();
    public static IndexBuffer Create(int[] indices, int size) {
        switch (Renderer.GetAPI()) {
            case None:
                HB_CORE_ASSERT(false, "RendererAPI.None is currently not supported!");
                return null;
            case OpenGL:
                return new OpenGLIndexBuffer(indices, size);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public abstract int getCount();
}
