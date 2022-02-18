package com.chernobyl.gameengine.render;

import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.OpenGLShader;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public abstract class Shader {
    public abstract void destroy();

    public abstract void Bind();
    public abstract void Unbind();

    public static Shader Create(String vertexSrc, String fragmentSrc) {
        switch (Renderer.GetAPI()){
            case None:    HB_CORE_ASSERT(false, "RendererAPI::None is currently not supported!"); return null;
            case OpenGL:  return new OpenGLShader(vertexSrc, fragmentSrc);
        }

        HB_CORE_ASSERT(false, "Unknown RendererAPI!");
        return null;
    }
}
