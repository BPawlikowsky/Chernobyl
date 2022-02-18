package com.chernobyl.gameengine;

import com.chernobyl.gameengine.event.*;
import com.chernobyl.gameengine.layer.*;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.platform.linux.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import lombok.Getter;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
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

    public Application() {

        HB_CORE_ASSERT(s_Instance == null, "Application already exists!");

        m_Window = LinuxWindow.get();
        m_Window.SetEventCallback(Application::onEvent);
        m_ImGuiLayer = new ImGuiLayer();
        pushOverlay(m_ImGuiLayer);

    }

    public static Application get() {
        if (s_Instance == null) {
            s_Instance = new Application();
        }
        return s_Instance;
    }

    public void Run() {
        while (m_Running) {

            float time = (float)glfwGetTime();
            Timestep timestep = new Timestep(time - m_LastFrameTime);
            m_LastFrameTime = time;

            for (Layer layer : m_LayerStack)
                layer.OnUpdate(timestep);

            m_ImGuiLayer.Begin();
            for (Layer layer : m_LayerStack)
                layer.OnImGuiRender();
            m_ImGuiLayer.End();

            m_Window.OnUpdate();
        }
        m_Window.Shutdown();
    }

    private static void onEvent(Event e) {
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(Application::onWindowClose, EventType.WindowClose);

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

    private static boolean onWindowClose(WindowCloseEvent e) {
        m_Running = false;
        return true;
    }
}
