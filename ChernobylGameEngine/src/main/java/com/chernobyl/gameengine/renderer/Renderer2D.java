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
import java.util.ArrayList;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static com.chernobyl.gameengine.math.Math.toRadians;

public class Renderer2D {
    private static class QuadVertex {
        Vec3 Position;
        Vec4 Color;
        Vec2 TexCoord;
        float TexIndex;
        float TilingFactor;
        // TODO: texid

        public QuadVertex() {
            Position = new Vec3();
            Color = new Vec4();
            TexCoord = new Vec2();
        }
        public QuadVertex(Vec3 position, Vec4 color, Vec2 texCoord) {
            Position = position;
            Color = color;
            TexCoord = texCoord;
        }
    }

    static class Renderer2DData
    {
        int MaxQuads = 100;
		int MaxVertices = MaxQuads * 4;
		int MaxIndices = MaxQuads * 6;
        static final int MaxTextureSlots = 32;

        VertexArray QuadVertexArray;
        VertexBuffer QuadVertexBuffer;
        Shader TextureShader;
        Texture2D WhiteTexture;

        int QuadIndexCount = 0;
        ArrayList<QuadVertex> QuadVertexBufferBase;
        int QuadVertexBufferPtr = 0;

        final Texture2D[] TextureSlots = new Texture2D[MaxTextureSlots];
        int TextureSlotIndex = 1; // 0 = white texture

        Vec4[] QuadVertexPositions = new Vec4[4];

    }


    static Renderer2DData s_Data = new Renderer2DData();

    public static void Init()
    {
        HB_PROFILE_FUNCTION();

        s_Data.QuadVertexArray = VertexArray.Create();
        s_Data.QuadVertexBuffer = VertexBuffer.Create(s_Data.MaxVertices * Float.BYTES);

        s_Data.QuadVertexBuffer.SetLayout(new BufferLayout(new BufferElement[]{
                new BufferElement(ShaderDataType.Float3, "a_Position"),
                new BufferElement(ShaderDataType.Float4, "a_Color"),
                new BufferElement(ShaderDataType.Float2, "a_TexCoord" ),
                new BufferElement(ShaderDataType.Float, "a_TexIndex" ),
                new BufferElement(ShaderDataType.Float, "a_TilingFactor" )
        }));
        s_Data.QuadVertexArray.AddVertexBuffer(s_Data.QuadVertexBuffer);

        s_Data.QuadVertexBufferBase = new ArrayList<>();

        int[] quadIndices = new int[s_Data.MaxIndices];

        int offset = 0;
        for (int i = 0; i < s_Data.MaxIndices; i += 6)
        {
            quadIndices[i + 0] = offset + 0;
            quadIndices[i + 1] = offset + 1;
            quadIndices[i + 2] = offset + 2;

            quadIndices[i + 3] = offset + 2;
            quadIndices[i + 4] = offset + 3;
            quadIndices[i + 5] = offset + 0;

            offset += 4;
        }


        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        IndexBuffer quadIB = IndexBuffer.Create(quadIndices, s_Data.MaxIndices);
        s_Data.QuadVertexArray.SetIndexBuffer(quadIB);


        s_Data.WhiteTexture = Texture2D.Create(1, 1);
        s_Data.WhiteTexture = Texture2D.Create(1, 1);
        int whiteTextureData = 0xffffffff;
        ByteBuffer buf;
        try(MemoryStack stack = MemoryStack.stackPush()) {
            buf = stack.calloc(4);
            buf.putInt(whiteTextureData);
            buf.flip();
        }
        s_Data.WhiteTexture.SetData(MemoryUtil.memAddress(buf), Integer.BYTES);

        int[] samplers = new int[s_Data.MaxTextureSlots];
        for (int i = 0; i < s_Data.MaxTextureSlots; i++)
            samplers[i] = i;

        s_Data.TextureShader = Shader.Create("assets/shaders/Texture.glsl");
        s_Data.TextureShader.Bind();

        s_Data.TextureShader.SetIntArray("u_Textures", samplers, s_Data.MaxTextureSlots);

        // Set all texture slots to 0
        s_Data.TextureSlots[0] = s_Data.WhiteTexture;

        s_Data.QuadVertexPositions[0] = new Vec4( -0.5f, -0.5f, 0.0f, 1.0f );
        s_Data.QuadVertexPositions[1] = new Vec4(  0.5f, -0.5f, 0.0f, 1.0f );
        s_Data.QuadVertexPositions[2] = new Vec4(  0.5f,  0.5f, 0.0f, 1.0f );
        s_Data.QuadVertexPositions[3] = new Vec4( -0.5f,  0.5f, 0.0f, 1.0f );

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

        s_Data.QuadIndexCount = 0;
        s_Data.QuadVertexBufferPtr = 0;
        s_Data.QuadVertexBufferBase.clear();

        s_Data.TextureSlotIndex = 1;

        HB_PROFILE_FUNCTION_STOP();
    }

    private static void Flush()
    {
        // Bind textures
        for (int i = 0; i < s_Data.TextureSlotIndex; i++)
            s_Data.TextureSlots[i].Bind(i);
        RenderCommand.DrawIndexed(s_Data.QuadVertexArray, s_Data.QuadIndexCount);
    }

