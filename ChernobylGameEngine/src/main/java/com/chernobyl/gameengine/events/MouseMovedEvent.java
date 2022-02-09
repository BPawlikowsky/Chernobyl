package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;
import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryMouse;

public class MouseMovedEvent extends Event{
    @Getter
    private final float mouseX;
    @Getter
    private final float mouseY;

    public MouseMovedEvent(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }

    @Override
    EventType GetStaticType() {
        return EventType.MouseMoved;
    }

    @Override
    public String toString() {
        return "MouseMovedEvent{" +
                "mouseX=" + mouseX +
                ", mouseY=" + mouseY +
                '}';
    }
}
