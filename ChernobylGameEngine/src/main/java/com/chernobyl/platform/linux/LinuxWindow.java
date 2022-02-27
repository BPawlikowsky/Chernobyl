package com.chernobyl.platform.linux;

import com.chernobyl.gameengine.core.Config;
import com.chernobyl.gameengine.core.input.KeyCode;
import com.chernobyl.gameengine.core.input.MouseCode;
import com.chernobyl.gameengine.events.*;
import com.chernobyl.gameengine.render.GraphicsContext;
import com.chernobyl.gameengine.core.window.IEventCallback;
import com.chernobyl.gameengine.core.window.Window;
import com.chernobyl.gameengine.core.window.WindowProps;
import com.chernobyl.gameengine.renderer.Renderer;
import com.chernobyl.platform.opengl.OpenGLContext;
import org.lwjgl.glfw.GLFWErrorCallback;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.core.Instrumentor.*;
import static com.chernobyl.gameengine.core.Log.HB_CORE_ERROR;
import static com.chernobyl.gameengine.core.Log.HB_CORE_INFO;
import static com.chernobyl.gameengine.renderer.RendererAPI.API.OpenGL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LinuxWindow extends Window {
    private boolean vSync;
    private GraphicsContext m_Context;
    public IEventCallback eventCallback;
    static boolean s_GLFWInitialized = false;

    private LinuxWindow(){
        HB_PROFILE_FUNCTION();

        WindowProps windowProps = new WindowProps();
        this.width = windowProps.getWidth();
        this.height = windowProps.getHeight();
        this.title = windowProps.getTitle();
        Init();

        HB_PROFILE_FUNCTION_STOP();
    }

    private LinuxWindow(WindowProps windowProps){
        HB_PROFILE_FUNCTION();

        this.width = windowProps.getWidth();
        this.height = windowProps.getHeight();
        this.title = windowProps.getTitle();
        Init();

        HB_PROFILE_FUNCTION_STOP();
    }


    public static Window get() {
        if(Window.m_Window == null) {
            Window.m_Window = new LinuxWindow();
        }

        return Window.m_Window;
    }

    public static Window get(WindowProps windowProps) {
        if(Window.m_Window == null) {
            Window.m_Window = new LinuxWindow(windowProps);
        }

        return Window.m_Window;
    }

    private void glfwErrorCallback(int err, long desc) {
        HB_CORE_ERROR("GLFW Error({0}): {1}", err, GLFWErrorCallback.getDescription(desc));
    }

    void Init()
    {
        HB_PROFILE_FUNCTION();

        HB_PROFILE_SCOPE("glfwInit");

        HB_CORE_INFO("Creating window {0} ({1}, {2})", this.title, this.width, this.height);

        if (!s_GLFWInitialized)
        {
            // TODO: glfwTerminate on system shutdown
            boolean success = glfwInit();
            HB_CORE_ASSERT(success, "Could not initialize GLFW!");
            glfwSetErrorCallback(this::glfwErrorCallback);

            s_GLFWInitialized = true;
        }

        HB_PROFILE_SCOPE_STOP("glfwInit");

        HB_PROFILE_SCOPE("glfwCreateWindow");

        if (Config.isDebug)
            if (Renderer.GetAPI() == OpenGL) glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        nativeWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        HB_PROFILE_SCOPE_STOP("glfwCreateWindow");

        HB_CORE_ASSERT(nativeWindow != NULL, "Could not create GLFW window.");

        m_Context = OpenGLContext.Create(nativeWindow);
        m_Context.Init();

        SetVSync(true);

        // Set GLFW callbacks
        glfwSetWindowSizeCallback(nativeWindow, (long window, int width, int height) ->
        {
            this.width = width;
            this.height = height;

            WindowResizeEvent event = new WindowResizeEvent(width, height);
            eventCallback.invoke(event);
        });

        glfwSetWindowCloseCallback(nativeWindow, (long window) ->
        {
            
            WindowCloseEvent event = new WindowCloseEvent();
            eventCallback.invoke(event);
        });

        glfwSetKeyCallback(nativeWindow, (long window, int key, int scancode, int action, int mods) ->
        {


            switch (action)
            {
                case GLFW_PRESS:
                {
                    KeyPressedEvent event = new KeyPressedEvent(KeyCode.getEnumFromKey(key), 0);
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_RELEASE:
                {
                    KeyReleasedEvent event = new KeyReleasedEvent(KeyCode.getEnumFromKey(key));
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_REPEAT:
                {
                    KeyPressedEvent event = new KeyPressedEvent(KeyCode.getEnumFromKey(key), 1);
                    eventCallback.invoke(event);
                    break;
                }
            }
        });

        glfwSetCharCallback(nativeWindow, (long window, int keycode) ->
        {
            KeyTypedEvent event = new KeyTypedEvent(KeyCode.getEnumFromKey(keycode));
            eventCallback.invoke(event);
        });

        glfwSetMouseButtonCallback(nativeWindow, (long window, int button, int action, int mods) ->
        {
            switch (action)
            {
                case GLFW_PRESS:
                {
                    MouseButtonPressedEvent event = new MouseButtonPressedEvent(MouseCode.getEnumFromKey(button));
                    eventCallback.invoke(event);
                    break;
                }
                case GLFW_RELEASE:
                {
                    MouseButtonReleasedEvent event = new MouseButtonReleasedEvent(MouseCode.getEnumFromKey(button));
                    eventCallback.invoke(event);
                    break;
                }
            }
        });

        glfwSetScrollCallback(nativeWindow, (long window, double xOffset, double yOffset) ->
        {
            MouseScrolledEvent event = new MouseScrolledEvent((float)xOffset, (float)yOffset);
            eventCallback.invoke(event);
        });

        glfwSetCursorPosCallback(nativeWindow, (long window, double xPos, double yPos) ->
        {
            MouseMovedEvent event = new MouseMovedEvent((float)xPos, (float)yPos);
            eventCallback.invoke(event);
        });

        HB_PROFILE_FUNCTION_STOP();
    }

    public void Shutdown()
    {
        HB_PROFILE_FUNCTION();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(nativeWindow);
        glfwDestroyWindow(nativeWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        HB_CORE_INFO("Shutdown finished successfully");

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void OnUpdate() {
        HB_PROFILE_FUNCTION();

        glfwPollEvents();
        m_Context.SwapBuffers();

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void SetEventCallback(IEventCallback callback) {
       eventCallback = callback;
    }

    @Override
    public void SetVSync(boolean enabled) {
        HB_PROFILE_FUNCTION();

        if (enabled)
            glfwSwapInterval(1);
        else
            glfwSwapInterval(0);

        vSync = enabled;

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public boolean IsVSync() {
        return vSync;
    }
}
