package com.chernobyl.platform.opengl.openglbuffer;

import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.render.BufferLayout;
import lombok.Getter;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class OpenGLVertexBuffer extends VertexBuffer {
    private final int m_RendererID;
    @Getter
    protected int m_Count;
    private BufferLayout layout;

    public OpenGLVertexBuffer(float[] vertices, int size) {
        super();
        m_RendererID = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    public void destroy() {
        glDeleteBuffers(m_RendererID);
    }

    void Bind() {
        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);
    }

    void Unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public BufferLayout GetLayout() {
        return layout;
    }

    @Override
    public void SetLayout(BufferLayout layout) {
        this.layout = layout;
    }
}
