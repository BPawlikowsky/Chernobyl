package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.platform.opengl.OpenGLShader;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;

public class Renderer {
    public static void Init() {
        HB_PROFILE_FUNCTION();

        RenderCommand.Init();
        Renderer2D.Init();

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void Shutdown() {
        Renderer2D.Shutdown();
    }

    private static class SceneData {
        Mat4 ViewProjectionMatrix;
    }
    private static final SceneData s_SceneData = new SceneData();
    private static final RendererAPI.API s_RendererAPI = RendererAPI.API.OpenGL;

    public static void OnWindowResize(int width, int height)
    {
        RenderCommand.SetViewport(0, 0, width, height);
    }

    public static void BeginScene(OrthographicCamera camera) {
        s_SceneData.ViewProjectionMatrix = camera.GetViewProjectionMatrix();
    }

    public static void EndScene() {
    }

    public static void Submit(Shader shader, VertexArray vertexArray, Mat4 transform) {
        OpenGLShader glShader = (OpenGLShader) shader;
        glShader.Bind();
        glShader.UploadUniformMat4("u_ViewProjection", s_SceneData.ViewProjectionMatrix);
        glShader.UploadUniformMat4("u_Transform", transform);

        vertexArray.Bind();
        RenderCommand.DrawIndexed(vertexArray);
    }

    public static void Submit(Shader shader, VertexArray vertexArray) {
        OpenGLShader glShader = (OpenGLShader) shader;
        glShader.Bind();
        var transform = new Mat4(1.0f);
        glShader.UploadUniformMat4("u_ViewProjection", s_SceneData.ViewProjectionMatrix);
        glShader.UploadUniformMat4("u_Transform", transform);

        vertexArray.Bind();
        RenderCommand.DrawIndexed(vertexArray);
    }

    public static RendererAPI.API GetAPI() { return RendererAPI.GetAPI(); }
}
