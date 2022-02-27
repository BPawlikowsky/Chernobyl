package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.input.MouseCode;
import com.chernobyl.gameengine.events.enums.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent{
    public MouseButtonReleasedEvent(MouseCode button) {
        super(button);
    }

    @Override
    public EventType GetStaticType() {
        return EventType.MouseButtonReleased;
    }

    @Override
    public String toString() {
        return "MouseButtonReleasedEvent{" +
                "button=" + mouseButton +
                '}';
    }
}
