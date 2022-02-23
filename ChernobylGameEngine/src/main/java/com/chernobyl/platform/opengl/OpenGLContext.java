package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.render.GraphicsContext;
import org.lwjgl.opengl.GL;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
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
        glfwMakeContextCurrent(m_WindowHandle);
        GL.createCapabilities();

        HB_CORE_INFO("OpenGL Info:");
        HB_CORE_INFO("  Vendor: {0}", glGetString(GL_VENDOR));
        HB_CORE_INFO("  Renderer: {0}", glGetString(GL_RENDERER));
        HB_CORE_INFO("  Version: {0}", glGetString(GL_VERSION));
    }

    @Override
    public void SwapBuffers() {
        glfwSwapBuffers(m_WindowHandle);
    }
}
