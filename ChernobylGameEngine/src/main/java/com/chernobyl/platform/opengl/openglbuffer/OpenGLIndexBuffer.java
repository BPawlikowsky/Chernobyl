package com.chernobyl.platform.opengl.openglbuffer;

import com.chernobyl.gameengine.buffer.IndexBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class OpenGLIndexBuffer extends IndexBuffer {
    private final int m_RendererID;
    private int m_Count;

    public OpenGLIndexBuffer(int[] indices, int size) {
        super();
        m_RendererID = glCreateBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_RendererID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void destroy() {
        glDeleteBuffers(m_RendererID);
    }

    void Bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_RendererID);
    }

    void Unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
