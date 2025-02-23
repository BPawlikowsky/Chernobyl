package com.chernobyl.gameengine.core.layer;

import com.chernobyl.gameengine.core.Application;
import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.core.Timestep;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGuiContext;

import static com.chernobyl.gameengine.core.Asserts.HB_ASSERT;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static com.chernobyl.gameengine.core.Log.HB_CORE_INFO;
import static org.lwjgl.glfw.GLFW.*;

public class ImGuiLayer extends Layer {
    private final float m_Time = 0.0f;
    private final ImGuiImplGl3 imGuiImplGl3;
    private final ImGuiImplGlfw imGuiImplGlfw;

    public ImGuiLayer() {
        super("ImGuiLayer");
        imGuiImplGl3 = new ImGuiImplGl3();
        imGuiImplGlfw = new ImGuiImplGlfw();
    }


    @Override
    public void OnAttach() {
        HB_PROFILE_FUNCTION();

        HB_CORE_INFO("ImGui version {0}", ImGui.getVersion());
        ImGuiContext ctx = ImGui.createContext();
        ImGui.setCurrentContext(ctx);
        long window = Application.getM_Window().getNativeWindow();
        HB_ASSERT(window != 0, "Native window is null.");

        ImGuiIO io = ImGui.getIO();
        io.setConfigFlags(io.getConfigFlags() | ImGuiConfigFlags.NavEnableKeyboard);       // Enable Keyboard Controls
        // io.setConfigFlags(io.getConfigFlags() | ImGuiConfigFlags.NavEnableGamepad);      // Enable Gamepad Controls
        io.setConfigFlags( io.getConfigFlags()| ImGuiConfigFlags.DockingEnable);           // Enable Docking
        io.setConfigFlags( io.getConfigFlags()| ImGuiConfigFlags.ViewportsEnable);         // Enable Multi-Viewport / Platform Windows
        // io.setConfigFlags( io.getConfigFlags()| ImGuiConfigFlags.ViewportsNoTaskBarIcons);
        // io.setConfigFlags( io.getConfigFlags()| ImGuiConfigFlags.ViewportsNoMerge);

        ImGui.styleColorsDark();

        ImGuiStyle style = ImGui.getStyle();
        if ((io.getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) > 0)
        {
            style.setWindowRounding(0.0f);
            var windowColor = style.getColor(ImGuiCol.WindowBg);
            style.setColor(ImGuiCol.WindowBg, windowColor.x, windowColor.y, windowColor.z, 1.0f);
        }
        imGuiImplGlfw.init(window, true);
        imGuiImplGl3.init("#version 410");

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Begin()
    {
        HB_PROFILE_FUNCTION();

        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(Application.getM_Window().getWidth(), Application.getM_Window().getHeight());
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();

        HB_PROFILE_FUNCTION_STOP();
    }

    public void End()
    {
        HB_PROFILE_FUNCTION();

        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(Application.getM_Window().getWidth(), Application.getM_Window().getHeight());

        // Rendering
        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());

        if ((io.getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) > 0)
        {
            long backup_current_context = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backup_current_context);
        }

        HB_PROFILE_FUNCTION_STOP();
    }

    public void OnImGuiRender()
    {
    }

    @Override
    public void OnDetach() {
        HB_PROFILE_FUNCTION();

        imGuiImplGl3.dispose();
        imGuiImplGlfw.dispose();
        ImGui.destroyContext();

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void OnUpdate(Timestep ts) {

    }

    @Override
    public void OnEvent(Event event) {

    }
}
