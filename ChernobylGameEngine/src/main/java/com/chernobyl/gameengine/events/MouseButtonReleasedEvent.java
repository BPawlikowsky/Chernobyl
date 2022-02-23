package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent{
    public MouseButtonReleasedEvent(int button) {
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
