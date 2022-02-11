package com.chernobyl.gameengine;

import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.event.EventDispatcher;
import com.chernobyl.gameengine.event.WindowCloseEvent;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.layer.Layer;
import com.chernobyl.gameengine.layer.LayerStack;
import com.chernobyl.gameengine.window.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import lombok.Getter;
import org.lwjgl.opengl.GL;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static org.lwjgl.opengl.GL11.*;

public class Application {
    @Getter
    private static Window window = null;
    private static Application application = null;
    private static boolean running = true;
    @Getter
    private static final LayerStack m_LayerStack = new LayerStack();

    public Application() {
        HB_CORE_ASSERT(application != null, "Application already exists!");
        window = LinuxWindow.get();
        window.SetEventCallback(Application::onEvent);
    }

    public static Application get() {
        if(application == null) {
            application = new Application();
        }
        return application;
    }

    public void Run() {
        while (running) {
            GL.createCapabilities();
            glClearColor(1, 0, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            for (Layer layer : m_LayerStack)
                layer.OnUpdate();

            window.OnUpdate();
        }
        window.Shutdown();
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
        layer.OnAttach();
    }

    public void pushOverlay(Layer layer) {
        m_LayerStack.PushOverlay(layer);
        layer.OnAttach();
    }

    private static boolean onWindowClose(WindowCloseEvent e) {
        running = false;
        return true;
    }
}
