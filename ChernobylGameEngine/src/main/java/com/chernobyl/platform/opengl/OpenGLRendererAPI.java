package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.math.Vec4;
import com.chernobyl.gameengine.renderer.RendererAPI;
import com.chernobyl.gameengine.renderer.VertexArray;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLRendererAPI extends RendererAPI {
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
