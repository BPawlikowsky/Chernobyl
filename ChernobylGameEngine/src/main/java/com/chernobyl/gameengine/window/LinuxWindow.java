package com.chernobyl.gameengine.window;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_INFO;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LinuxWindow extends Window{
    private long glfwWindow;
    private boolean vSync;
    static boolean s_GLFWInitialized = false;

    private LinuxWindow(){
        WindowProps windowProps = new WindowProps();
        this.width = windowProps.width;
        this.height = windowProps.height;
        this.title = windowProps.title;
        Init();
    }

    private LinuxWindow(WindowProps windowProps){
        this.width = windowProps.width;
        this.height = windowProps.height;
        this.title = windowProps.title;
        Init();
    }


    public static Window get() {
        if(Window.window == null) {
            Window.window = new LinuxWindow();
        }

        return Window.window;
    }

    public static Window get(WindowProps windowProps) {
        if(Window.window == null) {
            Window.window = new LinuxWindow(windowProps);
        }

        return Window.window;
    }

    void Init()
    {
        HB_CORE_INFO("Creating window {0} ({1}, {2})", this.title, this.width, this.height);

        if (!s_GLFWInitialized)
        {
            // TODO: glfwTerminate on system shutdown
            boolean success = glfwInit();
            HB_CORE_ASSERT(success, "Could not intialize GLFW!");

            s_GLFWInitialized = true;
        }

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        HB_CORE_ASSERT(glfwWindow != NULL, "Could not create GLFW window.");

        glfwMakeContextCurrent(glfwWindow);
        SetVSync(true);
    }

    public void Shutdown()
    {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public void OnUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(glfwWindow);
    }

    @Override
    void SetEventCallback(IEventCallback callback) {

    }

    @Override
    void SetVSync(boolean enabled) {
        if (enabled)
            glfwSwapInterval(1);
        else
            glfwSwapInterval(0);

        vSync = enabled;
    }

    @Override
    boolean IsVSync() {
        return vSync;
    }
}
