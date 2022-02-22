package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.OrthographicCamera;
import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.math.Vec2;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.BufferElement;
import com.chernobyl.gameengine.render.BufferLayout;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.render.ShaderDataType;

public class Renderer2D {
    static class Renderer2DStorage
    {
        VertexArray QuadVertexArray;
        Shader FlatColorShader;
    }

    static Renderer2DStorage s_Data;

    public static void Init()
    {
        s_Data = new Renderer2DStorage();
        s_Data.QuadVertexArray = VertexArray.Create();

        float[] squareVertices = {
                -0.5f, -0.5f, 0.0f,
                 0.5f, -0.5f, 0.0f,
                 0.5f,  0.5f, 0.0f,
                -0.5f,  0.5f, 0.0f
    };

        VertexBuffer squareVB = VertexBuffer.Create(squareVertices, squareVertices.length);
        squareVB.SetLayout(new BufferLayout(new BufferElement[]{
                new BufferElement(ShaderDataType.Float3, "a_Position")
        }));
        s_Data.QuadVertexArray.AddVertexBuffer(squareVB);

        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        IndexBuffer
        squareIB = IndexBuffer.Create(squareIndices, squareIndices.length);
        s_Data.QuadVertexArray.SetIndexBuffer(squareIB);

        s_Data.FlatColorShader = Shader.Create("assets/shaders/FlatColor.glsl");
    }

    public static void Shutdown()
    {
        s_Data = null;
    }

    public static void BeginScene(OrthographicCamera camera)
    {
        s_Data.FlatColorShader.Bind();
        s_Data.FlatColorShader.UploadUniformMat4("u_ViewProjection", camera.GetViewProjectionMatrix());
        s_Data.FlatColorShader.UploadUniformMat4("u_Transform", new Mat4(1.0f));
    }

    public static void EndScene()
    {

    }

    public static void DrawQuad(Vec2 position, Vec2 size, Vec4 color)
    {
        DrawQuad(new Vec3( position.x, position.y, 0.0f ), size, color);
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Vec4 color)
    {
        s_Data.FlatColorShader.Bind();
        s_Data.FlatColorShader.UploadUniformFloat4("u_Color", color);

        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);
    }
}
