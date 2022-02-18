package com.chernobyl.gameengine.math;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;

public class Mat4 extends Matrix4f {
    public Mat4() {
        super();
    }

    public Mat4(float f) {
        super(
                f, 0.0f, 0.0f, 0.0f, 0.0f,
                f, 0.0f, 0.0f, 0.0f, 0.0f,
                f, 0.0f, 0.0f, 0.0f, 0.0f,
                f
        );
    }

    public Mat4(Vector4fc col0, Vector4fc col1, Vector4fc col2, Vector4fc col3) {
        super(col0, col1, col2, col3);
    }

    @Override
    public Mat4 identity() {
        return (Mat4) super.identity();
    }

    @Override
    public Mat4 mul(Matrix4fc right) {
        return (Mat4) super.mul(right);
    }

    @Override
    public Mat4 ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return (Mat4) super.ortho(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Mat4 rotate(float angle, Vector3fc axis) {
        return (Mat4) super.rotate(angle, axis);
    }

    @Override
    public Mat4 translate(Vector3fc offset) {
        return (Mat4) super.translate(offset);
    }

    @Override
    public Mat4 rotate(float angle, Vector3fc axis, Matrix4f dest) {
        return (Mat4) super.rotate(angle, axis, dest);
    }

    public float[] toArray() {
        var arr = new float[4 * 4];
        return get(arr);
    }

    @Override
    public Mat4 invert(Matrix4f dest) {
        return (Mat4) super.invert(dest);
    }

    @Override
    public Mat4 invertPerspective(Matrix4f dest) {
        return (Mat4) super.invertPerspective(dest);
    }

    @Override
    public Mat4 invertPerspective() {
        return (Mat4) super.invertPerspective();
    }

    @Override
    public Mat4 invertOrtho(Matrix4f dest) {
        return (Mat4) super.invertOrtho(dest);
    }

    @Override
    public Mat4 invertOrtho() {
        return (Mat4) super.invertOrtho();
    }

    @Override
    public Mat4 invert() {
        return (Mat4) super.invert();
    }

    @Override
    public Mat4 scale(Vector3fc xyz) {
        return (Mat4) super.scale(xyz);
    }
}
