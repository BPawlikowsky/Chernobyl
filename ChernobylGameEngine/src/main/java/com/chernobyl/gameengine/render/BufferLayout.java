package com.chernobyl.gameengine.render;

import java.util.Arrays;
import java.util.Vector;

public class BufferLayout {
    private static Vector<BufferElement> m_Elements;
    int m_Stride = 0;

    public BufferLayout() {
    }

    public BufferLayout(BufferElement[] elements) {
        m_Elements = new Vector<>(Arrays.stream(elements).toList());
        CalculateOffsetsAndStride();
    }


    public int GetStride() {
        return m_Stride;
    }

    public Vector<BufferElement> GetElements() {
        return m_Elements;
    }

    public int begin() {
        return 0;
    }

    public int end() {
        return m_Elements.size();
    }

    private void CalculateOffsetsAndStride() {
        int offset = 0;
        m_Stride = 0;
        for (var element : m_Elements) {
            element.Offset = offset;
            offset += element.Size;
            m_Stride += element.Size;
        }
    }
}
