package com.chernobyl.gameengine.renderer;

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
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;

public class Renderer2D {
    static class Renderer2DStorage
    {
        VertexArray QuadVertexArray;
        Shader TextureShader;
        Texture2D WhiteTexture;
    }

    static Renderer2DStorage s_Data;

    public static void Init()
    {
        HB_PROFILE_FUNCTION();

        s_Data = new Renderer2DStorage();
        s_Data.QuadVertexArray = VertexArray.Create();

        float[] squareVertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                 0.5f,  0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f, 0.0f, 0.0f, 1.0f
    };

        VertexBuffer squareVB = VertexBuffer.Create(squareVertices, squareVertices.length);
        squareVB.SetLayout(new BufferLayout(new BufferElement[]{
                new BufferElement(ShaderDataType.Float3, "a_Position"),
                new BufferElement(ShaderDataType.Float2, "a_TexCoord")
        }));
        s_Data.QuadVertexArray.AddVertexBuffer(squareVB);


        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        IndexBuffer squareIB = IndexBuffer.Create(squareIndices, squareIndices.length);
        s_Data.QuadVertexArray.SetIndexBuffer(squareIB);

        s_Data.WhiteTexture = Texture2D.Create(1, 1);
        int whiteTextureData = 0xffffffff;
        ByteBuffer buf;
        try(MemoryStack stack = MemoryStack.stackPush()) {
            buf = stack.calloc(4);
            buf.putInt(whiteTextureData);
            buf.flip();
        }
        s_Data.WhiteTexture.SetData(MemoryUtil.memAddress(buf), Integer.BYTES);

        s_Data.TextureShader = Shader.Create("assets/shaders/Texture.glsl");
        s_Data.TextureShader.Bind();
        s_Data.TextureShader.SetInt("u_Texture", 0);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void Shutdown()
    {
        s_Data.QuadVertexArray.destroy();
        s_Data.TextureShader.destroy();
        s_Data.WhiteTexture.destroy();
        s_Data = null;
    }

    public static void BeginScene(OrthographicCamera camera)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.Bind();
        s_Data.TextureShader.SetMat4("u_ViewProjection", camera.GetViewProjectionMatrix());

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void EndScene()
    {
        HB_PROFILE_FUNCTION();

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawQuad(Vec2 position, Vec2 size, Vec4 color)
    {
        DrawQuad(new Vec3( position.x, position.y, 0.0f ), size, color);
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Vec4 color)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", color);
        s_Data.TextureShader.SetFloat("u_TilingFactor", 1.0f);
        s_Data.WhiteTexture.Bind();

        Mat4 transform = new Mat4().translate(position)
                .scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.TextureShader.SetMat4("u_Transform", transform);

        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }
    public static void DrawQuad(Vec2 position, Vec2 size, Vec4 color, Vec4 tintColor, float tilingFactor)
    {
        DrawQuad(new Vec3( position.x, position.y, 0.0f ), size, color, tintColor, tilingFactor);
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Vec4 color, Vec4 tintColor, float tilingFactor)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", color);
        s_Data.TextureShader.SetFloat("u_TilingFactor", tilingFactor);
        s_Data.WhiteTexture.Bind();

        Mat4 transform = new Mat4().translate(position)
                .scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.TextureShader.SetMat4("u_Transform", transform);

        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Texture2D texture)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", new Vec4(1.0f));
        s_Data.TextureShader.SetFloat("u_TilingFactor", 1.0f);
        texture.Bind();

        Mat4 transform = new Mat4().translate(position).scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.TextureShader.SetMat4("u_Transform", transform);

        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Texture2D texture, float tilingFactor)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", new Vec4(1.0f));
        s_Data.TextureShader.SetFloat("u_TilingFactor", tilingFactor);
        texture.Bind();

        Mat4 transform = new Mat4().translate(position).scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.TextureShader.SetMat4("u_Transform", transform);

        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawRotatedQuad(Vec2 position, Vec2 size, float rotation, Vec4 color)
    {
        DrawRotatedQuad(new Vec3( position.x, position.y, 0.0f ), size, rotation, color);
    }

    public static void DrawRotatedQuad(Vec3 position, Vec2 size, float rotation, Vec4 color)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", color);
        s_Data.TextureShader.SetFloat("u_TilingFactor", 1.0f);
        s_Data.WhiteTexture.Bind();

        Mat4 transform = new Mat4().translate(position)
                .rotate(rotation, new Vec3( 0.0f, 0.0f, 1.0f ))
                .scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.TextureShader.SetMat4("u_Transform", transform);
        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawRotatedQuad(Vec2 position, Vec2 size, float rotation, Texture2D texture, float tilingFactor, Vec4 tintColor)
    {
        DrawRotatedQuad(new Vec3( position.x, position.y, 0.0f ), size, rotation, texture, tilingFactor, tintColor);
    }

    public static void DrawRotatedQuad(Vec3 position, Vec2 size, float rotation, Texture2D texture, float tilingFactor, Vec4 tintColor)
    {
        HB_PROFILE_FUNCTION();

        s_Data.TextureShader.SetFloat4("u_Color", tintColor);
        s_Data.TextureShader.SetFloat("u_TilingFactor", tilingFactor);
        texture.Bind();

        Mat4 transform = new Mat4().translate(position)
                .rotate(rotation, new Vec3( 0.0f, 0.0f, 1.0f ))
                .scale(new Vec3( size.x, size.y, 1.0f ));
        s_Data.QuadVertexArray.Bind();
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray);

        HB_PROFILE_FUNCTION_STOP();
    }
}
