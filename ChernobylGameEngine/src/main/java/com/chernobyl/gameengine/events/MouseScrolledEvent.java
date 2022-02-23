package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.events.enums.EventType;
import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryMouse;

public class MouseScrolledEvent extends Event {
    @Getter
    private final float xOffset;
    @Getter
    private final float yOffset;

    public MouseScrolledEvent(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }

    @Override
    public EventType GetStaticType() {
        return EventType.MouseScrolled;
    }

    @Override
    public String toString() {
        return "MouseScrolledEvent{" +
                "xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                '}';
    }
}
