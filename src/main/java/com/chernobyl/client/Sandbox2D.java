package com.chernobyl.client;

import com.chernobyl.gameengine.OrthographicCameraController;
import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.layer.Layer;
import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.BufferElement;
import com.chernobyl.gameengine.render.BufferLayout;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.render.ShaderDataType;
import com.chernobyl.gameengine.renderer.RenderCommand;
import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.gameengine.renderer.VertexArray;
import imgui.ImGui;

public class Sandbox2D extends Layer {
    private final OrthographicCameraController m_CameraController;

    // Temp
    private VertexArray m_SquareVA;
    private Shader m_FlatColorShader;

    private Vec4 m_SquareColor = new Vec4( 0.2f, 0.3f, 0.8f, 1.0f );

    public Sandbox2D()
    {
	    super("Sandbox2D");
        m_CameraController = new OrthographicCameraController(1280.0f / 720.0f);
    }

    public void OnAttach()
    {
        m_SquareVA = VertexArray.Create();

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
        m_SquareVA.AddVertexBuffer(squareVB);

        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        IndexBuffer squareIB = IndexBuffer.Create(squareIndices, squareIndices.length);
        m_SquareVA.SetIndexBuffer(squareIB);

        m_FlatColorShader =  Shader.Create("assets/shaders/FlatColor.glsl");
    }

    public void OnDetach()
    {
    }

    public void OnUpdate(Timestep ts)
    {
        // Update
        m_CameraController.OnUpdate(ts);

        // Render
        RenderCommand.SetClearColor(new Vec4( 0.1f, 0.1f, 0.1f, 1 ));
        RenderCommand.Clear();

        Renderer.BeginScene(m_CameraController.GetCamera());

        m_FlatColorShader.Bind();
        m_FlatColorShader.UploadUniformFloat4("u_Color", m_SquareColor);

        Renderer.Submit(m_FlatColorShader, m_SquareVA, new Mat4().scale(new Vec3(1.5f)));

        Renderer.EndScene();
    }

    public void OnImGuiRender()
    {
        ImGui.begin("Settings");
        float[] arr = { m_SquareColor.x, m_SquareColor.y, m_SquareColor.z, m_SquareColor.w };
        ImGui.colorEdit4("Square Color", arr);
        m_SquareColor = new Vec4(arr[0], arr[1], arr[2], arr[3]);
        ImGui.end();
    }

    public void OnEvent(Event e)
    {
        m_CameraController.OnEvent(e);
    }
}
