package com.chernobyl.gameengine.render;

import lombok.Getter;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public enum ShaderDataType {
    None(0),
    Float(1), Float2(2), Float3(3), Float4(4),
    Mat3(5), Mat4(6),
    Int(7), Int2(8), Int3(9), Int4(10),
    Bool(11);

    @Getter
    private final int option;

    ShaderDataType(int i) {
        option = i;
    }
    
    static int ShaderDataTypeSize(ShaderDataType type)
    {
        switch (type)
        {
            case None:     return 0;
            case Float:    return 4;
            case Float2:   return 4 * 2;
            case Float3:   return 4 * 3;
            case Float4:   return 4 * 4;
            case Mat3:     return 4 * 3 * 3;
            case Mat4:     return 4 * 4 * 4;
            case Int:      return 4;
            case Int2:     return 4 * 2;
            case Int3:     return 4 * 3;
            case Int4:     return 4 * 4;
            case Bool:     return 1;
        }

        HB_CORE_ASSERT(false, "Unknown ShaderDataType!");
        return 0;
    }
}
