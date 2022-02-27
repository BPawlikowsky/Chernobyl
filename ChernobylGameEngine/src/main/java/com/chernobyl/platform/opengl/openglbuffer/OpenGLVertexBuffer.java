package com.chernobyl.platform.opengl.openglbuffer;

import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.render.BufferLayout;
import lombok.Getter;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class OpenGLVertexBuffer extends VertexBuffer {
    private final int m_RendererID;
    @Getter
    protected int m_Count;
    private BufferLayout layout;

    public OpenGLVertexBuffer(float[] vertices, int size) {
        HB_PROFILE_FUNCTION();

        m_RendererID = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void destroy() {
        HB_PROFILE_FUNCTION();

        glDeleteBuffers(m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Bind() {
        HB_PROFILE_FUNCTION();

        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Unbind() {
        HB_PROFILE_FUNCTION();

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        HB_PROFILE_FUNCTION_STOP();
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
