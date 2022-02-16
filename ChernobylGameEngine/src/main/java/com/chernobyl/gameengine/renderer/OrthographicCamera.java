package com.chernobyl.gameengine.renderer;

import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.math.Vec3;

import static org.joml.Math.toRadians;

public class OrthographicCamera {
    private final Mat4 m_ProjectionMatrix;
    private Mat4 m_ViewMatrix;
    private Mat4 m_ViewProjectionMatrix;

    private static Vec3 m_Position = new Vec3(0.0f, 0.0f, 0.0f);
    float m_Rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        m_ProjectionMatrix = new Mat4().ortho(left, right, bottom, top, -1.0f, 1.0f);
        m_ViewMatrix = (Mat4) new Mat4().identity();
        m_ViewProjectionMatrix = m_ProjectionMatrix.mul(m_ViewMatrix);
    }

    public Vec3 GetPosition() {
        return m_Position;
    }

    public void SetPosition(Vec3 position) {
        m_Position.x = position.x;
        m_Position.y = position.y;
        m_Position.z = position.z;
        RecalculateViewMatrix();
    }

    float GetRotation() {
        return m_Rotation;
    }

    public void SetRotation(float rotation) {
        m_Rotation = rotation;
        RecalculateViewMatrix();
    }

    public Mat4 GetProjectionMatrix() {
        return m_ProjectionMatrix;
    }

    public Mat4 GetViewMatrix() {
        return m_ViewMatrix;
    }

    public Mat4 GetViewProjectionMatrix() {
        return m_ViewProjectionMatrix;
    }

    private void RecalculateViewMatrix() {
        var transform = m_ViewMatrix
                .rotate(toRadians(m_Rotation), new Vec3(0f, 0f, 1f))
                .translate(m_Position);

        m_ViewMatrix = transform.invert();
        m_ViewProjectionMatrix =  m_ProjectionMatrix.mul(m_ViewMatrix);
    }
}
