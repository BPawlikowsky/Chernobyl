package com.chernobyl.platform.opengl.openglbuffer;

import com.chernobyl.gameengine.buffer.IndexBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class OpenGLIndexBuffer extends IndexBuffer {
    private final int m_RendererID;
    protected final int m_Count;

    public OpenGLIndexBuffer(int[] indices, int size) {
        super();
        m_RendererID = glCreateBuffers();
        m_Count = size;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_RendererID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public int getCount() {
        return m_Count;
    }

    public void destroy() {
        glDeleteBuffers(m_RendererID);
    }

    public void Bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_RendererID);
    }

    public void Unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
