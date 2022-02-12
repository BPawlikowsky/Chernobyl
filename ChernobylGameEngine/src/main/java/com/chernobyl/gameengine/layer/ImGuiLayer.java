package com.chernobyl.gameengine.layer;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.event.*;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;

import static com.chernobyl.gameengine.Asserts.HB_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_INFO;
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
        HB_CORE_INFO(ImGui.getVersion());
        ImGuiContext ctx = ImGui.createContext();
        ImGui.setCurrentContext(ctx);
        long window = Application.getWindow().getNativeWindow();
        HB_ASSERT(window != 0, "Native window is null.");

        ImGuiIO io = ImGui.getIO();
        io.setConfigFlags(io.getConfigFlags() | ImGuiConfigFlags.NavEnableKeyboard);       // Enable Keyboard Controls
        // io.setConfigFlags(io.getCofigFlags() | ImGuiConfigFlags.NavEnableGamepad);      // Enable Gamepad Controls
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
        imGuiImplGl3.init("#version 330 core");
    }

    public void Begin()
    {
        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
    }

    public void End()
    {
        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(Application.getWindow().getWidth(), Application.getWindow().getHeight());

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
    }

    public void OnImGuiRender()
    {
        ImBoolean show = new ImBoolean(true);
        ImGui.showDemoWindow(show);
    }

    @Override
    public void OnDetach() {
        imGuiImplGl3.dispose();
        imGuiImplGlfw.dispose();
        ImGui.destroyContext();
    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void OnEvent(Event event) {

    }
}
