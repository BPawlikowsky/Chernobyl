package com.chernobyl.client;

import com.chernobyl.gameengine.renderer.OrthographicCameraController;
import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.core.layer.Layer;
import com.chernobyl.gameengine.math.Vec2;
import com.chernobyl.gameengine.math.Vec3;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.renderer.RenderCommand;
import com.chernobyl.gameengine.renderer.Renderer2D;
import com.chernobyl.gameengine.renderer.Texture2D;
import com.chernobyl.gameengine.renderer.VertexArray;
import imgui.ImGui;

import static com.chernobyl.gameengine.core.Instrumentor.*;
import static com.chernobyl.gameengine.core.Instrumentor.ProfileResults;
import static com.chernobyl.gameengine.math.Math.toRadians;

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
        HB_PROFILE_FUNCTION();

        m_CheckerboardTexture = Texture2D.Create("assets/textures/Checkerboard.png");

        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnDetach()
    {
        HB_PROFILE_FUNCTION();

        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnUpdate(Timestep ts)
    {
        HB_PROFILE_FUNCTION();

        HB_PROFILE_SCOPE("Sandbox2D::OnUpdate");
        // Update
        HB_PROFILE_SCOPE("CameraController::OnUpdate");
        m_CameraController.OnUpdate(ts);
        HB_PROFILE_SCOPE_STOP("CameraController::OnUpdate");

        // Render
        HB_PROFILE_SCOPE("Renderer Prep");
        RenderCommand.SetClearColor(new Vec4( 0.1f, 0.1f, 0.1f, 1 ));
        RenderCommand.Clear();
        HB_PROFILE_SCOPE_STOP("Renderer Prep");

        HB_PROFILE_SCOPE("Renderer Draw");
        Renderer2D.BeginScene(m_CameraController.GetCamera());
        Renderer2D.DrawQuad(new Vec2( -1.0f, 0.0f ), new Vec2( 0.8f, 0.8f ), new Vec4( 0.8f, 0.2f, 0.3f, 1.0f ));
        Renderer2D.DrawRotatedQuad(new Vec2( -1.0f, 2.0f ), new Vec2( 0.8f, 0.8f ), toRadians(45f), new Vec4( 0.2f, 0.3f, 0.8f, 1.0f ));
        Renderer2D.DrawQuad(new Vec2( 0.5f, -0.5f ), new Vec2( 0.5f, 0.75f ), new Vec4( 0.2f, 0.3f, 0.8f, 1.0f ));
        Renderer2D.DrawQuad(new Vec3( 0.0f, 0.0f, -0.1f ), new Vec2( 10.0f, 10.0f ), m_CheckerboardTexture);
        Renderer2D.EndScene();
        HB_PROFILE_SCOPE_STOP("Renderer Draw");

        HB_PROFILE_SCOPE_STOP("Sandbox2D::OnUpdate");

        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnImGuiRender()
    {
        HB_PROFILE_FUNCTION();
        ImGui.begin("Settings");
        float[] arr = { m_SquareColor.x, m_SquareColor.y, m_SquareColor.z, m_SquareColor.w };
        ImGui.colorEdit4("Square Color", arr);
        m_SquareColor = new Vec4(arr[0], arr[1], arr[2], arr[3]);
        ImGui.end();

        for (var result : ProfileResults)
        {
            String label = result.Name +
                    String.format(" %.3fms ", result.Time);
            ImGui.text(label);
        }
        ProfileResults.clear();
        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnEvent(Event e)
    {
        m_CameraController.OnEvent(e);
    }
}
