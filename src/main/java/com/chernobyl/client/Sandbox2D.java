package com.chernobyl.client;

import com.chernobyl.gameengine.OrthographicCameraController;
import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.layer.Layer;
import com.chernobyl.gameengine.math.Vec2;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.renderer.RenderCommand;
import com.chernobyl.gameengine.renderer.Renderer2D;
import com.chernobyl.gameengine.renderer.Texture2D;
import com.chernobyl.gameengine.renderer.VertexArray;
import imgui.ImGui;

public class Sandbox2D extends Layer {
    private final OrthographicCameraController m_CameraController;

    // Temp
    private VertexArray m_SquareVA;
    private Shader m_FlatColorShader;

    private Vec4 m_SquareColor = new Vec4( 0.2f, 0.3f, 0.8f, 1.0f );
    private Texture2D m_CheckerboardTexture;

    public Sandbox2D()
    {
	    super("Sandbox2D");
        m_CameraController = new OrthographicCameraController(1280.0f / 720.0f);
    }

    public void OnAttach()
    {
        m_CheckerboardTexture = Texture2D.Create("assets/textures/Checkerboard.png");
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

        Renderer2D.BeginScene(m_CameraController.GetCamera());
        Renderer2D.DrawQuad(new Vec2( -1.0f, 0.0f ), new Vec2( 0.8f, 0.8f ), new Vec4( 0.8f, 0.2f, 0.3f, 1.0f ));
        Renderer2D.DrawQuad(new Vec2( 0.5f, -0.5f ), new Vec2( 0.5f, 0.75f ), new Vec4( 0.2f, 0.3f, 0.8f, 1.0f ));
        Renderer2D.DrawQuad(new Vec3( 0.0f, 0.0f, -0.1f ), new Vec2( 10.0f, 10.0f ), m_CheckerboardTexture);
        Renderer2D.EndScene();
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
