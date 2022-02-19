package com.chernobyl.gameengine.render;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.OpenGLShader;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class Shader {
    public abstract void destroy();

    public abstract void Bind();
    public abstract void Unbind();

    public static Shader Create(String filepath)
    {
        switch (Renderer.GetAPI())
        {
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLShader(filepath);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public static Shader Create(String name, String vertexSrc, String fragmentSrc) {
        switch (Renderer.GetAPI()){
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLShader(name, vertexSrc, fragmentSrc);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }

    public abstract String GetName();
}
