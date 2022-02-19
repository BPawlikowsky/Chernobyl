package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.renderer.Texture2D;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_INFO;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class OpenGLTexture2D extends Texture2D {
    private String m_Path;
    private final int m_Width;
    private final int m_Height;
    private final int m_RendererID;

    public OpenGLTexture2D(String path) {
        IntBuffer width, height, channels;
        ByteBuffer data;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            width = stack.callocInt(1);
            height = stack.callocInt(1);
            channels = stack.callocInt(1);
            stbi_set_flip_vertically_on_load(true);
            data = stbi_load(path, width, height, channels, 0);
        }
        HB_CORE_ASSERT(data != null, "Failed to load image!");
        m_Width = width.get(0);
        m_Height = height.get(0);

        HB_CORE_INFO("Texture height: {0}, width: {1}, channels: {2}", m_Height, m_Width, channels.get(0));

        m_RendererID = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(m_RendererID, 1, GL_RGB8, m_Width, m_Height);

        glTextureParameteri(m_RendererID, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(m_RendererID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureSubImage2D(m_RendererID, 0, 0, 0, m_Width, m_Height, GL_RGB, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);
    }

    @Override
    public void destroy() {
        glDeleteTextures(m_RendererID);
    }

    @Override
    public int GetWidth() {
        return m_Width;
    }

    @Override
    public int GetHeight() {
        return m_Height;
    }

    @Override
    public void Bind() {
        glBindTextureUnit(0, m_RendererID);

    }

    @Override
    public void Bind(int slot) {
        glBindTextureUnit(slot, m_RendererID);
    }
}
