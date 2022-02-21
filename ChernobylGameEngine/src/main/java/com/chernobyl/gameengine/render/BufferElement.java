package com.chernobyl.gameengine.render;

import lombok.Getter;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.render.ShaderDataType.ShaderDataTypeSize;

public class BufferElement {
    String Name;
    @Getter
    ShaderDataType Type;
    int Size;
    @Getter
    int Offset;
    @Getter
    boolean Normalized;

    BufferElement() {
    }

    public BufferElement(ShaderDataType type, String name, boolean normalized) {
        Name = name;
        Type = type;
        Size = ShaderDataTypeSize(type);
        Offset = 0;
        Normalized = normalized;
    }

    public BufferElement(ShaderDataType type, String name) {
        Name = name;
        Type = type;
        Size = ShaderDataTypeSize(type);
        Offset = 0;
        Normalized = false;
    }

    public int GetComponentCount() {
        switch (Type) {
            case Float:
                return 1;
            case Float2:
                return 2;
            case Float3:
                return 3;
            case Float4:
                return 4;
            case Mat3:
                return 3 * 3;
            case Mat4:
                return 4 * 4;
            case Int:
                return 1;
            case Int2:
                return 2;
            case Int3:
                return 3;
            case Int4:
                return 4;
            case Bool:
                return 1;
        }

        HB_CORE_ASSERT(false, "Unknown ShaderDataType!");
        return 0;
    }
}
