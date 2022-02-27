package com.chernobyl.gameengine.core.input;

import com.chernobyl.gameengine.core.Application;
import com.chernobyl.gameengine.util.Pair;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class LinuxInput extends Input{
    @Override
    protected boolean IsKeyPressedImpl(KeyCode keycode) {
        Application.Create();
        long window = Application.getM_Window().getNativeWindow();
        int state = glfwGetKey(window, keycode.key());
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    @Override
    protected boolean IsMouseButtonPressedImpl(MouseCode button) {
        Application.Create();
        long window = Application.getM_Window().getNativeWindow();
        int state = glfwGetMouseButton(window, button.key());
        return state == GLFW_PRESS;
    }

    @Override
    protected Pair<Float, Float> GetMousePositionImpl() {
        Application.Create();
        long window = Application.getM_Window().getNativeWindow();
        DoubleBuffer xpos, ypos;
        xpos = BufferUtils.createDoubleBuffer(1);
        ypos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xpos, ypos);
        return new Pair<>((float) xpos.get(), (float) ypos.get());
    }

    @Override
    protected float GetMouseXImpl() {
        return GetMousePositionImpl().getFirst();
    }

    @Override
    protected float GetMouseYImpl() {
        return GetMousePositionImpl().getSecond();
    }
}
