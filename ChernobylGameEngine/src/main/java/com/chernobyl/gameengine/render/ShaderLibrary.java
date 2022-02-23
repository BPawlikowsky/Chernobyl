package com.chernobyl.gameengine.render;

import java.util.HashMap;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public class ShaderLibrary {
    private final HashMap<String, Shader> m_Shaders = new HashMap<>();

    public void Add(String name, Shader shader) {
        HB_CORE_ASSERT(!Exists(name), "Shader already exists!");
        m_Shaders.put(name, shader);
    }

    public void Add(Shader shader) {
        var name = shader.GetName();
        Add(name, shader);
    }

    public Shader Load(String filepath) {
        var shader = Shader.Create(filepath);
        HB_CORE_ASSERT(shader != null, "Shader wasn't created: " + filepath);
        Add(shader);
        return shader;
    }

    public Shader Load(String name, String filepath) {
        var shader = Shader.Create(filepath);
        Add(name, shader);
        return shader;
    }

    public Shader Get(String name) {
        HB_CORE_ASSERT(Exists(name), "Shader not found!");
        return m_Shaders.get(name);
    }

    public boolean Exists(String name) {
        return m_Shaders.containsKey(name);
    }
}
