package com.chernobyl.gameengine;

import com.chernobyl.gameengine.math.Mat4;
import com.chernobyl.gameengine.math.Vec3;

import static org.joml.Math.toRadians;

public class OrthographicCamera {
    private Mat4 m_ProjectionMatrix;
    private final Mat4 m_ViewMatrix;
    private final Mat4 m_ViewProjectionMatrix;

    private static Vec3 m_Position = new Vec3(0.0f, 0.0f, 0.0f);
    float m_Rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        var ortho = new Mat4().setOrtho(left, right, bottom, top, -1.0f, 1.0f);
        m_ProjectionMatrix = new Mat4();
        m_ProjectionMatrix.mul(ortho);
        m_ViewMatrix = new Mat4(1.0f);
        m_ViewProjectionMatrix = new Mat4();
        m_ViewProjectionMatrix.set(m_ProjectionMatrix.mul(m_ViewMatrix));
    }

    public Vec3 GetPosition() {
        return m_Position;
    }

    public void SetPosition(Vec3 position) {
        m_Position = position;
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
        var transform = new Mat4()
                .translate(m_Position)
                .rotate(toRadians(m_Rotation), new Vec3(0f, 0f, 1f));

        transform.invert();
        m_ViewMatrix.set(transform);

        m_ProjectionMatrix.mul(m_ViewMatrix, m_ViewProjectionMatrix);
    }

    void SetProjection(float left, float right, float bottom, float top)
    {
        m_ProjectionMatrix = new Mat4().ortho(left, right, bottom, top, -1.0f, 1.0f);
        m_ProjectionMatrix.mul(m_ViewMatrix, m_ViewProjectionMatrix);
    }
}
