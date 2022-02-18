package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.render.Shader;

public class Renderer {
    private static class SceneData {
        Mat4 ViewProjectionMatrix;
    }
    private static final SceneData s_SceneData = new SceneData();
    private static final RendererAPI.API s_RendererAPI = RendererAPI.API.OpenGL;

    public static void BeginScene(OrthographicCamera camera) {
        s_SceneData.ViewProjectionMatrix = camera.GetViewProjectionMatrix();
    }

    public static void EndScene() {
    }

    public static void Submit(Shader shader, VertexArray vertexArray, Mat4 transform) {
        shader.Bind();
        shader.UploadUniformMat4("u_ViewProjection", s_SceneData.ViewProjectionMatrix);
        shader.UploadUniformMat4("u_Transform", transform);

        vertexArray.Bind();
        RenderCommand.DrawIndexed(vertexArray);
    }

    public static void Submit(Shader shader, VertexArray vertexArray) {
        shader.Bind();
        var transform = new Mat4(1.0f);
        shader.UploadUniformMat4("u_ViewProjection", s_SceneData.ViewProjectionMatrix);
        shader.UploadUniformMat4("u_Transform", transform);

        vertexArray.Bind();
        RenderCommand.DrawIndexed(vertexArray);
    }

    public static RendererAPI.API GetAPI() { return RendererAPI.GetAPI(); }
}
