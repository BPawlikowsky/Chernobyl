package com.chernobyl.gameengine.core;

import com.chernobyl.gameengine.events.EventDispatcher;
import com.chernobyl.gameengine.events.WindowCloseEvent;
import com.chernobyl.gameengine.events.WindowResizeEvent;
import com.chernobyl.gameengine.core.layer.ImGuiLayer;
import com.chernobyl.gameengine.core.layer.Layer;
import com.chernobyl.gameengine.core.layer.LayerStack;
import com.chernobyl.gameengine.events.enums.EventType;
import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.linux.LinuxWindow;
import com.chernobyl.gameengine.core.window.Window;
import lombok.Getter;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Application {
    private static final int TIGHT_STRIDE = 0;
    private static Application s_Instance = null;
    @Getter
    private static Window m_Window = null;
    private static boolean m_Running = true;
    @Getter
    private static final LayerStack m_LayerStack = new LayerStack();
    private final ImGuiLayer m_ImGuiLayer;

    private float m_LastFrameTime = 0.0f;

    private boolean m_Minimized = false;

    public Application() {

        HB_CORE_ASSERT(s_Instance == null, "Application already exists!");

        m_Window = LinuxWindow.get();
        m_Window.SetEventCallback(this::onEvent);

        Renderer.Init();

        m_ImGuiLayer = new ImGuiLayer();
        pushOverlay(m_ImGuiLayer);

    }

    public void destroy() {
        Renderer.Shutdown();
    }

    public static Application Create() {
        if (s_Instance == null) {
            s_Instance = new Application();
        }
        return s_Instance;
    }

    public void Run() {
        while (m_Running) {

            float time = (float) glfwGetTime();
            Timestep timestep = new Timestep(time - m_LastFrameTime);
            m_LastFrameTime = time;

            if (!m_Minimized) {
                for (Layer layer : m_LayerStack)
                    layer.OnUpdate(timestep);
            }

            m_ImGuiLayer.Begin();
            for (Layer layer : m_LayerStack)
                layer.OnImGuiRender();
            m_ImGuiLayer.End();

            m_Window.OnUpdate();
        }
        m_Window.Shutdown();
    }

    private void onEvent(Event e) {
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(this::OnWindowClose, EventType.WindowClose);
        dispatcher.Dispatch(this::OnWindowResize, EventType.WindowResize);

        for (int i = m_LayerStack.end(); i >= m_LayerStack.begin(); i--) {
            m_LayerStack.get(i).OnEvent(e);
            if (e.isM_Handled()) break;
        }
    }

    public void pushLayer(Layer layer) {
        m_LayerStack.PushLayer(layer);
    }

    public void pushOverlay(Layer overlay) {
        m_LayerStack.PushOverlay(overlay);
    }

    private boolean OnWindowClose(WindowCloseEvent e) {
        m_Running = false;
        return true;
    }

    private boolean OnWindowResize(WindowResizeEvent e) {
        if (e.getWidth() == 0 || e.getHeight() == 0) {
            m_Minimized = true;
            return false;
        }

        m_Minimized = false;
        Renderer.OnWindowResize(e.getWidth(), e.getHeight());

        return false;
    }
}
