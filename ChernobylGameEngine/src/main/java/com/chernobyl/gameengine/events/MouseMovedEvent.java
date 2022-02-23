package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.events.enums.EventType;
import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryMouse;

public class MouseMovedEvent extends Event {
    @Getter
    private final float x;
    @Getter
    private final float y;

    public MouseMovedEvent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }

    @Override
    public EventType GetStaticType() {
        return EventType.MouseMoved;
    }

    @Override
    public String toString() {
        return "MouseMovedEvent{" +
                "mouseX=" + x +
                ", mouseY=" + y +
                '}';
    }
}
