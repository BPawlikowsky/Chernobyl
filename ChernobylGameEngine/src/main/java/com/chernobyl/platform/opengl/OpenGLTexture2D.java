package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.renderer.Texture2D;
import lombok.Getter;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.core.Instrumentor.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class OpenGLTexture2D extends Texture2D {
    private String m_Path;
    private final int m_Width;
    private final int m_Height;
    private final int m_RendererID;
    int m_InternalFormat, m_DataFormat;

    public OpenGLTexture2D(String path) {
        HB_PROFILE_FUNCTION();

        IntBuffer width, height, channels;
        ByteBuffer data;
        HB_PROFILE_SCOPE("stbi_load - OpenGLTexture2D::OpenGLTexture2D(const std:string&)");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            width = stack.callocInt(1);
            height = stack.callocInt(1);
            channels = stack.callocInt(1);
            stbi_set_flip_vertically_on_load(true);
            data = stbi_load(path, width, height, channels, 0);
        }
        HB_PROFILE_SCOPE_STOP("stbi_load - OpenGLTexture2D::OpenGLTexture2D(const std:string&)");
        HB_CORE_ASSERT(data != null, "Failed to load image!");
        m_Width = width.get(0);
        m_Height = height.get(0);

        int internalFormat = 0, dataFormat = 0;
        if (channels.get(0) == 4)
        {
            internalFormat = GL_RGBA8;
            dataFormat = GL_RGBA;
        }
        else if (channels.get(0) == 3)
        {
            internalFormat = GL_RGB8;
            dataFormat = GL_RGB;
        }

        m_InternalFormat = internalFormat;
        m_DataFormat = dataFormat;

        HB_CORE_ASSERT(internalFormat != 0 && dataFormat != 0, "Format not supported!");

        m_RendererID = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(m_RendererID, 1, internalFormat, m_Width, m_Height);

        glTextureParameteri(m_RendererID, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(m_RendererID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(m_RendererID, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(m_RendererID, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTextureSubImage2D(m_RendererID, 0, 0, 0, m_Width, m_Height, dataFormat, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);

        HB_PROFILE_FUNCTION_STOP();
    }

    public OpenGLTexture2D(int width, int height) {
        HB_PROFILE_FUNCTION();

        m_Width = width;
        m_Height = height;
        m_InternalFormat = GL_RGBA8;
        m_DataFormat = GL_RGBA;

        m_RendererID = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(m_RendererID, 1, m_InternalFormat, m_Width, m_Height);

        glTextureParameteri(m_RendererID, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(m_RendererID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(m_RendererID, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(m_RendererID, GL_TEXTURE_WRAP_T, GL_REPEAT);

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void destroy() {
        HB_PROFILE_FUNCTION();

        glDeleteTextures(m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
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
    public void SetData(long data, int size) {
        HB_PROFILE_FUNCTION();

        int bpp = m_DataFormat == GL_RGBA ? 4 : 3;
        HB_CORE_ASSERT(size == m_Width * m_Height * bpp, "Data must be entire texture!");
        glTextureSubImage2D(m_RendererID, 0, 0, 0, m_Width, m_Height, m_DataFormat, GL_UNSIGNED_BYTE, data);

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public int GetRendererID() {
        return m_RendererID;
    }

    @Override
    public void Bind() {
        HB_PROFILE_FUNCTION();

        glBindTextureUnit(0, m_RendererID);

        HB_PROFILE_FUNCTION_STOP();

    }

    @Override
    public void Bind(int slot) {
        HB_PROFILE_FUNCTION();

        glBindTextureUnit(slot, m_RendererID);

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())) {
            return this.m_RendererID == ((OpenGLTexture2D) obj).m_RendererID;
        }
        return false;
    }
}
