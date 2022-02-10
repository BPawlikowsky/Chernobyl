package com.chernobyl.gameengine;

import com.chernobyl.gameengine.events.Event;
import com.chernobyl.gameengine.events.EventDispatcher;
import com.chernobyl.gameengine.events.WindowCloseEvent;
import com.chernobyl.gameengine.events.enums.EventType;
import com.chernobyl.gameengine.window.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import org.lwjgl.opengl.GL;

import static com.chernobyl.gameengine.Log.HB_CORE_TRACE;
import static org.lwjgl.opengl.GL11.*;

public abstract class Application {
    private final Window window;
    private static boolean running = true;

    public Application() {
        window = LinuxWindow.get();
        window.SetEventCallback(Application::onEvent);
    }

    public void Run() {
        while (running) {
            GL.createCapabilities();
            glClearColor(1, 0, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);
            window.OnUpdate();
        }
        window.Shutdown();
    }

    private static void onEvent(Event e) {
        HB_CORE_TRACE(e);
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(Application::onWindowClose, EventType.WindowClose);
    }

    private static boolean onWindowClose(WindowCloseEvent e) {
        running = false;
        return true;
    }
}
