package com.chernobyl.gameengine.math;

import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class Vec3 extends Vector3f {
    public Vec3() {
        super();
    }

    public Vec3(float d) {
        super(d);
    }

    public Vec3(float x, float y, float z) {
        super(x, y, z);
    }

    public Vec3(float[] xyz) {
        super(xyz);
    }

    public Vec3(FloatBuffer buffer) {
        super(buffer);
    }
}
