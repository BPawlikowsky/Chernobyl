package com.chernobyl.client;


import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.event.EventDispatcher;
import com.chernobyl.gameengine.event.KeyPressedEvent;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.input.Input;
import com.chernobyl.gameengine.layer.Layer;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.BufferElement;
import com.chernobyl.gameengine.render.BufferLayout;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.render.ShaderDataType;
import com.chernobyl.gameengine.renderer.OrthographicCamera;
import com.chernobyl.gameengine.renderer.RenderCommand;
import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.gameengine.renderer.VertexArray;
import com.chernobyl.gameengine.core.Timestep;
import imgui.ImGui;

import static com.chernobyl.gameengine.Log.*;
import static com.chernobyl.gameengine.input.KeyCodes.*;

class ExampleLayer extends Layer {
    private final Shader m_Shader;
    private final VertexArray m_VertexArray;

    private final Shader m_BlueShader;
    private final VertexArray m_SquareVA;

    private final OrthographicCamera m_Camera;
    private final Vec3 m_CameraPosition = new Vec3();
    private final float m_CameraMoveSpeed = 5.0f;

    private float m_CameraRotation = 0.0f;
    private final float m_CameraRotationSpeed = 180.0f;

    public ExampleLayer() {
        super("Example");

        m_Camera = new OrthographicCamera(-1.6f, 1.6f, -0.9f, 0.9f);

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
                -0.75f, -0.75f, 0.0f,
                0.75f, -0.75f, 0.0f,
                0.75f,  0.75f, 0.0f,
                -0.75f,  0.75f, 0.0f
        };

        var squareVB = VertexBuffer.Create(squareVertices, squareVertices.length);
        squareVB.SetLayout(new BufferLayout(new BufferElement[]{
                new BufferElement( ShaderDataType.Float3, "a_Position", false)
        }));
        m_SquareVA.AddVertexBuffer(squareVB);

        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        var squareIB = IndexBuffer.Create(squareIndices, squareIndices.length);
        m_SquareVA.SetIndexBuffer(squareIB);

        String vertexSrc = """
            #version 330 core

            layout(location = 0) in vec3 a_Position;
            layout(location = 1) in vec4 a_Color;
            
            uniform mat4 u_ViewProjection;
            
            out vec3 v_Position;
            out vec4 v_Color;
            
            void main()
            {
                v_Position = a_Position;
                v_Color = a_Color;
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
                color = v_Color;
            }
        """;

        m_Shader = new Shader(vertexSrc, fragmentSrc);

        String blueShaderVertexSrc = """
            #version 410 core

            layout(location = 0) in vec3 a_Position;
            uniform mat4 u_ViewProjection;
            out vec3 v_Position;
            void main()
            {
                v_Position = a_Position;
                gl_Position = u_ViewProjection * vec4(a_Position, 1.0);
            }
        """;

        String blueShaderFragmentSrc = """
            #version 410 core

            layout(location = 0) out vec4 color;
            in vec3 v_Position;
            void main()
            {
                color = vec4(0.2, 0.3, 0.8, 1.0);
            }
        """;

        m_BlueShader = new Shader(blueShaderVertexSrc, blueShaderFragmentSrc);

    }

    @Override
    public void OnAttach() {

    }

    @Override
    public void OnDetach() {

    }

    @Override
    public void OnUpdate(Timestep ts) {
//        m_CameraPosition.zero();
//        m_CameraRotation = 0.0f;

        if (Input.IsKeyPressed(HB_KEY_LEFT))
        m_CameraPosition.x -= m_CameraMoveSpeed * ts.GetSeconds();
		else if (Input.IsKeyPressed(HB_KEY_RIGHT))
        m_CameraPosition.x += m_CameraMoveSpeed * ts.GetSeconds();

        if (Input.IsKeyPressed(HB_KEY_UP))
        m_CameraPosition.y += m_CameraMoveSpeed * ts.GetSeconds();
		else if (Input.IsKeyPressed(HB_KEY_DOWN))
        m_CameraPosition.y -= m_CameraMoveSpeed * ts.GetSeconds();

        if (Input.IsKeyPressed(HB_KEY_A))
        m_CameraRotation += m_CameraRotationSpeed * ts.GetSeconds();
        if (Input.IsKeyPressed(HB_KEY_D))
        m_CameraRotation -= m_CameraRotationSpeed * ts.GetSeconds();

        RenderCommand.SetClearColor(new Vec4(0.1f, 0.1f, 0.1f, 1 ));
        RenderCommand.Clear();

        m_Camera.SetPosition(m_CameraPosition);
        m_Camera.SetRotation(m_CameraRotation);

        Renderer.BeginScene(m_Camera);

        Renderer.Submit(m_BlueShader, m_SquareVA);
        Renderer.Submit(m_Shader, m_VertexArray);

        Renderer.EndScene();
    }

    @Override
    public void OnImGuiRender() {
        ImGui.begin("Test");
        ImGui.text("Hello World");
        ImGui.end();
    }

    @Override
    public void OnEvent(Event event) {
    }
}
