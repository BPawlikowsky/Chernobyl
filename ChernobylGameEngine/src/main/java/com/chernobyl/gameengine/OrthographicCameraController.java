package com.chernobyl.gameengine;

import com.chernobyl.gameengine.core.Timestep;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.event.EventDispatcher;
import com.chernobyl.gameengine.event.MouseScrolledEvent;
import com.chernobyl.gameengine.event.WindowResizeEvent;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.input.Input;
import com.chernobyl.gameengine.math.Vec3;
import lombok.Getter;

import static com.chernobyl.gameengine.input.KeyCodes.*;

public class OrthographicCameraController {
    private float m_AspectRatio;
    private float m_ZoomLevel = 1.0f;
    private final OrthographicCamera m_Camera;

    private final boolean m_Rotation;

    private final Vec3 m_CameraPosition = new Vec3(0.0f, 0.0f, 0.0f);
    private float m_CameraRotation = 0.0f;
    private float m_CameraTranslationSpeed = 5.0f;
    private final float m_CameraRotationSpeed = 180.0f;

    public OrthographicCameraController(float aspectRatio) {
        m_AspectRatio = aspectRatio;
        m_Camera = new OrthographicCamera(-m_AspectRatio * m_ZoomLevel, m_AspectRatio * m_ZoomLevel, -m_ZoomLevel, m_ZoomLevel);
        m_Rotation = false;
    }

    public OrthographicCameraController(float aspectRatio, boolean rotation) {
        m_AspectRatio = aspectRatio;
        m_Camera = new OrthographicCamera(-m_AspectRatio * m_ZoomLevel, m_AspectRatio * m_ZoomLevel, -m_ZoomLevel, m_ZoomLevel);
        m_Rotation = rotation;
    }

    public OrthographicCamera GetCamera() {
        return m_Camera;
    }

    public void OnUpdate(Timestep ts) {
        if (Input.IsKeyPressed(HB_KEY_A))
            m_CameraPosition.x -= m_CameraTranslationSpeed * ts.GetSeconds();
        else if (Input.IsKeyPressed(HB_KEY_D))
            m_CameraPosition.x += m_CameraTranslationSpeed * ts.GetSeconds();

        if (Input.IsKeyPressed(HB_KEY_W))
            m_CameraPosition.y += m_CameraTranslationSpeed * ts.GetSeconds();
        else if (Input.IsKeyPressed(HB_KEY_S))
            m_CameraPosition.y -= m_CameraTranslationSpeed * ts.GetSeconds();

        if (m_Rotation) {
            if (Input.IsKeyPressed(HB_KEY_Q))
                m_CameraRotation += m_CameraRotationSpeed * ts.GetSeconds();
            if (Input.IsKeyPressed(HB_KEY_E))
                m_CameraRotation -= m_CameraRotationSpeed * ts.GetSeconds();

            m_Camera.SetRotation(m_CameraRotation);
        }

        m_Camera.SetPosition(m_CameraPosition);

        m_CameraTranslationSpeed = m_ZoomLevel;
    }

    public void OnEvent(Event e) {
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.Dispatch(this::OnMouseScrolled, EventType.MouseScrolled);
        dispatcher.Dispatch(this::OnWindowResized, EventType.WindowResize);
    }

    public float GetZoomLevel() { return m_ZoomLevel; }

    public void SetZoomLevel(float level) { m_ZoomLevel = level; }

    private boolean OnMouseScrolled(MouseScrolledEvent e) {
        m_ZoomLevel -= e.getYOffset() * 0.25f;
        m_ZoomLevel = Math.max(m_ZoomLevel, 0.25f);
        m_Camera.SetProjection(-m_AspectRatio * m_ZoomLevel, m_AspectRatio * m_ZoomLevel, -m_ZoomLevel, m_ZoomLevel);
        return false;
    }

    private boolean OnWindowResized(WindowResizeEvent e) {
        m_AspectRatio = (float) e.getWidth() / (float) e.getHeight();
        m_Camera.SetProjection(-m_AspectRatio * m_ZoomLevel, m_AspectRatio * m_ZoomLevel, -m_ZoomLevel, m_ZoomLevel);
        return false;
    }
}
