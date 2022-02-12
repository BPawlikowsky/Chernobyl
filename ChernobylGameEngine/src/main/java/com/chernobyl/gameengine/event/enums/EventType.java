package com.chernobyl.gameengine.event.enums;

public enum EventType {
    None(0),
    WindowClose(1), WindowResize(2), WindowFocus(3), WindowLostFocus(4), WindowMoved(5),
    AppTick(6), AppUpdate(7), AppRender(8),
    KeyPressed(9), KeyReleased(10),
    MouseButtonPressed(11), MouseButtonReleased(12), MouseMoved(13), MouseScrolled(14), KeyTyped(15);

    private final int value;

    EventType(int i) {
        value = i;
    }

    public int getValue() { return value; }
}
