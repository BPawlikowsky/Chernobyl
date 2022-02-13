package com.chernobyl.gameengine.renderer;

public class Renderer {
    private static final RendererAPI s_RendererAPI = RendererAPI.OpenGL;

    public static RendererAPI GetAPI() { return s_RendererAPI; }
}
