package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.platform.opengl.OpenGLRendererAPI;

public class RenderCommand {
    private static final RendererAPI s_RendererAPI = new OpenGLRendererAPI();

    static void SetViewport(int x, int y, int width, int height)
    {
        s_RendererAPI.SetViewport(x, y, width, height);
    }

    public static void SetClearColor(Vec4 color) {
        s_RendererAPI.SetClearColor(color);
    }

    public static void Clear() {
        s_RendererAPI.Clear();
    }

    public static void DrawIndexed(VertexArray vertexArray) {
        s_RendererAPI.DrawIndexed(vertexArray, 0);
    }

    public static void DrawIndexed(VertexArray vertexArray, int count) {
        s_RendererAPI.DrawIndexed(vertexArray, count);
    }

    public static void Init() {
        s_RendererAPI.Init();
    }
}
