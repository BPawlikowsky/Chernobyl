package com.chernobyl.gameengine.window;

import com.chernobyl.gameengine.event.*;
import org.lwjgl.glfw.GLFWErrorCallback;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_ERROR;
import static com.chernobyl.gameengine.Log.HB_CORE_INFO;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LinuxWindow extends Window{
    private long glfwWindow;
    private boolean vSync;
    public IEventCallback eventCallback;
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

    private void glfwErrorCallback(int err, long desc) {
        HB_CORE_ERROR("GLFW Error({0}): {1}", err, GLFWErrorCallback.getDescription(desc));
    }

    void Init()
    {
        HB_CORE_INFO("Creating window {0} ({1}, {2})", this.title, this.width, this.height);

        if (!s_GLFWInitialized)
        {
            // TODO: glfwTerminate on system shutdown
            boolean success = glfwInit();
            HB_CORE_ASSERT(success, "Could not intialize GLFW!");
            glfwSetErrorCallback(this::glfwErrorCallback);

            s_GLFWInitialized = true;
        }

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        HB_CORE_ASSERT(glfwWindow != NULL, "Could not create GLFW window.");

        glfwMakeContextCurrent(glfwWindow);
        SetVSync(true);

        // Set GLFW callbacks
        glfwSetWindowSizeCallback(glfwWindow, (long window, int width, int height) ->
        {
            this.width = width;
            this.height = height;

            WindowResizeEvent event = new WindowResizeEvent(width, height);
            eventCallback.invoke(event);
        });

        glfwSetWindowCloseCallback(glfwWindow, (long window) ->
        {
            
            WindowCloseEvent event = new WindowCloseEvent();
            eventCallback.invoke(event);
        });

        glfwSetKeyCallback(glfwWindow, (long window, int key, int scancode, int action, int mods) ->
        {
            

            switch (action)
            {
                case GLFW_PRESS:
                {
                    KeyPressedEvent event = new KeyPressedEvent(key, 0);
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_RELEASE:
                {
                    KeyReleasedEvent event = new KeyReleasedEvent(key);
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_REPEAT:
                {
                    KeyPressedEvent event = new KeyPressedEvent(key, 1);
                    eventCallback.invoke(event);
                    break;
                }
            }
        });

        glfwSetMouseButtonCallback(glfwWindow, (long window, int button, int action, int mods) ->
        {
            switch (action)
            {
                case GLFW_PRESS:
                {
                    MouseButtonPressedEvent event = new MouseButtonPressedEvent(button);
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_RELEASE:
                {
                    MouseButtonReleasedEvent event = new MouseButtonReleasedEvent(button);
                    eventCallback.invoke(event);
                    break;
                }
            }
        });

        glfwSetScrollCallback(glfwWindow, (long window, double xOffset, double yOffset) ->
        {
            MouseScrolledEvent event = new MouseScrolledEvent((float)xOffset, (float)yOffset);
            eventCallback.invoke(event);
        });

        glfwSetCursorPosCallback(glfwWindow, (long window, double xPos, double yPos) ->
        {
            MouseMovedEvent event = new MouseMovedEvent((float)xPos, (float)yPos);
            eventCallback.invoke(event);
        });
    }

    public void Shutdown()
    {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        HB_CORE_INFO("Shutdown finished successfully");
    }

    @Override
    public void OnUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(glfwWindow);
    }

    @Override
    public void SetEventCallback(IEventCallback callback) {
       eventCallback = callback;
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
