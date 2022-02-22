package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.renderer.RendererAPI;
import com.chernobyl.gameengine.renderer.VertexArray;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLRendererAPI extends RendererAPI {
    @Override
    public void Init() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
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
    }
}
