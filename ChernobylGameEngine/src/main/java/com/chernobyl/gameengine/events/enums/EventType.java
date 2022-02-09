package com.chernobyl.gameengine.events.enums;

public enum EventType {
    None(0),
    WindowClose, WindowResize, WindowFocus, WindowLostFocus, WindowMoved,
    AppTick, AppUpdate, AppRender,
    KeyPressed, KeyReleased,
    MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled;

    private final int value;

    EventType(int i) {
        value = i;
    }

    EventType() {
        value = this.ordinal();
    }

    public int getValue() { return value; }
}
