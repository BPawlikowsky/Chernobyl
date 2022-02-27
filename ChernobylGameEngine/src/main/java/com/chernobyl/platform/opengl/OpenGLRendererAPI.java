package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.renderer.RendererAPI;
import com.chernobyl.gameengine.renderer.VertexArray;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_FUNCTION_STOP;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLRendererAPI extends RendererAPI {
    @Override
    public void Init() {
        HB_PROFILE_FUNCTION();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);

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
}
