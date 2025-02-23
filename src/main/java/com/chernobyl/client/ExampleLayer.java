package com.chernobyl.client;


import com.chernobyl.gameengine.renderer.OrthographicCameraController;
import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.core.layer.Layer;
import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.*;
import com.chernobyl.gameengine.renderer.*;
import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.platform.opengl.OpenGLShader;
import imgui.ImGui;

class ExampleLayer extends Layer {
    private final OpenGLShader m_Shader;
    private final ShaderLibrary m_ShaderLibrary = new ShaderLibrary();
    private final OpenGLShader m_FlatColorShader;

    private final VertexArray m_VertexArray;
    private final VertexArray m_SquareVA;

    private final OrthographicCameraController m_CameraController;

    private Vec3 m_SquareColor = new Vec3( 0.2f, 0.3f, 0.8f );
    private final Texture2D m_Texture, m_ChernoLogoTexture;

    public ExampleLayer() {
        super("Example");

        m_CameraController = new OrthographicCameraController(1280.0f / 720.0f);

        m_VertexArray = VertexArray.Create();

        var vertices = new float[]{
                -0.5f, -0.5f, 0.0f, 0.8f, 0.2f, 0.8f, 1.0f,
                0.5f, -0.5f, 0.0f, 0.2f, 0.3f, 0.8f, 1.0f,
                0.0f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f
        };

        var vertexBuffer = VertexBuffer.Create(vertices, vertices.length);
        BufferLayout layout = new BufferLayout(new BufferElement[]{
                new BufferElement(ShaderDataType.Float3, "a_Position", false),
                new BufferElement(ShaderDataType.Float4, "a_Color", false)
        });
        vertexBuffer.SetLayout(layout);

        m_VertexArray.AddVertexBuffer(vertexBuffer);

        int[] indices = {0, 1, 2};
        var indexBuffer = IndexBuffer.Create(indices, indices.length);
        m_VertexArray.SetIndexBuffer(indexBuffer);

        m_SquareVA = VertexArray.Create();

        float[] squareVertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                 0.5f,  0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f, 0.0f, 0.0f, 1.0f
        };

        var squareVB = VertexBuffer.Create(squareVertices, squareVertices.length);
        squareVB.SetLayout(new BufferLayout(new BufferElement[]{
                new BufferElement( ShaderDataType.Float3, "a_Position", false),
                new BufferElement( ShaderDataType.Float2, "a_TexCoord", false)
        }));
        m_SquareVA.AddVertexBuffer(squareVB);

        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        var squareIB = IndexBuffer.Create(squareIndices, squareIndices.length);
        m_SquareVA.SetIndexBuffer(squareIB);

        String vertexSrc = """
            #version 330 core

            layout(location = 0) in vec3 a_Position;
            
            uniform mat4 u_ViewProjection;
            
            out vec3 v_Position;
            
            void main()
            {
                v_Position = a_Position;
                gl_Position = u_ViewProjection * vec4(a_Position, 1.0);
            }
        """;

        String fragmentSrc = """
            #version 330 core

            layout(location = 0) out vec4 color;
            
            in vec3 v_Position;
            in vec4 v_Color;
            
            void main()
            {
                color = vec4(v_Position * 0.5 + 0.5, 1.0);
            }
        """;

        m_Shader = (OpenGLShader) Shader.Create("VertexPosColor" ,vertexSrc, fragmentSrc);


        String flatColorShaderVertexSrc = """
            #version 410 core

            layout(location = 0) in vec3 a_Position;
            
            uniform mat4 u_Transform;
            uniform mat4 u_ViewProjection;
            
            out vec3 v_Position;
            void main()
            {
                v_Position = a_Position;
                gl_Position = u_ViewProjection * u_Transform * vec4(a_Position, 1.0);
            }
        """;

        String flatColorShaderFragmentSrc = """
            #version 410 core

            layout(location = 0) out vec4 color;
            
            in vec3 v_Position;
            
            uniform vec3 u_Color;
            
            void main()
            {
                color = vec4(u_Color, 1.0);
            }
        """;

        m_FlatColorShader = (OpenGLShader) Shader.Create("FlatColor",flatColorShaderVertexSrc, flatColorShaderFragmentSrc);

        var textureShader = (OpenGLShader) m_ShaderLibrary.Load("assets/shaders/Texture.glsl");

        m_Texture = Texture2D.Create("assets/textures/Checkerboard.png");
        m_ChernoLogoTexture = Texture2D.Create("assets/textures/ChernoLogo.png");
        textureShader.Bind();
        textureShader.UploadUniformInt("u_Texture", 0);
    }

    @Override
    public void OnAttach() {

    }

    @Override
    public void OnDetach() {

    }

    @Override
    public void OnUpdate(Timestep ts) {
        m_CameraController.OnUpdate(ts);

        RenderCommand.SetClearColor(new Vec4(0.1f, 0.1f, 0.1f, 1 ));
        RenderCommand.Clear();

        Renderer.BeginScene(m_CameraController.GetCamera());

        Mat4 scale = new Mat4().scale(new Vec3(0.1f));

        m_FlatColorShader.Bind();
        m_FlatColorShader.UploadUniformFloat3("u_Color", m_SquareColor);

        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++)
            {
                Vec3 pos = new Vec3(x * 0.11f, y * 0.11f, 0.0f);
                Mat4 transform = new Mat4().translate(pos).mul(scale);
                Renderer.Submit(m_FlatColorShader, m_SquareVA, transform);
            }
        }
        var textureShader = m_ShaderLibrary.Get("Texture");

        m_Texture.Bind();
        Renderer.Submit(textureShader, m_SquareVA, new Mat4().scale(new Vec3(1.5f)));

        m_ChernoLogoTexture.Bind();
        Renderer.Submit(textureShader, m_SquareVA, new Mat4().scale(new Vec3(1.5f)));

        // Triangle
        // Renderer.Submit(m_Shader, m_VertexArray);

        Renderer.EndScene();
    }

    @Override
    public void OnImGuiRender() {
        ImGui.begin("Settings");
        var arr = new float[] { m_SquareColor.x, m_SquareColor.y, m_SquareColor.z };
        ImGui.colorEdit3("Square Color", arr);
        m_SquareColor = new Vec3(arr[0], arr[1], arr[2]);
        ImGui.end();
    }

    @Override
    public void OnEvent(Event event) {
        m_CameraController.OnEvent(event);
    }
}
