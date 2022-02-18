package com.chernobyl.gameengine.math;

import org.joml.Matrix3f;

public class Mat3 extends Matrix3f {
    public float[] toArray() {
        var arr = new float[4 * 4];
        return get(arr);
    }
}
