package com.chernobyl.gameengine;

import com.chernobyl.gameengine.window.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public abstract class Application {
    private final Window window;
    private boolean running = false;

    public Application() {
        window = LinuxWindow.get();
    }

    public void Run() {
        running = true;
        while (running) {
            GL.createCapabilities();
            glClearColor(1, 0, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);
            window.OnUpdate();
        }
        window.Shutdown();
    }
}
