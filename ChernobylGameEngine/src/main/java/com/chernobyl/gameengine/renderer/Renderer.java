package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.render.Shader;

public class Renderer {
    private static class SceneData {
        Mat4 ViewProjectionMatrix;
    }
    private static final SceneData m_SceneData = new SceneData();
    private static final RendererAPI.API s_RendererAPI = RendererAPI.API.OpenGL;

    public static void BeginScene(OrthographicCamera camera) {
        m_SceneData.ViewProjectionMatrix = camera.GetViewProjectionMatrix();
    }

    public static void EndScene() {

    }

    public static void Submit(Shader shader, VertexArray vertexArray) {
        shader.Bind();
        shader.UploadUniformMat4("u_ViewProjection", m_SceneData.ViewProjectionMatrix);

        vertexArray.Bind();
        RenderCommand.DrawIndexed(vertexArray);
    }

    public static RendererAPI.API GetAPI() { return RendererAPI.GetAPI(); }
}
