package com.chernobyl.gameengine.renderer;

import com.chernobyl.platform.opengl.OpenGLTexture2D;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class Texture2D extends Texture{

    public static Texture2D Create(int width, int height)
    {
        switch (Renderer.GetAPI())
        {
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLTexture2D(width, height);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public static OpenGLTexture2D Create(String path)
    {
        switch (Renderer.GetAPI())
        {
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLTexture2D(path);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }
}
