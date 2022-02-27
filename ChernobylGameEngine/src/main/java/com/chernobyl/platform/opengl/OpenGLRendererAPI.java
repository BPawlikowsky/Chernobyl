package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.core.Config;
import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.renderer.RendererAPI;
import com.chernobyl.gameengine.renderer.VertexArray;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static com.chernobyl.gameengine.core.Log.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLRendererAPI extends RendererAPI {

    @Override
    public void Init() {
        HB_PROFILE_FUNCTION();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);

        if(Config.isDebug) {
            glEnable(GL_DEBUG_OUTPUT);
            glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
            glDebugMessageCallback(this::OpenGLMessageCallback, NULL);

            // LWJGL bug causes this line to generate GL_INVALID error in GL Debug messages
            glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DEBUG_SEVERITY_NOTIFICATION, 0, false);
        }

        HB_PROFILE_FUNCTION_STOP();
    }

    public void SetViewport(int x, int y, int width, int height)
    {
        glViewport(x, y, width, height);
    }

    public void SetClearColor(Vec4 color)
    {
        glClearColor(color.x, color.y, color.x, color.z);
    }

    public void Clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void DrawIndexed(VertexArray vertexArray)
    {
        glDrawElements(GL_TRIANGLES, vertexArray.GetIndexBuffer().GetCount(), GL_UNSIGNED_INT, NULL);
        glBindTexture(GL_TEXTURE_2D, 0);
    }


    public void OpenGLMessageCallback(
            int source,
            int type,
            int id,
            int severity,
            int length,
		long message,
		long userParam)
    {
        var textMessage = GLDebugMessageCallback.getMessage(length, message);
        // stackTrace index 5 points beyond the callback stack part to the function that caused the callback message
        var stackTrace = Thread.currentThread().getStackTrace()[5];
        switch (severity)
        {
            case GL_DEBUG_SEVERITY_HIGH:         HB_CORE_CRITICAL(stackTrace.getClassName() + "." + stackTrace.getMethodName() + "(): " + textMessage); return;
            case GL_DEBUG_SEVERITY_MEDIUM:       HB_CORE_ERROR(textMessage); return;
            case GL_DEBUG_SEVERITY_LOW:          HB_CORE_WARN(textMessage); return;
            case GL_DEBUG_SEVERITY_NOTIFICATION: HB_CORE_TRACE(textMessage); return;
        }

        HB_CORE_ASSERT(false, "Unknown severity level!");
    }
}
