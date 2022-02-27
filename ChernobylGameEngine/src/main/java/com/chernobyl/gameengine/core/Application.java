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
import static com.chernobyl.gameengine.core.Instrumentor.*;
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
        HB_PROFILE_FUNCTION();

        HB_CORE_ASSERT(s_Instance == null, "Application already exists!");

        m_Window = Window.Create();
        m_Window.SetEventCallback(this::onEvent);

        Renderer.Init();

        m_ImGuiLayer = new ImGuiLayer();
        pushOverlay(m_ImGuiLayer);

        HB_PROFILE_FUNCTION_STOP();
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
        HB_PROFILE_FUNCTION();

        while (m_Running) {
            HB_PROFILE_SCOPE("RunLoop");

            float time = (float) glfwGetTime();
            Timestep timestep = new Timestep(time - m_LastFrameTime);
            m_LastFrameTime = time;

            if (!m_Minimized) {
                HB_PROFILE_SCOPE("LayerStack OnUpdate");

                for (Layer layer : m_LayerStack)
                    layer.OnUpdate(timestep);

                HB_PROFILE_SCOPE_STOP("LayerStack OnUpdate");
            }

            HB_PROFILE_SCOPE("LayerStack OnImGuiRender");

            m_ImGuiLayer.Begin();
            for (Layer layer : m_LayerStack)
                layer.OnImGuiRender();
            m_ImGuiLayer.End();

            HB_PROFILE_SCOPE_STOP("LayerStack OnImGuiRender");

            m_Window.OnUpdate();

            HB_PROFILE_SCOPE_STOP("RunLoop");
        }
        m_Window.Shutdown();

        HB_PROFILE_FUNCTION_STOP();
    }

    private void onEvent(Event e) {
        HB_PROFILE_FUNCTION();

        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(this::OnWindowClose, EventType.WindowClose);
        dispatcher.Dispatch(this::OnWindowResize, EventType.WindowResize);

        for (int i = m_LayerStack.end(); i >= m_LayerStack.begin(); i--) {
            m_LayerStack.get(i).OnEvent(e);
            if (e.isM_Handled()) break;
        }

        HB_PROFILE_FUNCTION_STOP();
    }

    public void pushLayer(Layer layer) {
        HB_PROFILE_FUNCTION();

        m_LayerStack.PushLayer(layer);

        HB_PROFILE_FUNCTION_STOP();
    }

    public void pushOverlay(Layer overlay) {
        HB_PROFILE_FUNCTION();

        m_LayerStack.PushOverlay(overlay);

        HB_PROFILE_FUNCTION_STOP();
    }

    private boolean OnWindowClose(WindowCloseEvent e) {
        m_Running = false;
        return true;
    }

    private boolean OnWindowResize(WindowResizeEvent e) {
        HB_PROFILE_FUNCTION();

        if (e.getWidth() == 0 || e.getHeight() == 0) {
            m_Minimized = true;
            return false;
        }

        m_Minimized = false;
        Renderer.OnWindowResize(e.getWidth(), e.getHeight());

        HB_PROFILE_FUNCTION_STOP();

        return false;
    }
}
