package com.chernobyl.gameengine.core.input;

import com.chernobyl.gameengine.util.Pair;

public abstract class Input {
    private static final Input s_Instance = new LinuxInput();
    public static boolean IsKeyPressed(int keycode) {
        return s_Instance.IsKeyPressedImpl(keycode);
    }

    public static boolean IsMouseButtonPressed(int button) {
        return s_Instance.IsMouseButtonPressedImpl(button);
    }

    public static Pair<Float, Float> GetMousePosition() {
        return s_Instance.GetMousePositionImpl();
    }

    public static float GetMouseX() {
        return s_Instance.GetMouseXImpl();
    }

    public static float GetMouseY() {
        return s_Instance.GetMouseYImpl();
    }

    protected abstract boolean IsKeyPressedImpl(int keycode);

    protected abstract boolean IsMouseButtonPressedImpl(int button);

    protected abstract Pair<Float, Float> GetMousePositionImpl();

    protected abstract float GetMouseXImpl();

    protected abstract float GetMouseYImpl();
}
