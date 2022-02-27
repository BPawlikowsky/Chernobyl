package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.input.MouseCode;
import com.chernobyl.gameengine.events.enums.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent{
    public MouseButtonPressedEvent(MouseCode button) {
        super(button);
    }

    @Override
    public EventType GetStaticType() {
        return EventType.MouseButtonPressed;
    }

    @Override
    public String toString() {
        return "MouseButtonPressedEvent{" +
                "button=" + mouseButton +
                '}';
    }
}
