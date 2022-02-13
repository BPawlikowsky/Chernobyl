package com.chernobyl.gameengine;

import com.chernobyl.gameengine.buffer.*;
import com.chernobyl.gameengine.event.*;
import com.chernobyl.gameengine.layer.*;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.render.BufferElement;
import com.chernobyl.gameengine.render.BufferLayout;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.gameengine.render.ShaderDataType;
import com.chernobyl.platform.linux.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_ERROR;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Application {
    private static final int TIGHT_STRIDE = 0;
    @Getter
    private static Window window = null;
    private static Application application = null;
    private static boolean running = true;
    @Getter
    private static final LayerStack m_LayerStack = new LayerStack();
    private final ImGuiLayer m_ImGuiLayer;

    private final int m_VertexArray;
    private final VertexBuffer m_VertexBuffer;
    private final IndexBuffer m_IndexBuffer;
    private final Shader m_Shader;

    public Application() {
        HB_CORE_ASSERT(application == null, "Application already exists!");
        window = LinuxWindow.get();
        window.SetEventCallback(Application::onEvent);
        m_ImGuiLayer = new ImGuiLayer();
        pushOverlay(m_ImGuiLayer);

        m_VertexArray = glGenVertexArrays();
        glBindVertexArray(m_VertexArray);

        var vertices = new float[]{
                -0.5f, -0.5f, 0.0f, 0.8f, 0.2f, 0.8f, 1.0f,
                0.5f, -0.5f, 0.0f, 0.2f, 0.3f, 0.8f, 1.0f,
                0.0f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f
        };

        m_VertexBuffer = VertexBuffer.Create(vertices, vertices.length);
        {
            BufferLayout layout = new BufferLayout(new BufferElement[]{
                    new BufferElement(ShaderDataType.Float3, "a_Position", false),
                    new BufferElement(ShaderDataType.Float4, "a_Color", false)
            });

            m_VertexBuffer.SetLayout(layout);
        }

        int index = 0;
		var layout = m_VertexBuffer.GetLayout();
        for (var element : layout.GetElements())
        {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index,
                    element.GetComponentCount(),
                    ShaderDataTypeToOpenGLBaseType(element.getType()),
                    element.isNormalized(),
                    layout.GetStride(),
                    element.getOffset());
            index++;
        }


        int[] indices = {0, 1, 2};
        m_IndexBuffer = IndexBuffer.Create(indices, indices.length);

        String vertexSrc = """
                        #version 410 core

                        layout(location = 0) in vec3 a_Position;
                        layout(location = 1) in vec4 a_Color;
                        out vec3 v_Position;
                        out vec4 v_Color;
                        void main()
                        {
                            v_Position = a_Position;
                            gl_Position = vec4(a_Position, 1.0);
                            v_Color = a_Color;
                        }
        """;
        String fragmentSrc = """
                            #version 410 core

                        layout(location = 0) out vec4 color;
                        in vec3 v_Position;
                        in vec4 v_Color;
                        void main()
                        {
                            color = v_Color;
                        }
        """;

        m_Shader = new Shader(vertexSrc, fragmentSrc);
    }

    public static Application get() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }

    public void Run() {
        while (running) {
            glClearColor(0.1f, 0.1f, 0.1f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            m_Shader.Bind();

            glBindVertexArray(m_VertexArray);
            glDrawElements(GL_TRIANGLES, m_IndexBuffer.getM_Count(), GL_UNSIGNED_INT, NULL);

            for (Layer layer : m_LayerStack)
                layer.OnUpdate();

            m_ImGuiLayer.Begin();
            for (Layer layer : m_LayerStack)
                layer.OnImGuiRender();
            m_ImGuiLayer.End();

            window.OnUpdate();
        }
        window.Shutdown();
    }

    private static void onEvent(Event e) {
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(Application::onWindowClose, EventType.WindowClose);

        for (int i = m_LayerStack.end(); i >= m_LayerStack.begin(); i--) {
            m_LayerStack.get(i).OnEvent(e);
            if (e.isM_Handled()) break;
        }
    }

    public void pushLayer(Layer layer) {
        m_LayerStack.PushLayer(layer);
    }

    public void pushOverlay(Layer overlay) {
        m_LayerStack.PushOverlay(overlay);
    }

    private static boolean onWindowClose(WindowCloseEvent e) {
        running = false;
        return true;
    }


    static int ShaderDataTypeToOpenGLBaseType(ShaderDataType type)
    {
        switch (type)
        {
            case Float:    return GL_FLOAT;
            case Float2:   return GL_FLOAT;
            case Float3:   return GL_FLOAT;
            case Float4:   return GL_FLOAT;
            case Mat3:     return GL_FLOAT;
            case Mat4:     return GL_FLOAT;
            case Int:      return GL_INT;
            case Int2:     return GL_INT;
            case Int3:     return GL_INT;
            case Int4:     return GL_INT;
            case Bool:     return GL_BOOL;
        }

        HB_CORE_ASSERT(false, "Unknown ShaderDataType!");
        return 0;
    }
}
