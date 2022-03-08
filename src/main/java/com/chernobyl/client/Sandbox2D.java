package com.chernobyl.client;

import com.chernobyl.gameengine.core.Application;
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
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import static com.chernobyl.gameengine.core.Instrumentor.*;
import static com.chernobyl.gameengine.renderer.Renderer2D.GetStats;

public class Sandbox2D extends Layer {
    private final OrthographicCameraController m_CameraController;

    // Temp
    private VertexArray m_SquareVA;
    private Shader m_FlatColorShader;

    private Vec4 m_SquareColor = new Vec4( 0.2f, 0.3f, 0.8f, 1.0f );
    private Texture2D m_CheckerboardTexture;
    static float rotation = 0.0f;
    static boolean dockingEnabled = true;
    static ImBoolean dockspaceOpen = new ImBoolean(true);
    static ImBoolean opt_fullscreen_persistant = new ImBoolean(true);
    static int dockspace_flags = ImGuiDockNodeFlags.None;

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
        Renderer2D.ResetStats();
        RenderCommand.SetClearColor(new Vec4( 0.1f, 0.1f, 0.1f, 1 ));
        RenderCommand.Clear();
        HB_PROFILE_SCOPE_STOP("Renderer Prep");

        rotation += 50f * ts.GetSeconds();

        HB_PROFILE_SCOPE("Renderer Draw");
        Renderer2D.BeginScene(m_CameraController.GetCamera());
        Renderer2D.DrawRotatedQuad(new Vec2( 1.0f, 0.0f ), new Vec2( 0.8f, 0.8f ), -45.0f, new Vec4( 0.8f, 0.2f, 0.3f, 1.0f ));
        Renderer2D.DrawQuad(new Vec2( -1.0f, 0.0f ), new Vec2( 0.8f, 0.8f ), new Vec4( 0.8f, 0.2f, 0.3f, 1.0f ));
        Renderer2D.DrawQuad(new Vec2( 0.5f, -0.5f ), new Vec2( 0.5f, 0.75f ), m_SquareColor);
        Renderer2D.DrawQuad(new Vec3( 0.0f, 0.0f, -0.1f ), new Vec2( 20.0f, 20.0f ), m_CheckerboardTexture, 10.0f);
        Renderer2D.DrawRotatedQuad(new Vec3( -2.0f, 0.0f, 0.0f ), new Vec2( 1.0f, 1.0f ), rotation, m_CheckerboardTexture, 20.0f);
        Renderer2D.EndScene();

        Renderer2D.BeginScene(m_CameraController.GetCamera());
        for (float y = -5.0f; y < 5.0f; y += 0.5f)
        {
            for (float x = -5.0f; x < 5.0f; x += 0.5f)
            {
                Vec4 color = new Vec4( (x + 5.0f) / 10.0f, 0.4f, (y + 5.0f) / 10.0f, 0.7f );
                Renderer2D.DrawQuad(new Vec2( x, y ), new Vec2( 0.45f, 0.45f ), color);
            }
        }
        Renderer2D.EndScene();
        HB_PROFILE_SCOPE_STOP("Renderer Draw");

        HB_PROFILE_SCOPE_STOP("Sandbox2D::OnUpdate");

        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnImGuiRender()
    {
        HB_PROFILE_FUNCTION();

        // Note: Switch this to true to enable dockspace
        if (dockingEnabled)
        {
            ImBoolean opt_fullscreen = opt_fullscreen_persistant;

            // We are using the ImGuiWindowFlags_NoDocking flag to make the parent window not dockable into,
            // because it would be confusing to have two docking targets within each others.
            int window_flags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
            if (opt_fullscreen.get())
            {
                ImGuiViewport viewport = ImGui.getMainViewport();
                ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY());
                ImGui.setNextWindowSize(viewport.getSizeX(), viewport.getSizeY());
                ImGui.setNextWindowViewport(viewport.getID());
                ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
                ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
                window_flags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
                window_flags |= ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;
            }

            // When using ImGuiDockNodeFlags_PassthruCentralNode, DockSpace() will render our background and handle the pass-thru hole, so we ask Begin() to not render a background.
            if ((dockspace_flags & ImGuiDockNodeFlags.PassthruCentralNode) != 0)
                window_flags |= ImGuiWindowFlags.NoBackground;

            // Important: note that we proceed even if Begin() returns false (aka window is collapsed).
            // This is because we want to keep our DockSpace() active. If a DockSpace() is inactive,
            // all active windows docked into it will lose their parent and become undocked.
            // We cannot preserve the docking relationship between an active window and an inactive docking, otherwise
            // any change of dockspace/settings would lead to windows being stuck in limbo and never being visible.
            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0.0f, 0.0f);
            ImGui.begin("DockSpace Demo", dockspaceOpen, window_flags);
            ImGui.popStyleVar();

            if (opt_fullscreen.get())
                ImGui.popStyleVar(2);

            // DockSpace
            ImGuiIO io = ImGui.getIO();
            if ((io.getConfigFlags() & ImGuiConfigFlags.DockingEnable) != 0)
            {
                int dockspace_id = ImGui.getID("MyDockSpace");
                ImGui.dockSpace(dockspace_id, 0.0f, dockspace_flags);
            }

            if (ImGui.beginMenuBar())
            {
                if (ImGui.beginMenu("File"))
                {
                    // Disabling fullscreen would allow the window to be moved to the front of other windows,
                    // which we can't undo at the moment without finer window depth/z control.
                    //ImGui::MenuItem("Fullscreen", NULL, &opt_fullscreen_persistant);

                    if (ImGui.menuItem("Exit")) Application.Get().Close();
                    ImGui.endMenu();
                }

                ImGui.endMenuBar();
            }

            ImGui.begin("Settings");

            var stats = Renderer2D.GetStats();
            ImGui.text("Renderer2D Stats:");
            ImGui.text("Draw Calls: %d" + stats.DrawCalls);
            ImGui.text("Quads: %d" + stats.QuadCount);
            ImGui.text("Vertices: %d" + stats.GetTotalVertexCount());
            ImGui.text("Indices: %d" + stats.GetTotalIndexCount());

            float[] arr = { m_SquareColor.x, m_SquareColor.y, m_SquareColor.z, m_SquareColor.w };
            ImGui.colorEdit4("Square Color", arr);
            m_SquareColor = new Vec4(arr[0], arr[1], arr[2], arr[3]);

            int textureID = m_CheckerboardTexture.GetRendererID();
            ImGui.image(textureID, 256.0f, 256.0f );
            ImGui.end();

            ImGui.end();
        }
        else
        {
            ImGui.begin("Settings");

            var stats = Renderer2D.GetStats();
            ImGui.text("Renderer2D Stats:");
            ImGui.text("Draw Calls: %d" + stats.DrawCalls);
            ImGui.text("Quads: %d" + stats.QuadCount);
            ImGui.text("Vertices: %d" + stats.GetTotalVertexCount());
            ImGui.text("Indices: %d" + stats.GetTotalIndexCount());

            float[] arr = { m_SquareColor.x, m_SquareColor.y, m_SquareColor.z, m_SquareColor.w };
            ImGui.colorEdit4("Square Color", arr);
            m_SquareColor = new Vec4(arr[0], arr[1], arr[2], arr[3]);

            int textureID = m_CheckerboardTexture.GetRendererID();
            ImGui.image(textureID, 256.0f, 256.0f );
            ImGui.end();
        }
        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnEvent(Event e)
    {
        m_CameraController.OnEvent(e);
    }
}