    public static void EndScene()
    {
        HB_PROFILE_FUNCTION();

        int dataSize = s_Data.QuadVertexBufferBase.size();
        var b = new float[s_Data.MaxVertices];
        int i = 0;
        for (var el : s_Data.QuadVertexBufferBase) {
                b[i++] = el.Position.x; b[i++] = el.Position.y; b[i++] = el.Position.z;
                b[i++] = el.Color.x; b[i++] = el.Color.y; b[i++] = el.Color.z; b[i++] = el.Color.w;
                b[i++] = el.TexCoord.x; b[i++] = el.TexCoord.y;
                b[i++] = el.TexIndex; b[i++] = el.TilingFactor;
        }
        s_Data.QuadVertexBuffer.SetData( b, dataSize);

        Flush();
        HB_PROFILE_FUNCTION_STOP();
    }


    public static void DrawQuad(Vec2 position, Vec2 size, Vec4 color)
    {
        DrawQuad(new Vec3( position.x, position.y, 0.0f ), size, color);
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Vec4 color)
    {
        HB_PROFILE_FUNCTION();

        final float textureIndex = 0.0f; // White Texture
		final float tilingFactor = 1.0f;

        Mat4 transform = new Mat4().translate(position)
                .scale(new Vec3( size.x, size.y, 1.0f ));


        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[0]),
                color,
                new Vec2(0.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[1]),
                color,
                new Vec2(1.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[2]),
                color,
                new Vec2(1.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[3]),
                color,
                new Vec2(0.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadIndexCount += 6;

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Texture2D texture)
    {
        HB_PROFILE_FUNCTION();

        DrawQuad(position, size, texture, 1.0f);

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawQuad(Vec3 position, Vec2 size, Texture2D texture, float tilingFactor)
    {
        HB_PROFILE_FUNCTION();

        final Vec4 color = new Vec4( 1.0f, 1.0f, 1.0f, 1.0f );

        float textureIndex = 0.0f;
        for (int i = 1; i < s_Data.TextureSlotIndex; i++)
        {
            if (s_Data.TextureSlots[i].equals(texture))
            {
                textureIndex = (float)i;
                break;
            }
        }

        if (textureIndex == 0.0f)
        {
            textureIndex = (float)s_Data.TextureSlotIndex;
            s_Data.TextureSlots[s_Data.TextureSlotIndex] = texture;
            s_Data.TextureSlotIndex++;
        }


        Mat4 transform = new Mat4().translate(position)
                .scale(new Vec3( size.x, size.y, 1.0f ));


        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[0]),
                color,
                new Vec2(0.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[1]),
                color,
                new Vec2(1.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[2]),
                color,
                new Vec2(1.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[3]),
                color,
                new Vec2(0.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadIndexCount += 6;

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawRotatedQuad(Vec2 position, Vec2 size, float rotation, Vec4 color)
    {
        DrawRotatedQuad(new Vec3( position.x, position.y, 0.0f ), size, rotation, color);
    }

    public static void DrawRotatedQuad(Vec3 position, Vec2 size, float rotation, Vec4 color)
    {
        HB_PROFILE_FUNCTION();

        final float textureIndex = 0.0f; // White Texture
		final float tilingFactor = 1.0f;

        Mat4 transform = new Mat4().translate(position)
                .rotate(toRadians(rotation), new Vec3( 0.0f, 0.0f, 1.0f ))
                .scale(new Vec3( size.x, size.y, 1.0f ));

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[0]),
                color,
                new Vec2(0.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[1]),
                color,
                new Vec2(1.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[2]),
                color,
                new Vec2(1.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[3]),
                color,
                new Vec2(0.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadIndexCount += 6;

        HB_PROFILE_FUNCTION_STOP();
    }

    public static void DrawRotatedQuad(Vec2 position, Vec2 size, float rotation, Texture2D texture, float tilingFactor, Vec4 tintColor)
    {
        DrawRotatedQuad(new Vec3( position.x, position.y, 0.0f ), size, rotation, texture, tilingFactor, tintColor);
    }

    public static void DrawRotatedQuad(Vec3 position, Vec2 size, float rotation, Texture2D texture, float tilingFactor)
    {
        final Vec4 color = new Vec4( 1.0f, 1.0f, 1.0f, 1.0f );
        DrawRotatedQuad(position, size, rotation, texture, tilingFactor, color);
    }

    public static void DrawRotatedQuad(Vec3 position, Vec2 size, float rotation, Texture2D texture, float tilingFactor, Vec4 tintColor)
    {
        HB_PROFILE_FUNCTION();

        final Vec4 color = new Vec4( 1.0f, 1.0f, 1.0f, 1.0f );

        float textureIndex = 0.0f;
        for (int i = 1; i < s_Data.TextureSlotIndex; i++)
        {
            if (s_Data.TextureSlots[i].equals(texture))
            {
                textureIndex = (float)i;
                break;
            }
        }

        if (textureIndex == 0.0f)
        {
            textureIndex = (float)s_Data.TextureSlotIndex;
            s_Data.TextureSlots[s_Data.TextureSlotIndex] = texture;
            s_Data.TextureSlotIndex++;
        }

        Mat4 transform = new Mat4().translate(position)
                .rotate(toRadians(rotation), new Vec3( 0.0f, 0.0f, 1.0f ))
                .scale(new Vec3( size.x, size.y, 1.0f ));

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[0]),
                color,
                new Vec2(0.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[1]),
                color,
                new Vec2(1.0f, 0.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[2]),
                color,
                new Vec2(1.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadVertexBufferBase.add(new QuadVertex(
                transform.mul(s_Data.QuadVertexPositions[3]),
                color,
                new Vec2(0.0f, 1.0f)));
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TexIndex = textureIndex;
        s_Data.QuadVertexBufferBase.get(s_Data.QuadVertexBufferPtr).TilingFactor = tilingFactor;
        s_Data.QuadVertexBufferPtr++;

        s_Data.QuadIndexCount += 6;
        
        HB_PROFILE_FUNCTION_STOP();
    }
}
