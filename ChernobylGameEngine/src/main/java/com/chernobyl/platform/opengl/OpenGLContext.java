package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.render.GraphicsContext;
import org.lwjgl.opengl.GL;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static com.chernobyl.gameengine.core.Log.HB_CORE_INFO;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLContext extends GraphicsContext {
    private final long m_WindowHandle;

    public OpenGLContext(long windowHandle) {
        HB_CORE_ASSERT(windowHandle != 0, "Window handle is null!");
        this.m_WindowHandle = windowHandle;
    }

    @Override
    public void Init() {
        HB_PROFILE_FUNCTION();

        glfwMakeContextCurrent(m_WindowHandle);
        GL.createCapabilities();

        HB_CORE_INFO("OpenGL Info:");
        HB_CORE_INFO("  Vendor: {0}", glGetString(GL_VENDOR));
        HB_CORE_INFO("  Renderer: {0}", glGetString(GL_RENDERER));
        HB_CORE_INFO("  Version: {0}", glGetString(GL_VERSION));

        HB_PROFILE_FUNCTION_STOP();
    }

    @Override
    public void SwapBuffers() {
        HB_PROFILE_FUNCTION();

        glfwSwapBuffers(m_WindowHandle);

        HB_PROFILE_FUNCTION_STOP();
    }
}
