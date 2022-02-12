package com.chernobyl.gameengine.layer;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.event.*;
import com.chernobyl.gameengine.event.enums.EventType;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiKey;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class ImGuiLayer extends Layer {
    private float m_Time = 0.0f;
    private final ImGuiImplGl3 imGuiImplGl3;
//    private final ImGuiImplGlfw imGuiImplGlfw;

    public ImGuiLayer() {
        super("ImGuiLayer");
        imGuiImplGl3 = new ImGuiImplGl3();
    }


    @Override
    public void OnAttach() {
        ImGuiContext ctx = ImGui.createContext();
        ImGui.setCurrentContext(ctx);
        ImGui.styleColorsDark();
        imGuiImplGl3.init("#version 130");

        ImGuiIO io = ImGui.getIO();
        io.addBackendFlags(ImGuiBackendFlags.HasMouseCursors);
        io.addBackendFlags(ImGuiBackendFlags.HasSetMousePos);

        // TEMPORARY: should eventually use Hazel key codes
        io.setKeyMap(ImGuiKey.Tab, GLFW_KEY_TAB);
        io.setKeyMap(ImGuiKey.LeftArrow, GLFW_KEY_LEFT);
        io.setKeyMap(ImGuiKey.RightArrow, GLFW_KEY_RIGHT);
        io.setKeyMap(ImGuiKey.UpArrow, GLFW_KEY_UP);
        io.setKeyMap(ImGuiKey.DownArrow, GLFW_KEY_DOWN);
        io.setKeyMap(ImGuiKey.PageUp, GLFW_KEY_PAGE_UP);
        io.setKeyMap(ImGuiKey.PageDown, GLFW_KEY_PAGE_DOWN);
        io.setKeyMap(ImGuiKey.Home, GLFW_KEY_HOME);
        io.setKeyMap(ImGuiKey.End, GLFW_KEY_END);
        io.setKeyMap(ImGuiKey.Insert, GLFW_KEY_INSERT);
        io.setKeyMap(ImGuiKey.Delete, GLFW_KEY_DELETE);
        io.setKeyMap(ImGuiKey.Backspace, GLFW_KEY_BACKSPACE);
        io.setKeyMap(ImGuiKey.Space, GLFW_KEY_SPACE);
        io.setKeyMap(ImGuiKey.Enter, GLFW_KEY_ENTER);
        io.setKeyMap(ImGuiKey.Escape, GLFW_KEY_ESCAPE);
        io.setKeyMap(ImGuiKey.A, GLFW_KEY_A);
        io.setKeyMap(ImGuiKey.C, GLFW_KEY_C);
        io.setKeyMap(ImGuiKey.V, GLFW_KEY_V);
        io.setKeyMap(ImGuiKey.X, GLFW_KEY_X);
        io.setKeyMap(ImGuiKey.Y, GLFW_KEY_Y);
        io.setKeyMap(ImGuiKey.Z, GLFW_KEY_Z);

    }

    @Override
    public void OnDetach() {
        imGuiImplGl3.dispose();
        ImGui.destroyContext();
    }

    @Override
    public void OnUpdate() {
        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(Application.getWindow().getWidth(), Application.getWindow().getHeight());

        float time = (float) glfwGetTime();
        io.setDeltaTime(m_Time > 0.0f ? (time - m_Time) : (1.0f / 60.0f));
        m_Time = time;

        imGuiImplGl3.updateFontsTexture();
        ImGui.newFrame();

        final ImBoolean show = new ImBoolean(true);
        ImGui.showDemoWindow(show);

        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void OnEvent(Event event) {

        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.Dispatch(ImGuiLayer::OnMouseButtonPressedEvent, EventType.MouseButtonPressed);
        dispatcher.Dispatch(ImGuiLayer::OnMouseButtonReleasedEvent, EventType.MouseButtonReleased);
        dispatcher.Dispatch(ImGuiLayer::OnMouseMovedEvent, EventType.MouseMoved);
        dispatcher.Dispatch(ImGuiLayer::OnMouseScrolledEvent, EventType.MouseScrolled);
        dispatcher.Dispatch(ImGuiLayer::OnKeyPressedEvent, EventType.KeyPressed);
        dispatcher.Dispatch(ImGuiLayer::OnKeyTypedEvent, EventType.KeyTyped);
        dispatcher.Dispatch(ImGuiLayer::OnKeyReleasedEvent, EventType.KeyReleased);
        dispatcher.Dispatch(ImGuiLayer::OnWindowResizeEvent, EventType.WindowResize);
    }

    private static boolean OnWindowResizeEvent(WindowResizeEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(e.getWidth(), e.getHeight());
        io.setDisplayFramebufferScale(1.0f, 1.0f);
        glViewport(0, 0, e.getWidth(), e.getHeight());
        return false;
    }

    private static boolean OnKeyReleasedEvent(KeyReleasedEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setKeysDown(e.GetKeyCode(), false);
        return false;
    }

    private static boolean OnKeyTypedEvent(KeyTypedEvent e) {
    ImGuiIO io = ImGui.getIO();
    int keycode = e.GetKeyCode();
		if (keycode > 0 && keycode < 0x10000000)
            io.addInputCharacter(keycode);
        return false;
    }

    private static boolean OnKeyPressedEvent(KeyPressedEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setKeysDown(e.GetKeyCode(), true);

        io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
        io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
        io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
        io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
        return false;
    }

    private static boolean OnMouseScrolledEvent(MouseScrolledEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setMouseWheelH(io.getMouseWheelH() + e.getXOffset());
        io.setMouseWheel(io.getMouseWheel() + e.getYOffset());
        return false;
    }

    private static boolean OnMouseMovedEvent(MouseMovedEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setMousePos(e.getX(), e.getY());
        return false;
    }

    private static boolean OnMouseButtonReleasedEvent(MouseButtonReleasedEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setMouseDown(e.getMouseButton(), false);
        return false;
    }

    private static boolean OnMouseButtonPressedEvent(MouseButtonPressedEvent e) {
        ImGuiIO io = ImGui.getIO();
        io.setMouseDown(e.getMouseButton(), true);
        return false;
    }
}
