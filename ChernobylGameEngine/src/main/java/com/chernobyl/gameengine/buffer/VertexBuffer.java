package com.chernobyl.gameengine.buffer;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.openglbuffer.OpenGLVertexBuffer;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class VertexBuffer {

    public static VertexBuffer Create(float[] vertices, int size) {
        switch (Renderer.GetAPI()) {
            case None:
                HB_CORE_ASSERT(false, "RendererAPI.None is currently not supported!");
                return null;
            case OpenGL:
                return new OpenGLVertexBuffer(vertices, size);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

}
