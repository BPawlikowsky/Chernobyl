package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.platform.opengl.OpenGLVertexArray;

import java.util.Vector;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class VertexArray {
    public abstract void destroy();

    public abstract void Bind();

    public abstract void Unbind();

    public abstract void AddVertexBuffer(VertexBuffer vertexBuffer);

    public abstract void SetIndexBuffer(IndexBuffer indexBuffer);

    public abstract Vector<VertexBuffer> GetVertexBuffers();

    public abstract IndexBuffer GetIndexBuffer();

    public static VertexArray Create() {
        switch (Renderer.GetAPI()) {
            case None:
                HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!");
                return null;
            case OpenGL:
                return new OpenGLVertexArray();
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }
}
