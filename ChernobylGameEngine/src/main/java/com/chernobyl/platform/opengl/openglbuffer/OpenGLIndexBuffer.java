package com.chernobyl.platform.opengl.openglbuffer;

import com.chernobyl.gameengine.buffer.IndexBuffer;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class OpenGLIndexBuffer extends IndexBuffer {
    private final int m_RendererID;
    protected final int m_Count;

    public OpenGLIndexBuffer(int[] indices, int size) {
        HB_PROFILE_FUNCTION();

        m_Count = size;
        m_RendererID = glCreateBuffers();
        // GL_ELEMENT_ARRAY_BUFFER is not valid without an actively bound VAO
        // Binding with GL_ARRAY_BUFFER allows the data to be loaded regardless of VAO state.
        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);
        glBufferData(GL_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        HB_PROFILE_FUNCTION_STOP();
    }

    public int GetCount() {
        return m_Count;
    }

    @Override
    public void destroy() {
        HB_PROFILE_FUNCTION();

        glDeleteBuffers(m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Bind() {
        HB_PROFILE_FUNCTION();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Unbind() {
        HB_PROFILE_FUNCTION();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        HB_PROFILE_FUNCTION_STOP();
    }
}
