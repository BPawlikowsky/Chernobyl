package com.chernobyl.gameengine.input;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.util.Pair;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class LinuxInput extends Input{
    @Override
    protected boolean IsKeyPressedImpl(int keycode) {
        Application.get();
        long window = Application.getWindow().getNativeWindow();
        int state = glfwGetKey(window, keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    @Override
    protected boolean IsMouseButtonPressedImpl(int button) {
        Application.get();
        long window = Application.getWindow().getNativeWindow();
        int state = glfwGetMouseButton(window, button);
        return state == GLFW_PRESS;
    }

    @Override
    protected Pair<Float, Float> GetMousePositionImpl() {
        Application.get();
        long window = Application.getWindow().getNativeWindow();
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
