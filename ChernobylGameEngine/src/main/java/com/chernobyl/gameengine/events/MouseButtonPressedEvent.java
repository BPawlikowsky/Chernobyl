package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent{
    public MouseButtonPressedEvent(int button) {
        super(button);
    }

    @Override
    EventType GetStaticType() {
        return EventType.MouseButtonPressed;
    }

    @Override
    public String toString() {
        return "MouseButtonPressedEvent{" +
                "button=" + button +
                '}';
    }
}
