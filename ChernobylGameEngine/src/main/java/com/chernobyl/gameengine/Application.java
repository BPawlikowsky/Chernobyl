package com.chernobyl.gameengine;

import com.chernobyl.gameengine.buffer.IndexBuffer;
import com.chernobyl.gameengine.buffer.VertexBuffer;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.event.EventDispatcher;
import com.chernobyl.gameengine.event.WindowCloseEvent;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.layer.ImGuiLayer;
import com.chernobyl.gameengine.layer.Layer;
import com.chernobyl.gameengine.layer.LayerStack;
import com.chernobyl.gameengine.render.Shader;
import com.chernobyl.platform.linux.LinuxWindow;
import com.chernobyl.gameengine.window.Window;
import lombok.Getter;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
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
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        m_VertexBuffer = VertexBuffer.Create(vertices, vertices.length);


        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, TIGHT_STRIDE, NULL);

        int[] indices = {0, 1, 2};
        m_IndexBuffer = IndexBuffer.Create(indices, indices.length);

        String vertexSrc = """
                        #version 330 core

                        layout(location = 0) in vec3 a_Position;
                        out vec3 v_Position;
                        void main()
                        {
                            v_Position = a_Position;
                            gl_Position = vec4(a_Position, 1.0);
                        }
        """;
        String fragmentSrc = """
                            #version 330 core

                        layout(location = 0) out vec4 color;
                        in vec3 v_Position;
                        void main()
                        {
                            color = vec4(v_Position * 0.5 + 0.5, 1.0);
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
            glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, NULL);

            HB_CORE_ASSERT(glGetError() == GL_NO_ERROR, "GL Error has occured!");

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
}
