package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.render.ShaderDataType;
import com.chernobyl.gameengine.renderer.VertexArray;

import java.util.Vector;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

public class OpenGLVertexArray extends VertexArray {
    private final int m_RendererID;
    private final Vector<VertexBuffer> m_VertexBuffers;
    private IndexBuffer m_IndexBuffer;

    public OpenGLVertexArray() {
        m_VertexBuffers = new Vector<>();
        m_RendererID = glCreateVertexArrays();
    }

    @Override
    public void destroy() {
        glDeleteVertexArrays(m_RendererID);
    }

    @Override
    public void Bind() {
        glBindVertexArray(m_RendererID);
    }

    @Override
    public void Unbind() {
        glBindVertexArray(0);
    }

    @Override
    public void AddVertexBuffer(VertexBuffer vertexBuffer) {
        HB_CORE_ASSERT(vertexBuffer.GetLayout().GetElements().size() != 0, "Vertex Buffer has no layout!");

        glBindVertexArray(m_RendererID);
        vertexBuffer.Bind();

        int index = 0;
        var layout = vertexBuffer.GetLayout();
        for (var element : layout.GetElements())
        {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index,
                    element.GetComponentCount(),
                    ShaderDataTypeToOpenGLBaseType(element.getType()),
                    element.isNormalized(),
                    layout.GetStride(),
                    element.getOffset());
            index++;
        }

        m_VertexBuffers.insertElementAt(vertexBuffer, 0);
    }

    @Override
    public void SetIndexBuffer(IndexBuffer indexBuffer) {
        glBindVertexArray(m_RendererID);
        indexBuffer.Bind();

        m_IndexBuffer = indexBuffer;
    }

    @Override
    public Vector<VertexBuffer> GetVertexBuffers() {
        return m_VertexBuffers;
    }

    @Override
    public IndexBuffer GetIndexBuffer() {
        return m_IndexBuffer;
    }

    static int ShaderDataTypeToOpenGLBaseType(ShaderDataType type)
    {
        switch (type)
        {
            case Float:    return GL_FLOAT;
            case Float2:   return GL_FLOAT;
            case Float3:   return GL_FLOAT;
            case Float4:   return GL_FLOAT;
            case Mat3:     return GL_FLOAT;
            case Mat4:     return GL_FLOAT;
            case Int:      return GL_INT;
            case Int2:     return GL_INT;
            case Int3:     return GL_INT;
            case Int4:     return GL_INT;
            case Bool:     return GL_BOOL;
        }

        HB_CORE_ASSERT(false, "Unknown ShaderDataType!");
        return 0;
    }
}
