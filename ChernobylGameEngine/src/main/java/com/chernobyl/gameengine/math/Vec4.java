package com.chernobyl.gameengine.math;

import org.joml.Vector4f;

import java.nio.FloatBuffer;

public class Vec4 extends Vector4f {
    public Vec4(float d) {
        super(d);
    }

    public Vec4(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    public Vec4(float[] xyzw) {
        super(xyzw);
    }

    public Vec4(FloatBuffer buffer) {
        super(buffer);
    }

    public Vec4() {
        super();
    }
}
