package com.chernobyl.gameengine.core.input;

import com.chernobyl.gameengine.core.window.Window;
import com.chernobyl.gameengine.util.Pair;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public abstract class Input {
    private static final Input s_Instance = Input.Create();
    public static boolean IsKeyPressed(KeyCode keycode) {
        return s_Instance.IsKeyPressedImpl(keycode);
    }

    public static boolean IsMouseButtonPressed(MouseCode button) {
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

    protected abstract boolean IsKeyPressedImpl(KeyCode keycode);

    protected abstract boolean IsMouseButtonPressedImpl(MouseCode button);

    protected abstract Pair<Float, Float> GetMousePositionImpl();

    protected abstract float GetMouseXImpl();

    protected abstract float GetMouseYImpl();

    public static Input Create() {
        switch (Window.getS_platform()) {
            case Linux -> { return new LinuxInput(); }
            case None -> { return null; }
        }

        HB_CORE_ASSERT(false, "Unknown platform!");
        return null;
    }
}
